package com.assovio.holerize_api.api.assembler;

import java.util.TimeZone;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.assovio.holerize_api.api.dto.request.PedidoExecucaoRequestDTO;
import com.assovio.holerize_api.api.dto.response.PedidoExecucaoResponseDTO;
import com.assovio.holerize_api.domain.model.PedidoExecucao;

@Component
public class PedidoExecucaoAssembler {
    
    public PedidoExecucaoResponseDTO toDto(PedidoExecucao entity){
        ModelMapper modelMapper = new ModelMapper();
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(PedidoExecucao.class, PedidoExecucaoResponseDTO.class)
            .<String>addMapping(src -> src.getPedidoImportacao().getCpf(), (des, value) -> des.setCpf(value))
            .<String>addMapping(src -> src.getPedidoImportacao().getSenha(), (des, value) -> des.setSenha(value))
            .<Integer>addMapping(src -> src.getPedidoImportacao().getQuantidadeAnosSolicitados(), (des, value) -> des.setQuantidadeAnosSolicitados(value))
            .<Integer>addMapping(src -> src.getPedidoImportacao().getQuantidadeAnosBaixados(), (des, value) -> des.setQuantidadeAnosBaixados(value))
            .<String>addMapping(src -> src.getPedidoImportacao().getUuid(), (des, value) -> des.setPedidoImportacaoUuid(value));

        var dto = modelMapper.map(entity, PedidoExecucaoResponseDTO.class);
        dto.setFile(entity.getPedidoImportacao().getFile());
        return dto;
    }

    public PedidoExecucao toFinishEntity(PedidoExecucaoRequestDTO requestDTO, PedidoExecucao entity) {
        ModelMapper modelMapper = new ModelMapper();
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(PedidoExecucaoRequestDTO.class, PedidoExecucao.class)
            .<Integer>addMapping(src -> src.getMesDe(), (des, value) -> des.getPedidoImportacao().setMesDeBaixado(value))
            .<Integer>addMapping(src -> src.getAnoDe(), (des, value) -> des.getPedidoImportacao().setAnoDeBaixado(value))
            .<Integer>addMapping(src -> src.getMesAte(), (des, value) -> des.getPedidoImportacao().setMesAteBaixado(value))
            .<Integer>addMapping(src -> src.getAnoAte(), (des, value) -> des.getPedidoImportacao().setAnoAteBaixado(value))
            .<Integer>addMapping(src -> src.getQuantidadeAnosBaixados(), (des, value) -> des.getPedidoImportacao().setQuantidadeAnosBaixados(value))
            .addMappings(mapper -> {
                mapper.skip(PedidoExecucao::setLog);
                mapper.skip(PedidoExecucao::setTipoErro);
            });
        modelMapper.map(requestDTO, entity);
        entity.getPedidoImportacao().setFile(requestDTO.getFile());
        return entity;
    }

    public PedidoExecucao toStoreEntity(PedidoExecucaoRequestDTO requestDTO) {
        ModelMapper modelMapper = new ModelMapper();
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(PedidoExecucaoRequestDTO.class, PedidoExecucao.class)
            .addMappings(mapper -> {
                mapper.skip(PedidoExecucao::setLog);
                mapper.skip(PedidoExecucao::setTipoErro);
            });
        return modelMapper.map(requestDTO, PedidoExecucao.class);
    }

    public PedidoExecucao toErrorEntity(PedidoExecucaoRequestDTO requestDTO, PedidoExecucao entity) {
        ModelMapper modelMapper = new ModelMapper();
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(PedidoExecucaoRequestDTO.class, PedidoExecucao.class)
            .addMappings(mapper -> {
                mapper.skip(PedidoExecucao::setMesDe);
                mapper.skip(PedidoExecucao::setAnoDe);
                mapper.skip(PedidoExecucao::setMesAte);
                mapper.skip(PedidoExecucao::setAnoAte);
            });
        modelMapper.map(requestDTO, entity);
        return entity;
    }
}
