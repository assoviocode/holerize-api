package com.assovio.holerize_api.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.assovio.holerize_api.api.dto.request.UsuarioRequestDTO;
import com.assovio.holerize_api.api.dto.response.UsuarioResponseDTO;
import com.assovio.holerize_api.domain.model.Usuario;

@Component
public class UsuarioAssembler {
    
    @Autowired
    ModelMapper strictModelMapper;

    public Usuario toEntity(UsuarioRequestDTO requestDTO){
        return strictModelMapper.map(requestDTO, Usuario.class);
    }

    public UsuarioResponseDTO toDto(Usuario usuario) {
        var dto = strictModelMapper.map(usuario, UsuarioResponseDTO.class);
        dto.setProfileImage(usuario.getProfileImage());
        return dto;
    }
}
