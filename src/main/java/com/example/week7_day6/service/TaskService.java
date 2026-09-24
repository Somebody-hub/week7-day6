package com.example.week7_day6.service;

import com.example.week7_day6.dto.*;
import com.example.week7_day6.exception.TagAlreadyExistsException;
import com.example.week7_day6.exception.TaskNotFoundException;
import com.example.week7_day6.model.Task;
import com.example.week7_day6.model.TaskPriority;
import com.example.week7_day6.model.TaskStatus;
import com.example.week7_day6.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskResponse> getTasks(TaskStatus status, TaskPriority priority, String tag) {
        Predicate<Task> filter = task -> true;

        if (status != null) {
            filter = filter.and(task -> task.getTaskStatus() == status);
        }
        if (priority != null) {
            filter = filter.and(task -> task.getTaskPriority() == priority);
        }
        if (tag != null && !tag.isBlank()) {
            filter = filter.and(task -> task.getTags().contains(tag.trim().toLowerCase()));
        }

        return taskRepository.findAll().stream()
                .filter(filter)
                .map(this::toResponse)
                .toList();
    }

    public TaskResponse findById(Integer id) {
        return toResponse(taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id)));
    }

    public TaskResponse create(CreateTaskRequest request) {
        Task newTask = taskRepository.save(
                request.title(),
                request.description(),
                request.taskPriority()
        );
        return toResponse(newTask);
    }

    public TaskResponse updatePriority(Integer id, UpdateTaskPriorityRequest request) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        task.setTaskPriority(request.priority());
        return toResponse(task);
    }

    public TaskResponse updateStatus(Integer id, UpdateTaskStatusRequest request) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        task.setTaskStatus(request.status());
        return toResponse(task);
    }

    public void deleteById(Integer id) {
        boolean deleted = taskRepository.deleteById(id);
        if (!deleted) {
            throw new TaskNotFoundException(id);
        }
    }

    public TaskResponse addTag(Integer id, String tag) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        if (task.getTags().contains(tag)) {
            throw new TagAlreadyExistsException(tag);
        }
        task.addTag(tag);
        return toResponse(task);
    }

    public TaskStatisticsResponse getStatistics() {
        List<Task> taskList = taskRepository.findAll();

        Map<TaskStatus, Integer> byStatus = new EnumMap<>(TaskStatus.class);
        for (TaskStatus status : TaskStatus.values()) {
            byStatus.put(status, 0);
        }

        Map<TaskPriority, Integer> byPriority = new EnumMap<>(TaskPriority.class);
        for (TaskPriority priority : TaskPriority.values()) {
            byPriority.put(priority, 0);
        }

        for (Task task : taskList) {
            byStatus.put(task.getTaskStatus(), byStatus.get(task.getTaskStatus()) + 1);
            byPriority.put(task.getTaskPriority(), byPriority.get(task.getTaskPriority()) + 1);
        }
        return new TaskStatisticsResponse(byStatus, byPriority);
    }

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(task.getId(), task.getTitle(), task.getDescription(), task.getTaskPriority(), task.getTaskStatus(), task.getTags());
    }

    /*
    private List<TaskResponse> toListResponse(List<Task> taskList) {
        return taskList.stream()
                .map(this::toResponse)
                .toList();
    }

     */

}
