package com.example.week7_day6.repository;

import com.example.week7_day6.model.Task;
import com.example.week7_day6.model.TaskPriority;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TaskRepository {
    private final AtomicInteger idGenerator = new AtomicInteger(1);
    private final Map<Integer, Task> tasks = new ConcurrentHashMap<>();

    public List<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }

    public Optional<Task> findById(Integer id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(tasks.get(id));
    }

    public Task save(String title, String description, TaskPriority taskPriority) {
        int id = idGenerator.getAndIncrement();
        Task task = new Task(id, title, description, taskPriority);
        tasks.put(id, task);
        return task;
    }

    public boolean deleteById(Integer id) {
        if (id == null) {
            return false;
        }
        return tasks.remove(id) != null;
    }
}
