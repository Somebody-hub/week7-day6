package com.example.week7_day6.exception;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(Integer id) {
        super("Task not found with id: " + id);
    }
}
