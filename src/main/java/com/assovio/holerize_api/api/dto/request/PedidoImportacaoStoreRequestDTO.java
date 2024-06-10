package com.assovio.holerize_api.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoImportacaoStoreRequestDTO {
    
    @NotBlank(message = "CPF é obrigatório.")
    private String cpf;

    @NotBlank(message = "Senha é obrigatório.")
    private String senha;

    @NotNull(message = "Ano inicial é obrigatório.")
    @JsonProperty("ano_de")
    private Integer anoDe;

    @NotNull(message = "Ano final é obrigatório.")
    @JsonProperty("ano_ate")
    private Integer anoAte;

    public void setCpf(String cpf){
        this.cpf = cpf.replace(".", "").replace("-", "").replaceFirst("^0+", "");
    }
}
