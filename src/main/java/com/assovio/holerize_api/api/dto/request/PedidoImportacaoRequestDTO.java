package com.assovio.holerize_api.api.dto.request;

import com.assovio.holerize_api.domain.model.Enums.EnumErrorType;
import com.assovio.holerize_api.domain.model.Enums.EnumStatusImportacao;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoImportacaoRequestDTO {

    private String nome;
    private String cpf;
    private String senha;
    private EnumStatusImportacao status;

    @JsonProperty("tipo_erro")
    private EnumErrorType tipoErro;

    private String log;

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

    private byte[] file;

    public void setCpf(String cpf){
        this.cpf = cpf.replaceAll("[\\.\\-]", "").replaceFirst("^0+", "");
    }
}
