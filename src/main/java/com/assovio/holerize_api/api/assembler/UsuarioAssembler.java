package com.assovio.holerize_api.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.assovio.holerize_api.api.dto.request.UsuarioRequestDTO;
import com.assovio.holerize_api.domain.model.Usuario;

@Component
public class UsuarioAssembler {
    
    @Autowired
    ModelMapper strictModelMapper;

    public Usuario toEntity(UsuarioRequestDTO requestDTO){
        return strictModelMapper.map(requestDTO, Usuario.class);
    }
}
