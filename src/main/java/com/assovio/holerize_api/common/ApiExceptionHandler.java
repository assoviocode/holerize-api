package com.assovio.holerize_api.common;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.FieldError;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleError400(MethodArgumentNotValidException ex){
        List<FieldError> listErrors = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(listErrors.stream().map(ValidationErrorData::new).toList());
    }

    private record ValidationErrorData(String field, String message){

        public ValidationErrorData(FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
