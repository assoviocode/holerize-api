package com.assovio.holerize_api.domain.validator.pedidoexecucao;

import com.assovio.holerize_api.api.dto.request.PedidoExecucaoRequestDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PedidoExecucaoStoreValidator implements ConstraintValidator<PedidoExecucaoFinishValid, PedidoExecucaoRequestDTO> {

    @Override
    public boolean isValid(PedidoExecucaoRequestDTO value, ConstraintValidatorContext context) {
        if (value.getMesDe() == null){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("O campo mes_de não pode ser nulo")
                .addPropertyNode("mes_de")
                .addConstraintViolation();
            
            return false;
        } else if (value.getAnoDe() == null){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("O campo ano_de não pode ser nulo")
                .addPropertyNode("ano_de")
                .addConstraintViolation();
            
            return false;
        } else if (value.getMesAte() == null){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("O campo mes_ate não pode ser nulo")
                .addPropertyNode("mes_ate")
                .addConstraintViolation();
            
            return false;
        } else if (value.getAnoAte() == null){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("O campo ano_ate não pode ser nulo")
                .addPropertyNode("ano_ate")
                .addConstraintViolation();
            
            return false;
        } else if (value.getPedidoImportacaoUuid() == null || value.getPedidoImportacaoUuid().isBlank()){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("O campo pedido_importacao_uuid não pode ser nulo ou vazio")
                .addPropertyNode("pedido_importacao_uuid")
                .addConstraintViolation();
            
            return false;
        }

        return true;
    }
    
}
