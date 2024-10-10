package com.assovio.holerize_api.api.dto.response;

import com.assovio.holerize_api.domain.model.Enums.EnumErrorType;
import com.assovio.holerize_api.domain.model.Enums.EnumStatusImportacao;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoExecucaoResponseDTO {
    
    private String uuid;

    private String cpf;

    private String senha;

    private EnumStatusImportacao status;

    private String log;

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

    @JsonProperty("quantidade_anos_solicitados")
    private Integer quantidadeAnosSolicitados;

    @JsonProperty("quantidade_anos_baixados")
    private Integer quantidadeAnosBaixados;

    @JsonProperty("total_vinculos_baixados")
    private Integer totalVinculosBaixados;

    private byte[] file;

    @JsonProperty("pedido_importacao_uuid")
    private String pedidoImportacaoUuid;
}
