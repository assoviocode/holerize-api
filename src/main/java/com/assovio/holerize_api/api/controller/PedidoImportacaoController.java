package com.assovio.holerize_api.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assovio.holerize_api.api.assembler.PedidoImportacaoAssembler;
import com.assovio.holerize_api.api.dto.request.PedidoImportacaoRequestDTO;
import com.assovio.holerize_api.api.dto.response.PedidoImportacaoResponseDTO;
import com.assovio.holerize_api.domain.exceptions.BusinessException;
import com.assovio.holerize_api.domain.exceptions.InvalidOperation;
import com.assovio.holerize_api.domain.exceptions.RegisterNotFound;
import com.assovio.holerize_api.domain.model.PedidoImportacao;
import com.assovio.holerize_api.domain.model.Enums.EnumStatusImportacao;
import com.assovio.holerize_api.domain.service.PedidoImportacaoService;
import com.assovio.holerize_api.domain.validator.pedidoimportacao.PedidoImportacaoErrorValid;
import com.assovio.holerize_api.domain.validator.pedidoimportacao.PedidoImportacaoStoreValid;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("pedidosImportacao")
public class PedidoImportacaoController {
    
    private PedidoImportacaoAssembler pedidoImportacaoAssembler;
    private PedidoImportacaoService pedidoImportacaoService;

    @PostMapping
    public ResponseEntity<PedidoImportacaoResponseDTO> store(@RequestBody @PedidoImportacaoStoreValid PedidoImportacaoRequestDTO requestDTO) {
        PedidoImportacao newPedidoImportacao = pedidoImportacaoAssembler.toStoreEntity(requestDTO);
        newPedidoImportacao = pedidoImportacaoService.save(newPedidoImportacao);
        return ResponseEntity.ok(pedidoImportacaoAssembler.toDto(newPedidoImportacao));
    }

    @GetMapping("proximo")
    public ResponseEntity<PedidoImportacaoResponseDTO> next() throws RegisterNotFound {
        var optionalPedido = pedidoImportacaoService.getNext();

        if (!optionalPedido.isPresent())
            throw new RegisterNotFound("Não encontrado próximo registro na fila");

        var pedido = optionalPedido.get();
        pedido.setStatus(EnumStatusImportacao.EM_ANDAMENTO);
        pedido = pedidoImportacaoService.save(pedido);
        return ResponseEntity.ok(pedidoImportacaoAssembler.toDto(pedido));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> destroy(@PathVariable Long id) throws RegisterNotFound {
        var optionalPedido = pedidoImportacaoService.getById(id);
        
        if (!optionalPedido.isPresent())
            throw new RegisterNotFound("Pedido de importação não encontrado");

        var pedidoImportacao = optionalPedido.get();
        pedidoImportacaoService.logicalDelete(pedidoImportacao);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}/finalizado")
    public ResponseEntity<PedidoImportacaoResponseDTO> finish(@PathVariable Long id) throws RegisterNotFound {
        var optionalPedido = pedidoImportacaoService.getById(id);

        if (!optionalPedido.isPresent())
            throw new RegisterNotFound("Pedido de importação não encontrado");

        var pedidoImportacao = optionalPedido.get();
        pedidoImportacao.setStatus(EnumStatusImportacao.CONCLUIDO);
        pedidoImportacao = pedidoImportacaoService.save(pedidoImportacao);
        return ResponseEntity.ok(pedidoImportacaoAssembler.toDto(pedidoImportacao));
    }

    @PatchMapping("{id}/erro")
    public ResponseEntity<PedidoImportacaoResponseDTO> error(@PathVariable Long id, @RequestBody @PedidoImportacaoErrorValid PedidoImportacaoRequestDTO requestDTO) throws BusinessException {
        var optionalPedido = pedidoImportacaoService.getById(id);

        if (!optionalPedido.isPresent())
            throw new RegisterNotFound("Pedido de importação não encontrado");
        else if (optionalPedido.get().getStatus() != EnumStatusImportacao.EM_ANDAMENTO)
            throw new InvalidOperation("Status da importação inválido para a operação atual!");

        var pedidoImportacao = optionalPedido.get();
        pedidoImportacao = pedidoImportacaoAssembler.toErrorEntity(requestDTO, pedidoImportacao);
        pedidoImportacao.setStatus(EnumStatusImportacao.ERRO);
        pedidoImportacao = pedidoImportacaoService.save(pedidoImportacao);
        return ResponseEntity.ok(pedidoImportacaoAssembler.toDto(pedidoImportacao));
    }
}
