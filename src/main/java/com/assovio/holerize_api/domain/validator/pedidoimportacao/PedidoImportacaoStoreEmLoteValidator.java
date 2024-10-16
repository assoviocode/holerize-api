package com.assovio.holerize_api.domain.validator.pedidoimportacao;

import java.util.List;

import com.assovio.holerize_api.api.dto.request.PedidoImportacaoRequestDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PedidoImportacaoStoreEmLoteValidator implements ConstraintValidator<PedidoImportacaoStoreEmLoteValid, List<PedidoImportacaoRequestDTO>> {

    @Override
    public boolean isValid(List<PedidoImportacaoRequestDTO> value, ConstraintValidatorContext context) {
        for (PedidoImportacaoRequestDTO pedidoImportacaoRequestDTO : value) {
            validate(pedidoImportacaoRequestDTO);
        }
        return true;
    }

    private boolean validate(@PedidoImportacaoStoreValid PedidoImportacaoRequestDTO dto){
        return true;
    }
    
}
