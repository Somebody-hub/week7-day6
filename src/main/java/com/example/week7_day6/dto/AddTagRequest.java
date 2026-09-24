package com.example.week7_day6.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddTagRequest(
        @NotBlank(message = "Tag name must not be empty")
        @Size(max = 20, message = "Tag name cannot exceed 20 characters")
        String tag
) {
}
