package com.assovio.holerize_api.api.dto.response;

import com.assovio.holerize_api.domain.model.EnumStatusImportacao;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoImportacaoResponseDTO {
    
    private Long id;

    private String cpf;

    private String senha;

    @Enumerated(EnumType.STRING)
    private EnumStatusImportacao status;

    private String log;
    
    @JsonProperty("ano_de")
    private Integer anoDe;

    @JsonProperty("ano_ate")
    private Integer anoAte;
}
