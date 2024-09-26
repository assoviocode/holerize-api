package com.assovio.holerize_api.api.assembler;

import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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
        return modelMapper.map(pedidoImportacao, PedidoImportacaoResponseDTO.class);
    }

    public PedidoImportacao toStoreEntity(PedidoImportacaoRequestDTO requestDTO){
        ModelMapper modelMapper = new ModelMapper();
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(PedidoImportacaoRequestDTO.class, PedidoImportacao.class)
        .addMappings(mapper -> {
            mapper.skip(PedidoImportacao::setLog);
            mapper.skip(PedidoImportacao::setTipoErro);
            mapper.skip(PedidoImportacao::setQuantidadeAnosBaixados);
            mapper.skip(PedidoImportacao::setFile);
        });
        return modelMapper.map(requestDTO, PedidoImportacao.class);
    }

    public PedidoImportacao toErrorEntity(PedidoImportacaoRequestDTO requestDTO, PedidoImportacao entity){
        ModelMapper modelMapper = new ModelMapper();
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.createTypeMap(PedidoImportacaoRequestDTO.class, PedidoImportacao.class)
        .addMappings(mapper -> {
            mapper.skip(PedidoImportacao::setCpf);
            mapper.skip(PedidoImportacao::setSenha);
            mapper.skip(PedidoImportacao::setMesDe);
            mapper.skip(PedidoImportacao::setAnoDe);
            mapper.skip(PedidoImportacao::setMesAte);
            mapper.skip(PedidoImportacao::setAnoAte);
            mapper.skip(PedidoImportacao::setQuantidadeAnosBaixados);
            mapper.skip(PedidoImportacao::setFile);
        });
        modelMapper.map(requestDTO, entity);
        return entity;
    }

    public List<PedidoImportacaoResponseDTO> toCollectionDto(List<PedidoImportacao> entityList){
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
