package com.assovio.holerize_api.domain.validator.pedidoimportacao;

import com.assovio.holerize_api.api.dto.request.PedidoImportacaoRequestDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PedidoImportacaoFinishValidator implements ConstraintValidator<PedidoImportacaoFinishValid, PedidoImportacaoRequestDTO> {

    @Override
    public boolean isValid(PedidoImportacaoRequestDTO value, ConstraintValidatorContext context) {
        if (value.getQuantidadeAnosBaixados() == null){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("O campo quantidade_anos_baixados não pode ser nulo")
                .addPropertyNode("quantidade_anos_baixados")
                .addConstraintViolation();
            
            return false;
        } else if (value.getFile() == null || !(value.getFile().length > 1)){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("O campo file não pode ser nulo ou vazio")
                .addPropertyNode("file")
                .addConstraintViolation();
            
            return false;
        } else if (value.getAnoAte() == null){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("O campo ano_ate não pode ser nulo ou vazio")
                .addPropertyNode("ano_ate")
                .addConstraintViolation();
            
            return false;
        } else if (value.getMesAte() == null){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("O campo mes_ate não pode ser nulo ou vazio")
                .addPropertyNode("mes_ate")
                .addConstraintViolation();
            
            return false;
        } else if (value.getAnoDe() == null){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("O campo ano_de não pode ser nulo ou vazio")
                .addPropertyNode("ano_de")
                .addConstraintViolation();
            
            return false;
        } else if (value.getMesDe() == null){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("O campo mes_de não pode ser nulo ou vazio")
                .addPropertyNode("mes_de")
                .addConstraintViolation();
            
            return false;
        }

        return true;
    }

}