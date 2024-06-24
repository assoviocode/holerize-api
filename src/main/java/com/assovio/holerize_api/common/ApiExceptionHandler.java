package com.assovio.holerize_api.common;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.assovio.holerize_api.domain.exceptions.InvalidOperation;
import com.assovio.holerize_api.domain.exceptions.RegisterNotFound;

import org.springframework.validation.FieldError;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMissingArguments(MethodArgumentNotValidException ex){
        List<FieldError> listErrors = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(listErrors.stream().map(ValidationErrorData::new).toList());
    }

    private record ValidationErrorData(String campo, String condicao){

        public ValidationErrorData(FieldError error){
            this(error.getField().replaceFirst("([a-z])([A-Z])", "$1_$2").toLowerCase(), error.getDefaultMessage());
        }
    }

    @ExceptionHandler(RegisterNotFound.class)
    public ResponseEntity<?> handleRegisterNotFound(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(InvalidOperation.class)
    public ResponseEntity<String> handleInvalidOperation(InvalidOperation ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
