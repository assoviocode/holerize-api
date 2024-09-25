package com.assovio.holerize_api.domain.validator.usuario;

import com.assovio.holerize_api.api.dto.request.UsuarioRequestDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Valid;

public class UsuarioStoreValidator implements ConstraintValidator<UsuarioStoreValid, UsuarioRequestDTO>{

    @Override
    public boolean isValid(@Valid UsuarioRequestDTO value, ConstraintValidatorContext context) {
        
        if (value.getNome() == null || value.getNome().isBlank()){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("O campo nome não pode ser nulo ou vazio")
                .addPropertyNode("nome")
                .addConstraintViolation();
            
            return false;
        } else if (value.getEmail() == null || value.getEmail().isBlank()){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("O campo e-mail não pode ser nulo ou vazio")
                .addPropertyNode("email")
                .addConstraintViolation();
            
            return false;
        } else if (value.getLogin() == null || value.getLogin().isBlank()){
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