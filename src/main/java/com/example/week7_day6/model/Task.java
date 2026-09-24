package com.example.week7_day6.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Task {
    private final Integer id;
    private String title;
    private String description;
    private TaskPriority taskPriority;
    private TaskStatus taskStatus;
    private Set<String> tags = new HashSet<>();

    public Task(Integer id, String title, String description, TaskPriority taskPriority) {
        if (id == null) {
            throw new IllegalArgumentException("Task id cannot be null");
        }
        this.id = id;
        setTitle(title);
        this.description = description;
        setTaskPriority(taskPriority);
        setTaskStatus(TaskStatus.NEW);

    }


    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskPriority getTaskPriority() {
        return taskPriority;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Task title cannot be empty or null");
        }
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTaskPriority(TaskPriority taskPriority) {
        if (taskPriority == null) {
            throw new IllegalArgumentException("Task priority cannot be null");
        }
        this.taskPriority = taskPriority;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        if (taskStatus == null) {
            throw new IllegalArgumentException("Task status cannot be null");
        }
        this.taskStatus = taskStatus;
    }

    public void addTag(String tag) {
        if (tag.isBlank()) {
            throw new IllegalArgumentException("The tag cannot be empty");
        }
        this.tags.add(tag.trim().toLowerCase());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o instanceof Task task){
            return Objects.equals(task.id, id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
