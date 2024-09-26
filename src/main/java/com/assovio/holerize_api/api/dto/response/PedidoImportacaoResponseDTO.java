package com.assovio.holerize_api.api.dto.response;

import com.assovio.holerize_api.domain.model.Enums.EnumErrorType;
import com.assovio.holerize_api.domain.model.Enums.EnumStatusImportacao;
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

    @Enumerated(EnumType.STRING)
    @JsonProperty("tipo_erro")
    private EnumErrorType tipoErro;

    @JsonProperty("mes_de")
    private Integer mesDe;
    
    @JsonProperty("ano_de")
    private Integer anoDe;

    @JsonProperty("mes_ate")
    private Integer mesAte;

    @JsonProperty("ano_ate")
    private Integer anoAte;
}
