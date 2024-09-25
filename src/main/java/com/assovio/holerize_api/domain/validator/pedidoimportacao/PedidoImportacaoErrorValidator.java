package com.assovio.holerize_api.domain.validator.pedidoimportacao;

import com.assovio.holerize_api.api.dto.request.PedidoImportacaoRequestDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PedidoImportacaoErrorValidator implements ConstraintValidator<PedidoImportacaoErrorValid, PedidoImportacaoRequestDTO> {

    @Override
    public boolean isValid(PedidoImportacaoRequestDTO value, ConstraintValidatorContext context) {
        if (value.getLog() == null || value.getLog().isBlank()){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("O campo log não pode ser nulo ou vazio")
                .addPropertyNode("log")
                .addConstraintViolation();
            
            return false;
        } else if (value.getTipoErro() == null){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("O campo tipo_erro não pode ser nulo ou vazio")
                .addPropertyNode("tipo_erro")
                .addConstraintViolation();
            
            return false;
        }

        return true;
    }
    
}
