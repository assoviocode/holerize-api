package com.assovio.holerize_api.domain.exceptions;

public class InvalidOperationException extends BusinessException {
    
    public InvalidOperationException(){
        super();
    }

    public InvalidOperationException(String message){
        super(message);
    }
}
