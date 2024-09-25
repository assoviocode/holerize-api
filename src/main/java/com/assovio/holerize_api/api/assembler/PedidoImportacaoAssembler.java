package com.assovio.holerize_api.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.assovio.holerize_api.api.dto.request.PedidoImportacaoRequestDTO;
import com.assovio.holerize_api.api.dto.response.PedidoImportacaoResponseDTO;
import com.assovio.holerize_api.domain.model.PedidoImportacao;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class PedidoImportacaoAssembler {
    
    private ModelMapper strictModelMapper;

    public PedidoImportacaoResponseDTO toDto(PedidoImportacao pedidoImportacao){
        return this.strictModelMapper.map(pedidoImportacao, PedidoImportacaoResponseDTO.class);
    }

    public PedidoImportacao toEntity(PedidoImportacaoRequestDTO requestDTO){
        return this.strictModelMapper.map(requestDTO, PedidoImportacao.class);
    }

    public PedidoImportacao toEntity(PedidoImportacaoRequestDTO requestDTO, PedidoImportacao entity){
        this.strictModelMapper.map(requestDTO, entity);
        return entity;
    }

    public List<PedidoImportacaoResponseDTO> toCollectionDto(List<PedidoImportacao> entityList){
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
