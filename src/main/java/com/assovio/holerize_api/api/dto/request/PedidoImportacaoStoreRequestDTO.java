package com.assovio.holerize_api.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoImportacaoStoreRequestDTO {
    
    @NotBlank(message = "CPF é obrigatório.")
    private String cpf;

    @NotBlank(message = "Senha é obrigatório.")
    private String senha;

    public void setCpf(String cpf){
        this.cpf = cpf.replace(".", "").replace("-", "").replaceFirst("^0+", "");
    }
}
