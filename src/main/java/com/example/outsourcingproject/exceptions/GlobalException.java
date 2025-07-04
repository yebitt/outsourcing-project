package com.example.outsourcingproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    // UserException 예외 처리
    @ExceptionHandler(UserException.class)
    public ResponseEntity<String> handleUserException(UserException e) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    // 유효성 검사 에러 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException e) {

        Map<String, String> response = new LinkedHashMap<>();
        e.getBindingResult()
                .getFieldErrors()
                .forEach(error -> response.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // request body가 비어있을 때 에러 처리
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {

        String response = "body는 null이 될 수 없습니다.";

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

}