package com.example.week7_day6.exception;

import com.example.week7_day6.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.StringJoiner;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException e, HttpServletRequest request) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        StringJoiner joiner = new StringJoiner("; ");
        for (FieldError error : fieldErrors) {
            String fieldName = error.getField();
            String defaultMessage = error.getDefaultMessage();
            joiner.add(fieldName + ": " + defaultMessage);
        }

        ApiError apiError = new ApiError("INVALID_INPUT", joiner.toString(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(TaskNotFoundException e, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError("TASK_NOT_FOUND", e.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleInvalidJson(HttpMessageNotReadableException e, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError("MALFORMED_JSON", "Invalid JSON format or unknown enum value", request.getRequestURI()));
    }

    @ExceptionHandler(TagAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleTagAlreadyExists(TagAlreadyExistsException e, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiError("TAG_ALREADY_EXISTS", e.getMessage(), request.getRequestURI()));
    }
}
