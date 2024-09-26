package com.assovio.holerize_api.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.assovio.holerize_api.api.dto.request.PedidoImportacaoRequestDTO;
import com.assovio.holerize_api.api.dto.response.PedidoImportacaoResponseDTO;
import com.assovio.holerize_api.domain.model.PedidoImportacao;

@Component
public class PedidoImportacaoAssembler {

    @Autowired
    private ModelMapper strictModelMapper;

    public PedidoImportacaoResponseDTO toDto(PedidoImportacao pedidoImportacao){
        return strictModelMapper.map(pedidoImportacao, PedidoImportacaoResponseDTO.class);
    }

    public PedidoImportacao toStoreEntity(PedidoImportacaoRequestDTO requestDTO){
        var mapper = strictModelMapper;
        mapper.createTypeMap(PedidoImportacaoRequestDTO.class, PedidoImportacao.class)
        .addMappings(m -> {
            m.skip(PedidoImportacao::setLog);
            m.skip(PedidoImportacao::setTipoErro);
        });
        return mapper.map(requestDTO, PedidoImportacao.class);
    }

    public PedidoImportacao toErrorEntity(PedidoImportacaoRequestDTO requestDTO, PedidoImportacao entity){
        var mapper = strictModelMapper;
        mapper.createTypeMap(PedidoImportacaoRequestDTO.class, PedidoImportacao.class)
        .addMappings(m -> {
            m.skip(PedidoImportacao::setCpf);
            m.skip(PedidoImportacao::setSenha);
            m.skip(PedidoImportacao::setAnoDe);
            m.skip(PedidoImportacao::setAnoAte);
        });
        mapper.map(requestDTO, entity);
        return entity;
    }

    public List<PedidoImportacaoResponseDTO> toCollectionDto(List<PedidoImportacao> entityList){
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
