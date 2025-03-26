package com.graduation.rbackend.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 明确设置 401 状态码
    public ResponseEntity<?> handleBadCredentialsException(MethodArgumentNotValidException ex) {
        log.warn("❗ 捕获到验证异常，错误详情: {}", ex.getMessage());  // ✅ 添加调试日志

        String errorMessage=ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(":"));
        return ResponseEntity.status(401).body(Map.of("error", errorMessage));
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleException(Exception ex) {
        log.error("❗ 捕获到异常，错误详情: {}", ex.getMessage());  // ✅ 添加调试日志
        return ResponseEntity.status(500).body(Map.of("error", "服务器内部错误"));
    }
}