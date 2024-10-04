package com.assovio.holerize_api.api.dto.request;

import com.assovio.holerize_api.domain.model.Enums.EnumRole;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequestDTO {
    
    EnumRole role;
    @Email
    String email;
    String login;
    String nome;
    String senha;
    Integer creditos;
}
