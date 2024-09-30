package com.assovio.holerize_api.api.controller;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assovio.holerize_api.api.assembler.PedidoImportacaoAssembler;
import com.assovio.holerize_api.api.dto.request.PedidoImportacaoRequestDTO;
import com.assovio.holerize_api.api.dto.response.PedidoImportacaoResponseDTO;
import com.assovio.holerize_api.api.dto.response.PedidoImportacaoResponseSimpleDTO;
import com.assovio.holerize_api.api.infra.security.AESUtil;
import com.assovio.holerize_api.domain.exceptions.InvalidOperation;
import com.assovio.holerize_api.domain.exceptions.RegisterNotFound;
import com.assovio.holerize_api.domain.model.PedidoImportacao;
import com.assovio.holerize_api.domain.model.Enums.EnumStatusImportacao;
import com.assovio.holerize_api.domain.service.PedidoImportacaoService;
import com.assovio.holerize_api.domain.validator.pedidoimportacao.PedidoImportacaoErrorValid;
import com.assovio.holerize_api.domain.validator.pedidoimportacao.PedidoImportacaoFinishValid;
import com.assovio.holerize_api.domain.validator.pedidoimportacao.PedidoImportacaoStoreValid;
import com.assovio.holerize_api.domain.validator.pedidoimportacao.PedidoImportacaoUpdateValid;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;



@AllArgsConstructor
@RestController
@RequestMapping("pedidosImportacao")
public class PedidoImportacaoController {
    
    private PedidoImportacaoAssembler pedidoImportacaoAssembler;
    private PedidoImportacaoService pedidoImportacaoService;
    private AESUtil passwordEncoder;

    @GetMapping
    public ResponseEntity<Page<PedidoImportacaoResponseSimpleDTO>> index(
        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
        @RequestParam(name = "size", required = false, defaultValue = "30") Integer size,
        @RequestParam(name = "status", required = false) EnumStatusImportacao status,
        @RequestParam(name = "data_inicial", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataInicial
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PedidoImportacao> pedidoImportacaoPage = pedidoImportacaoService.getByFilters(status, dataInicial, pageable);
        Page<PedidoImportacaoResponseSimpleDTO> response = pedidoImportacaoAssembler.toPageSimpleDTO(pedidoImportacaoPage);
        return ResponseEntity.ok(response);
    }
    

    @PostMapping
    public ResponseEntity<PedidoImportacaoResponseDTO> store(@RequestBody @PedidoImportacaoStoreValid PedidoImportacaoRequestDTO requestDTO) throws Exception {
        PedidoImportacao newPedidoImportacao = pedidoImportacaoAssembler.toStoreEntity(requestDTO);
        newPedidoImportacao.setSenha(passwordEncoder.encrypt(newPedidoImportacao.getSenha()));
        newPedidoImportacao = pedidoImportacaoService.save(newPedidoImportacao);
        var dto = pedidoImportacaoAssembler.toDto(newPedidoImportacao);
        dto.setSenha(passwordEncoder.decrypt(dto.getSenha()));
        return ResponseEntity.ok(dto);
    }

    @GetMapping("proximo")
    public ResponseEntity<PedidoImportacaoResponseDTO> next() throws Exception {
        var optionalPedido = pedidoImportacaoService.getNext();

        if (!optionalPedido.isPresent())
            throw new RegisterNotFound("Não encontrado próximo registro na fila");

        var pedido = optionalPedido.get();
        pedido.setStatus(EnumStatusImportacao.EM_ANDAMENTO);
        pedido = pedidoImportacaoService.save(pedido);
        var dto = pedidoImportacaoAssembler.toDto(pedido);
        dto.setSenha(passwordEncoder.decrypt(dto.getSenha()));
        return ResponseEntity.ok(dto);
    }

    @PutMapping("{id}")
    public ResponseEntity<PedidoImportacaoResponseDTO> update(@PathVariable Long id, @RequestBody @PedidoImportacaoUpdateValid PedidoImportacaoRequestDTO requestDTO) throws Exception {
        var optionalPedido = pedidoImportacaoService.getById(id);

        if (!optionalPedido.isPresent())
            throw new RegisterNotFound("Pedido de importação não encontrado");

        var pedidoImportacao = optionalPedido.get();
        pedidoImportacao = pedidoImportacaoAssembler.toUpdateEntity(requestDTO, pedidoImportacao);
        pedidoImportacao.setSenha(passwordEncoder.encrypt(pedidoImportacao.getSenha()));
        pedidoImportacao = pedidoImportacaoService.save(pedidoImportacao);
        var dto = pedidoImportacaoAssembler.toDto(pedidoImportacao);
        dto.setSenha(passwordEncoder.decrypt(dto.getSenha()));
        return ResponseEntity.ok(dto);
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
    public ResponseEntity<PedidoImportacaoResponseDTO> finish(@PathVariable Long id, @RequestBody @PedidoImportacaoFinishValid PedidoImportacaoRequestDTO requestDTO) throws Exception {
        var optionalPedido = pedidoImportacaoService.getById(id);

        if (!optionalPedido.isPresent())
            throw new RegisterNotFound("Pedido de importação não encontrado");
        else if (optionalPedido.get().getStatus() != EnumStatusImportacao.EM_ANDAMENTO)
            throw new InvalidOperation("Status da importação inválido para a operação atual!");

        var pedidoImportacao = optionalPedido.get();
        pedidoImportacao = pedidoImportacaoAssembler.toFinishEntity(requestDTO, pedidoImportacao);
        pedidoImportacao.setStatus(EnumStatusImportacao.CONCLUIDO);
        pedidoImportacao = pedidoImportacaoService.save(pedidoImportacao);
        var dto = pedidoImportacaoAssembler.toDto(pedidoImportacao);
        dto.setSenha(passwordEncoder.decrypt(dto.getSenha()));
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("{id}/erro")
    public ResponseEntity<PedidoImportacaoResponseDTO> error(@PathVariable Long id, @RequestBody @PedidoImportacaoErrorValid PedidoImportacaoRequestDTO requestDTO) throws Exception {
        var optionalPedido = pedidoImportacaoService.getById(id);

        if (!optionalPedido.isPresent())
            throw new RegisterNotFound("Pedido de importação não encontrado");
        else if (optionalPedido.get().getStatus() != EnumStatusImportacao.EM_ANDAMENTO)
            throw new InvalidOperation("Status da importação inválido para a operação atual!");

        var pedidoImportacao = optionalPedido.get();
        pedidoImportacao = pedidoImportacaoAssembler.toErrorEntity(requestDTO, pedidoImportacao);
        pedidoImportacao.setStatus(EnumStatusImportacao.ERRO);
        pedidoImportacao = pedidoImportacaoService.save(pedidoImportacao);
        var dto = pedidoImportacaoAssembler.toDto(pedidoImportacao);
        dto.setSenha(passwordEncoder.decrypt(dto.getSenha()));
        return ResponseEntity.ok(dto);
    }
}
