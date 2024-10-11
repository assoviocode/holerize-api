package com.assovio.holerize_api.common;

import java.util.TimeZone;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.assovio.holerize_api.api.dto.response.UsuarioResponseDTO;
import com.assovio.holerize_api.domain.model.Usuario;

@Configuration
public class ModelMapperConfig {
    
    @Bean
    public ModelMapper strictModelMapper() {
        ModelMapper modelMapperStrict = new ModelMapper();
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
        modelMapperStrict.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapperStrict.createTypeMap(Usuario.class, UsuarioResponseDTO.class)
            .addMappings(mapper -> {
                mapper.skip(UsuarioResponseDTO::setProfileImage);
            });

        return modelMapperStrict;
    }
    
}
