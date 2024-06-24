package com.assovio.holerize_api.domain.exceptions;

public abstract class BusinessException extends Exception {
    
    protected BusinessException(){
        super();
    }

    protected BusinessException(String message){
        super(message);
    }
}