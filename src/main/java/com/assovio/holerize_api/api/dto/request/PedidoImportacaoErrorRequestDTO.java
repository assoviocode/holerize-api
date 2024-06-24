package com.assovio.holerize_api.api.dto.request;

import com.assovio.holerize_api.domain.model.EnumErrorType;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoImportacaoErrorRequestDTO {
    
    @NotBlank
    private String log;

    @NotNull
    @Enumerated(EnumType.STRING)
    @JsonProperty("tipo_erro")
    private EnumErrorType tipoErro;
}
