package com.assovio.holerize_api.api.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioResponseDTO {
    
    private Long id;
    private String email;
    private String login;
    private String nome;
    private String senha;
    private String token;
}
