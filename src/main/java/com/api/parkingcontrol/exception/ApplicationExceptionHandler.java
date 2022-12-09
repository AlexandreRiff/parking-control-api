package com.api.parkingcontrol.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Map<String, String> handleHttpRequestMethodNotSupported() {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", "método não suportado");
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String, String> handleHttpMessageNotReadable() {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", "corpo de solicitação necessário");
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Map<String, String> handleMethodArgumentTypeMismatch() {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", "argumento inválido");
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put("message", error.getDefaultMessage());
        });
        return errorMap;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public Map<String, String> handleResourceNotFound(ResourceNotFoundException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", "recurso não encontrado para o Id: " + exception.getId());
        return errorMap;
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<Object> handleBusinessRule(BusinessRuleException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());
        return new ResponseEntity<>(errorMap, exception.getHttpStatus());
    }

}
