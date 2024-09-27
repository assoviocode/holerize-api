package com.assovio.holerize_api.domain.validator.pedidoimportacao;

import com.assovio.holerize_api.api.dto.request.PedidoImportacaoRequestDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PedidoImportacaoUpdateValidator implements ConstraintValidator<PedidoImportacaoUpdateValid, PedidoImportacaoRequestDTO> {

    @Override
    public boolean isValid(@PedidoImportacaoStoreValid PedidoImportacaoRequestDTO value, ConstraintValidatorContext context) {
        
        return true;
    }
}
