package com.assovio.holerize_api.domain.validator;

import com.assovio.holerize_api.api.dto.request.UsuarioRequestDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsuarioLoginValidator implements ConstraintValidator<UsuarioLoginValid, UsuarioRequestDTO>{

    @Override
    public boolean isValid(UsuarioRequestDTO value, ConstraintValidatorContext context) {
        
        if (value.getLogin() == null || value.getLogin().isBlank()){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("O campo login não pode ser nulo ou vazio")
                .addPropertyNode("login")
                .addConstraintViolation();
            
            return false;
        } else if (value.getSenha() == null || value.getSenha().isBlank()){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("O campo senha não pode ser nulo ou vazio")
                .addPropertyNode("senha")
                .addConstraintViolation();
            
            return false;
        }

        return true;
    }
}