package com.example.week7_day6.dto;

import com.example.week7_day6.model.TaskPriority;
import com.example.week7_day6.model.TaskStatus;

import java.util.Map;

public record TaskStatisticsResponse(
        Map<TaskStatus, Integer> byStatus,
        Map<TaskPriority, Integer> byPriority
) {
}
