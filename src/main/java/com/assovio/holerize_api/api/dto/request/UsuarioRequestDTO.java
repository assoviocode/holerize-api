package com.assovio.holerize_api.api.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequestDTO {
    
    @Email
    String email;
    String login;
    String nome;
    String senha;
}
