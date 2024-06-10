package com.assovio.holerize_api.common;

import java.util.TimeZone;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.assovio.holerize_api.api.dto.request.PedidoImportacaoErrorRequestDTO;
import com.assovio.holerize_api.api.dto.request.PedidoImportacaoStoreRequestDTO;
import com.assovio.holerize_api.api.dto.response.PedidoImportacaoResponseDTO;
import com.assovio.holerize_api.domain.model.EnumStatusImportacao;
import com.assovio.holerize_api.domain.model.PedidoImportacao;

@Configuration
public class ModelMapperConfig {
    
    @Bean
    public ModelMapper strictModelMapper() {
        ModelMapper modelMapperStrict = new ModelMapper();

        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));

        modelMapperStrict.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapperStrict.createTypeMap(PedidoImportacaoStoreRequestDTO.class, PedidoImportacao.class)
            .<String>addMapping(src -> src.getCpf(), (des, value) -> des.setCpf(value))
            .<String>addMapping(src -> src.getSenha(), (des, value) -> des.setSenha(value))
            .<Integer>addMapping(src -> src.getAnoDe(), (des, value) -> des.setAnoDe(value))
            .<Integer>addMapping(src -> src.getAnoAte(), (des, value) -> des.setAnoAte(value));

        modelMapperStrict.createTypeMap(PedidoImportacaoErrorRequestDTO.class, PedidoImportacao.class)
            .<String>addMapping(src -> src.getLog(), (des, value) -> des.setLog(value));

        modelMapperStrict.createTypeMap(PedidoImportacao.class, PedidoImportacaoResponseDTO.class)
            .<Long>addMapping(src -> src.getId(), (des, value) -> des.setId(value))
            .<String>addMapping(src -> src.getCpf(), (des, value) -> des.setCpf(value))
            .<String>addMapping(src -> src.getSenha(), (des, value) -> des.setSenha(value))
            .<EnumStatusImportacao>addMapping(src -> src.getStatus(), (des, value) -> des.setStatus(value))
            .<String>addMapping(src -> src.getLog(), (des, value) -> des.setLog(value))
            .<Integer>addMapping(src -> src.getAnoDe(), (des, value) -> des.setAnoDe(value))
            .<Integer>addMapping(src -> src.getAnoAte(), (des, value) -> des.setAnoAte(value));

        return modelMapperStrict;
    }
    
}
