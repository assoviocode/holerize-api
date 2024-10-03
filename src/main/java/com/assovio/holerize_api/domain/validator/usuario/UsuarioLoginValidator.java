package com.assovio.holerize_api.domain.validator.usuario;

import com.assovio.holerize_api.api.dto.request.UsuarioRequestDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsuarioLoginValidator implements ConstraintValidator<UsuarioLoginValid, UsuarioRequestDTO>{

    @Override
    public boolean isValid(UsuarioRequestDTO value, ConstraintValidatorContext context) {
        
        if ((value.getLogin() == null || value.getLogin().isBlank()) && (value.getEmail() == null || value.getEmail().isBlank())){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("O campo login e e-mail não podem ser nulos ou vazios")
                .addPropertyNode("login, email")
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