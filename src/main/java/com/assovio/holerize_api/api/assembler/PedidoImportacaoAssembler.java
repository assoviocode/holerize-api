package com.assovio.holerize_api.api.assembler;

import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.assovio.holerize_api.api.dto.request.PedidoImportacaoRequestDTO;
import com.assovio.holerize_api.api.dto.response.PedidoImportacaoResponseDTO;
import com.assovio.holerize_api.domain.model.PedidoImportacao;

@Component
public class PedidoImportacaoAssembler {

    public PedidoImportacaoResponseDTO toDto(PedidoImportacao pedidoImportacao){
        ModelMapper modelMapper = new ModelMapper();
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(PedidoImportacao.class, PedidoImportacaoResponseDTO.class)
        .addMappings(mapper -> {
            mapper.skip(PedidoImportacaoResponseDTO::setFile);
        });
        var dto = modelMapper.map(pedidoImportacao, PedidoImportacaoResponseDTO.class);
        dto.setFile(pedidoImportacao.getFile());
        return dto;
    }

    public PedidoImportacao toStoreEntity(PedidoImportacaoRequestDTO requestDTO){
        ModelMapper modelMapper = new ModelMapper();
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(PedidoImportacaoRequestDTO.class, PedidoImportacao.class)
        .addMappings(mapper -> {
            mapper.skip(PedidoImportacao::setStatus);
            mapper.skip(PedidoImportacao::setLog);
            mapper.skip(PedidoImportacao::setTipoErro);
            mapper.skip(PedidoImportacao::setAnoAte);
            mapper.skip(PedidoImportacao::setMesAte);
            mapper.skip(PedidoImportacao::setQuantidadeAnosBaixados);
            mapper.skip(PedidoImportacao::setTotalVinculosBaixados);
            mapper.skip(PedidoImportacao::setFile);
            mapper.skip(PedidoImportacao::setUsuario);
        });
        return modelMapper.map(requestDTO, PedidoImportacao.class);
    }

    public PedidoImportacao toUpdateEntity(PedidoImportacaoRequestDTO requestDTO, PedidoImportacao entity){
        ModelMapper modelMapper = new ModelMapper();
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(PedidoImportacaoRequestDTO.class, PedidoImportacao.class)
        .addMappings(mapper -> {
            mapper.skip(PedidoImportacao::setFile);
            mapper.skip(PedidoImportacao::setUsuario);
        });
        modelMapper.map(requestDTO, entity);
        entity.setFile(requestDTO.getFile());
        return entity;
    }

    public List<PedidoImportacaoResponseDTO> toCollectionDto(List<PedidoImportacao> entityList){
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }

    public Page<PedidoImportacaoResponseDTO> toPageDTO(Page<PedidoImportacao> pedidoImportacaoPage) {
        return pedidoImportacaoPage.map(this::toDto);
    }
}
