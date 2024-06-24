package com.assovio.holerize_api.domain.exceptions;

public class RegisterNotFound extends BusinessException {
    
    public RegisterNotFound(){
        super();
    }

    public RegisterNotFound(String message){
        super(message);
    }
}
