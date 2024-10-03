package com.assovio.holerize_api.api.exception.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.assovio.holerize_api.domain.exceptions.InvalidOperationException;
import com.assovio.holerize_api.domain.exceptions.RegisterNotFoundException;
import com.assovio.holerize_api.domain.exceptions.SaldoInsuficienteException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
                List<ValidationErrorData> errors = ex.getBindingResult()
                                            .getFieldErrors()
                                            .stream()
                                            .map(ValidationErrorData::new).toList();
                    
                return handleExceptionInternal(ex, errors, headers, status, request);
    }

    @Override
    @Nullable
    protected ResponseEntity<Object> handleHandlerMethodValidationException(@NonNull HandlerMethodValidationException ex,
            @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        
        List<ValidationErrorData> errors = ex.getAllErrors()
            .stream()
            .filter(error -> error instanceof FieldError)
            .map(error -> new ValidationErrorData((FieldError) error))
            .collect(Collectors.toList());

        return new ResponseEntity<>(errors, new HttpHeaders(), status);
    }

    private record ValidationErrorData(String campo, String condicao){

        public ValidationErrorData(FieldError error){
            this(error.getField().replaceFirst("([a-z])([A-Z])", "$1_$2").toLowerCase(), error.getDefaultMessage());
        }
    }

    @ExceptionHandler(RegisterNotFoundException.class)
    public ResponseEntity<?> handleRegisterNotFound(RegisterNotFoundException exception, WebRequest request){
        return handleExceptionInternal(exception, null, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<?> handleInvalidOperation(InvalidOperationException exeption, WebRequest request){
        return handleExceptionInternal(exeption, exeption.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<?> handleSaldoInsuficiente(SaldoInsuficienteException exception, WebRequest request){
        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.PAYMENT_REQUIRED, request);
    }
}
