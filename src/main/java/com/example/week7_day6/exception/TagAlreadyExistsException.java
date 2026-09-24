package com.example.week7_day6.exception;

public class TagAlreadyExistsException extends RuntimeException {
    public TagAlreadyExistsException(String message) {
        super("Tag " + message + " already exists for this task");
    }
}
