package com.example.week7_day6.dto;

import com.example.week7_day6.model.TaskPriority;
import com.example.week7_day6.model.TaskStatus;

import java.util.Set;

public record TaskResponse(
        Integer id,
        String title,
        String description,
        TaskPriority priority,
        TaskStatus status,
        Set<String> tags
) {}