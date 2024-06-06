package com.assovio.holerize_api.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assovio.holerize_api.api.assembler.PedidoImportacaoAssembler;
import com.assovio.holerize_api.api.dto.request.PedidoImportacaoErrorRequestDTO;
import com.assovio.holerize_api.api.dto.request.PedidoImportacaoStoreRequestDTO;
import com.assovio.holerize_api.api.dto.response.PedidoImportacaoResponseDTO;
import com.assovio.holerize_api.domain.model.EnumStatusImportacao;
import com.assovio.holerize_api.domain.model.PedidoImportacao;
import com.assovio.holerize_api.domain.service.PedidoImportacaoService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/pedidosImportacao")
public class PedidoImportacaoController {
    
    private PedidoImportacaoAssembler pedidoImportacaoAssembler;
    private PedidoImportacaoService pedidoImportacaoService;

    @PostMapping
    public ResponseEntity<PedidoImportacaoResponseDTO> Store(@Valid @RequestBody PedidoImportacaoStoreRequestDTO requestDTO){
        PedidoImportacao newPedidoImportacao = pedidoImportacaoAssembler.toEntity(requestDTO);
        newPedidoImportacao.setStatus(EnumStatusImportacao.NOVO);
        pedidoImportacaoService.Save(newPedidoImportacao);
        return ResponseEntity.ok(pedidoImportacaoAssembler.toDto(newPedidoImportacao));
    }

    @GetMapping("/proximo")
    public ResponseEntity<PedidoImportacaoResponseDTO> Next(){
        
        PedidoImportacao nextImportacao = pedidoImportacaoService.GetNext();
        if (nextImportacao == null)
            return ResponseEntity.notFound().build();

        PedidoImportacaoResponseDTO response = pedidoImportacaoAssembler.toDto(nextImportacao);
        nextImportacao.setStatus(EnumStatusImportacao.EM_ANDAMENTO);
        pedidoImportacaoService.Save(nextImportacao);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/finalizado")
    public ResponseEntity<PedidoImportacaoResponseDTO> Finish(@PathVariable Long id){
        
        PedidoImportacao pedidoImportacao = pedidoImportacaoService.GetById(id);

        if (pedidoImportacao == null)
            return ResponseEntity.notFound().build();
        
        pedidoImportacao.setStatus(EnumStatusImportacao.CONCLUIDO);
        pedidoImportacaoService.Save(pedidoImportacao);

        return ResponseEntity.ok(pedidoImportacaoAssembler.toDto(pedidoImportacao));
    }

    @PutMapping("/{id}/erro")
    public ResponseEntity<?> Error(@PathVariable Long id, @Valid @RequestBody PedidoImportacaoErrorRequestDTO requestDTO){

        PedidoImportacao pedidoImportacao = pedidoImportacaoService.GetById(id);

        if (pedidoImportacao == null)
            return ResponseEntity.notFound().build();
        
        if (pedidoImportacao.getStatus() != EnumStatusImportacao.EM_ANDAMENTO)
            return ResponseEntity.badRequest().body("Status da importação inválido para a operação atual!");

        pedidoImportacaoAssembler.updateEntity(requestDTO, pedidoImportacao);
        pedidoImportacao.setStatus(EnumStatusImportacao.ERRO);
        pedidoImportacaoService.Save(pedidoImportacao);

        return ResponseEntity.ok(pedidoImportacaoAssembler.toDto(pedidoImportacao));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> Destroy(@PathVariable Long id){

        PedidoImportacao pedidoImportacao = pedidoImportacaoService.GetById(id);

        if (pedidoImportacao == null)
            return ResponseEntity.notFound().build();
        
        pedidoImportacaoService.LogicalDelete(pedidoImportacao);
        return ResponseEntity.noContent().build();
    }
}
