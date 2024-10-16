package com.assovio.holerize_api.domain.validator.pedidoimportacao;

import com.assovio.holerize_api.api.dto.request.PedidoImportacaoRequestDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PedidoImportacaoStoreValidator implements ConstraintValidator<PedidoImportacaoStoreValid, PedidoImportacaoRequestDTO> {

    @Override
    public boolean isValid(PedidoImportacaoRequestDTO value, ConstraintValidatorContext context) {
        if (value.getCpf() == null || value.getCpf().isBlank()){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("O campo cpf não pode ser nulo ou vazio")
                .addPropertyNode("cpf")
                .addConstraintViolation();
            
            return false;
        } else if (value.getSenha() == null || value.getSenha().isBlank()){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("O campo senha não pode ser nulo ou vazio")
                .addPropertyNode("senha")
                .addConstraintViolation();
            
            return false;
        } else if (value.getAnoDe() == null){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("O campo ano_de não pode ser nulo")
                .addPropertyNode("ano_de")
                .addConstraintViolation();
            
            return false;
        } else if (value.getMesDe() == null){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("O campo mes_de não pode ser nulo")
                .addPropertyNode("mes_de")
                .addConstraintViolation();
            
            return false;
        } else if (value.getQuantidadeAnosSolicitados() == null){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("O campo quantidade_anos_solicitados não pode ser nulo")
                .addPropertyNode("quantidade_anos_solicitados")
                .addConstraintViolation();
            
            return false;
        }
        
        return true;
    }
    
}
