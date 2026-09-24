package com.example.week7_day6.controller;

import com.example.week7_day6.dto.*;
import com.example.week7_day6.model.TaskPriority;
import com.example.week7_day6.model.TaskStatus;
import com.example.week7_day6.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(TaskController.BASE_PATH)
public class TaskController {
    public static final String BASE_PATH = "/api/tasks";
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority,
            @RequestParam(required = false) String tag
    ) {
        return ResponseEntity.ok(taskService.getTasks(status, priority, tag));
    }

    @GetMapping("/statistics")
    public ResponseEntity<TaskStatisticsResponse> getStatistics() {
        return ResponseEntity.ok(taskService.getStatistics());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(taskService.findById(id));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody CreateTaskRequest request) {
        TaskResponse createdTask = taskService.create(request);
        return ResponseEntity
                .created(URI.create(BASE_PATH + "/" + createdTask.id()))
                .body(createdTask);
    }

    @PostMapping("/{id}/tags")
    public ResponseEntity<TaskResponse> addTag(
            @PathVariable Integer id,
            @Valid @RequestBody AddTagRequest request
    ) {
        TaskResponse updateTask = taskService.addTag(id, request.tag());
        return ResponseEntity.ok(updateTask);
    }

    @PatchMapping("/{id}/priority")
    public ResponseEntity<TaskResponse> updatePriority(@PathVariable Integer id,
                                                       @Valid @RequestBody UpdateTaskPriorityRequest request
    ) {
        TaskResponse updateTask = taskService.updatePriority(id, request);
        return ResponseEntity.ok(updateTask);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponse> updateStatus(@PathVariable Integer id,
                                                     @Valid @RequestBody UpdateTaskStatusRequest request
    ) {
        TaskResponse updateTask = taskService.updateStatus(id, request);
        return ResponseEntity.ok(updateTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        taskService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
