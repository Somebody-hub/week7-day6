package com.example.week7_day6.dto;

import java.time.LocalDateTime;

public record ApiError(
        String code,
        String message,
        String path,
        LocalDateTime timestamp
) {
    public ApiError(String code, String message, String path) {
        this(code, message, path, LocalDateTime.now());
    }
}
