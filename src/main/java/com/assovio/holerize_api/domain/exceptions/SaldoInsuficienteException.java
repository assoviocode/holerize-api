package com.assovio.holerize_api.domain.exceptions;

public class SaldoInsuficienteException extends BusinessException {
    
    public SaldoInsuficienteException() {
        super();
    }

    public SaldoInsuficienteException(String message) {
        super(message);
    }
}
