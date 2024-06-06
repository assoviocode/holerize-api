package com.assovio.holerize_api.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.assovio.holerize_api.api.dto.request.PedidoImportacaoErrorRequestDTO;
import com.assovio.holerize_api.api.dto.request.PedidoImportacaoStoreRequestDTO;
import com.assovio.holerize_api.api.dto.response.PedidoImportacaoResponseDTO;
import com.assovio.holerize_api.domain.model.PedidoImportacao;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class PedidoImportacaoAssembler {
    
    private ModelMapper stricModelMapper;

    public PedidoImportacaoResponseDTO toDto(PedidoImportacao pedidoImportacao){
        return this.stricModelMapper.map(pedidoImportacao, PedidoImportacaoResponseDTO.class);
    }

    public PedidoImportacao toEntity(PedidoImportacaoStoreRequestDTO requestDTO){
        return this.stricModelMapper.map(requestDTO, PedidoImportacao.class);
    }

    public PedidoImportacao updateEntity(PedidoImportacaoErrorRequestDTO requestDTO, PedidoImportacao entity){
        this.stricModelMapper.map(requestDTO, entity);
        return entity;
    }

    public List<PedidoImportacaoResponseDTO> toCollectionDto(List<PedidoImportacao> entityList){
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
