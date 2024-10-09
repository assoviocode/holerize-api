package com.assovio.holerize_api.api.dto.response;

import com.assovio.holerize_api.domain.model.Enums.EnumStatusImportacao;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoImportacaoResponseSimpleDTO {
    
    private String uuid;

    private String nome;

    private String cpf;

    private EnumStatusImportacao status;

    @JsonProperty("mes_de")
    private Integer mesDeBaixado;

    @JsonProperty("ano_de")
    private Integer anoDeBaixado;

    @JsonProperty("mes_ate")
    private Integer mesAteBaixado;

    @JsonProperty("ano_ate")
    private Integer anoAteBaixado;

    @JsonProperty("quantidade_anos_solicitados")
    private Integer quantidadeAnosSolicitados;

    @JsonProperty("quantidade_anos_baixados")
    private Integer quantidadeAnosBaixados;

    private byte[] file;
}
