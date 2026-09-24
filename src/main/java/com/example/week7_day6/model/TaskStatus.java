package com.example.week7_day6.model;

public enum TaskStatus {
    NEW("New"),
    IN_PROGRESS("In progress"),
    DONE("Done"),
    CANCELED("Canceled");

    private final String taskStatusTitle;

    TaskStatus(String taskStatusTitle) {
        this.taskStatusTitle = taskStatusTitle;
    }

    public String getTaskStatusTitle() {
        return taskStatusTitle;
    }
}
