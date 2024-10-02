package com.assovio.holerize_api.api.exception.handler;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.assovio.holerize_api.domain.exceptions.InvalidOperationException;
import com.assovio.holerize_api.domain.exceptions.RegisterNotFoundException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
                List<FieldError> listErrors = ex.getFieldErrors();
                return ResponseEntity.badRequest().body(listErrors.stream().map(ValidationErrorData::new).toList());
    }

    private record ValidationErrorData(String campo, String condicao){

        public ValidationErrorData(FieldError error){
            this(error.getField().replaceFirst("([a-z])([A-Z])", "$1_$2").toLowerCase(), error.getDefaultMessage());
        }
    }

    @ExceptionHandler(RegisterNotFoundException.class)
    public ResponseEntity<?> handleRegisterNotFound(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<String> handleInvalidOperation(InvalidOperationException ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
