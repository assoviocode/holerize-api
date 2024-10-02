package com.assovio.holerize_api.domain.exceptions;

public class RegisterNotFoundException extends BusinessException {
    
    public RegisterNotFoundException(){
        super();
    }

    public RegisterNotFoundException(String message){
        super(message);
    }
}
