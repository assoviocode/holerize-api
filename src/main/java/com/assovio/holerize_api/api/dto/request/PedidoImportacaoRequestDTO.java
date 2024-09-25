package com.assovio.holerize_api.api.dto.request;

import com.assovio.holerize_api.domain.model.Enums.EnumErrorType;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoImportacaoRequestDTO {
    
    private String cpf;
    private String senha;

    @JsonProperty("tipo_erro")
    private EnumErrorType tipoErro;

    private String log;

    @JsonProperty("ano_de")
    private Integer anoDe;
    
    @JsonProperty("ano_ate")
    private Integer anoAte;

    public void setCpf(String cpf){
        this.cpf = cpf.replaceAll("[\\.\\-]", "").replaceFirst("^0+", "");
    }
}
