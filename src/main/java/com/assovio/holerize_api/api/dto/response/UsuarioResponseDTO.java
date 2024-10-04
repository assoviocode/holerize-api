package com.assovio.holerize_api.api.dto.response;

import com.assovio.holerize_api.domain.model.Enums.EnumRole;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponseDTO {
    
    private Long id;

    private String nome;

    private String login;

    private String email;

    @JsonProperty("perfil")
    private EnumRole role;

    private String token;
}
