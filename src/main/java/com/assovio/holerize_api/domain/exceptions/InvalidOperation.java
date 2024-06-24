package com.assovio.holerize_api.domain.exceptions;

public class InvalidOperation extends BusinessException {
    
    public InvalidOperation(){
        super();
    }

    public InvalidOperation(String message){
        super(message);
    }
}
