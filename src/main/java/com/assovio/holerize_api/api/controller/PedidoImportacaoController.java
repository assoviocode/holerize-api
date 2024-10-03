package com.assovio.holerize_api.api.controller;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assovio.holerize_api.api.assembler.PedidoImportacaoAssembler;
import com.assovio.holerize_api.api.dto.request.PedidoImportacaoRequestDTO;
import com.assovio.holerize_api.api.dto.response.PedidoImportacaoResponseDTO;
import com.assovio.holerize_api.api.dto.response.PedidoImportacaoResponseSimpleDTO;
import com.assovio.holerize_api.api.infra.security.AESUtil;
import com.assovio.holerize_api.domain.exceptions.InvalidOperationException;
import com.assovio.holerize_api.domain.exceptions.RegisterNotFoundException;
import com.assovio.holerize_api.domain.model.PedidoImportacao;
import com.assovio.holerize_api.domain.model.Enums.EnumStatusImportacao;
import com.assovio.holerize_api.domain.service.PedidoImportacaoService;
import com.assovio.holerize_api.domain.service.UsuarioService;
import com.assovio.holerize_api.domain.validator.pedidoimportacao.PedidoImportacaoErrorValid;
import com.assovio.holerize_api.domain.validator.pedidoimportacao.PedidoImportacaoFinishValid;
import com.assovio.holerize_api.domain.validator.pedidoimportacao.PedidoImportacaoStoreValid;
import com.assovio.holerize_api.domain.validator.pedidoimportacao.PedidoImportacaoUpdateValid;

import lombok.AllArgsConstructor;



@AllArgsConstructor
@RestController
@RequestMapping("pedidosImportacao")
public class PedidoImportacaoController {
    
    private PedidoImportacaoAssembler pedidoImportacaoAssembler;
    private PedidoImportacaoService pedidoImportacaoService;
    private UsuarioService usuarioService;
    private AESUtil passwordEncoder;

    @PostMapping
    public ResponseEntity<PedidoImportacaoResponseDTO> store(@RequestBody @PedidoImportacaoStoreValid PedidoImportacaoRequestDTO requestDTO) throws Exception {
        var usuario = usuarioService.getById(requestDTO.getUsuarioId());

        if (!usuario.isPresent())
            throw new InvalidOperationException("Usuário não encontrado!");

        PedidoImportacao newPedidoImportacao = pedidoImportacaoAssembler.toStoreEntity(requestDTO);
        newPedidoImportacao.setUsuario(usuario.get());
        newPedidoImportacao.setSenha(passwordEncoder.encrypt(newPedidoImportacao.getSenha()));
        newPedidoImportacao = pedidoImportacaoService.save(newPedidoImportacao);
        var dto = pedidoImportacaoAssembler.toDto(newPedidoImportacao);
        dto.setSenha(passwordEncoder.decrypt(dto.getSenha()));
        return new ResponseEntity<PedidoImportacaoResponseDTO>(dto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<PedidoImportacaoResponseSimpleDTO>> index(
        @AuthenticationPrincipal UserDetails usuario,
        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
        @RequestParam(name = "size", required = false, defaultValue = "30") Integer size,
        @RequestParam(name = "status", required = false) EnumStatusImportacao status,
        @RequestParam(name = "data_inicial", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataInicial
    ) throws InvalidOperationException {
        var usuarioLogado = usuarioService.getUsuarioByLogin(usuario.getUsername());
        if (!usuarioLogado.isPresent())
            throw new InvalidOperationException("Usuário não identificado");
            
        Pageable pageable = PageRequest.of(page, size);
        Page<PedidoImportacao> pedidoImportacaoPage = pedidoImportacaoService.getByFilters(usuarioLogado.get().getId(), status, dataInicial, pageable);
        Page<PedidoImportacaoResponseSimpleDTO> response = pedidoImportacaoAssembler.toPageSimpleDTO(pedidoImportacaoPage);
        return new ResponseEntity<Page<PedidoImportacaoResponseSimpleDTO>>(response, HttpStatus.OK);
    }

    @GetMapping("proximo")
    public ResponseEntity<PedidoImportacaoResponseDTO> next() throws Exception {
        var optionalPedido = pedidoImportacaoService.getNext();

        if (!optionalPedido.isPresent())
            throw new RegisterNotFoundException("Não encontrado próximo registro na fila");

        var pedido = optionalPedido.get();
        pedido.setStatus(EnumStatusImportacao.EM_ANDAMENTO);
        pedido = pedidoImportacaoService.save(pedido);
        var dto = pedidoImportacaoAssembler.toDto(pedido);
        dto.setSenha(passwordEncoder.decrypt(dto.getSenha()));
        return new ResponseEntity<PedidoImportacaoResponseDTO>(dto, HttpStatus.OK);
    }

    @GetMapping("{uuid}")
    public ResponseEntity<PedidoImportacaoResponseDTO> show(@PathVariable String uuid) throws RegisterNotFoundException {
        var optionalPedido = pedidoImportacaoService.getByUuid(uuid);

        if (!optionalPedido.isPresent())
            throw new RegisterNotFoundException("Pedido de importação não encontrado");

        return new ResponseEntity<PedidoImportacaoResponseDTO>(pedidoImportacaoAssembler.toDto(optionalPedido.get()), HttpStatus.OK);
    }
    
    
    @PutMapping("{uuid}")
    public ResponseEntity<PedidoImportacaoResponseDTO> update(@PathVariable String uuid, @RequestBody @PedidoImportacaoUpdateValid PedidoImportacaoRequestDTO requestDTO) throws Exception {
        var optionalPedido = pedidoImportacaoService.getByUuid(uuid);

        if (!optionalPedido.isPresent())
            throw new RegisterNotFoundException("Pedido de importação não encontrado");

        var pedidoImportacao = optionalPedido.get();
        pedidoImportacao = pedidoImportacaoAssembler.toUpdateEntity(requestDTO, pedidoImportacao);
        pedidoImportacao.setSenha(passwordEncoder.encrypt(pedidoImportacao.getSenha()));
        pedidoImportacao = pedidoImportacaoService.save(pedidoImportacao);
        var dto = pedidoImportacaoAssembler.toDto(pedidoImportacao);
        dto.setSenha(passwordEncoder.decrypt(dto.getSenha()));
        return new ResponseEntity<PedidoImportacaoResponseDTO>(dto, HttpStatus.OK);
    }

    @DeleteMapping("{uuid}")
    public ResponseEntity<?> destroy(@PathVariable String uuid) throws RegisterNotFoundException {
        var optionalPedido = pedidoImportacaoService.getByUuid(uuid);
        
        if (!optionalPedido.isPresent())
            throw new RegisterNotFoundException("Pedido de importação não encontrado");

        var pedidoImportacao = optionalPedido.get();
        pedidoImportacaoService.logicalDelete(pedidoImportacao);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("{uuid}/finalizado")
    public ResponseEntity<PedidoImportacaoResponseDTO> finish(@PathVariable String uuid, @RequestBody @PedidoImportacaoFinishValid PedidoImportacaoRequestDTO requestDTO) throws Exception {
        var optionalPedido = pedidoImportacaoService.getByUuid(uuid);

        if (!optionalPedido.isPresent())
            throw new RegisterNotFoundException("Pedido de importação não encontrado");
        else if (optionalPedido.get().getStatus() != EnumStatusImportacao.EM_ANDAMENTO)
            throw new InvalidOperationException("Status da importação inválido para a operação atual!");

        var pedidoImportacao = optionalPedido.get();
        pedidoImportacao = pedidoImportacaoAssembler.toFinishEntity(requestDTO, pedidoImportacao);
        pedidoImportacao.setStatus(EnumStatusImportacao.CONCLUIDO);
        pedidoImportacao = pedidoImportacaoService.save(pedidoImportacao);
        var dto = pedidoImportacaoAssembler.toDto(pedidoImportacao);
        dto.setSenha(passwordEncoder.decrypt(dto.getSenha()));
        return new ResponseEntity<PedidoImportacaoResponseDTO>(dto, HttpStatus.OK);
    }

    @PatchMapping("{uuid}/erro")
    public ResponseEntity<PedidoImportacaoResponseDTO> error(@PathVariable String uuid, @RequestBody @PedidoImportacaoErrorValid PedidoImportacaoRequestDTO requestDTO) throws Exception {
        var optionalPedido = pedidoImportacaoService.getByUuid(uuid);

        if (!optionalPedido.isPresent())
            throw new RegisterNotFoundException("Pedido de importação não encontrado");
        else if (optionalPedido.get().getStatus() != EnumStatusImportacao.EM_ANDAMENTO)
            throw new InvalidOperationException("Status da importação inválido para a operação atual!");

        var pedidoImportacao = optionalPedido.get();
        pedidoImportacao = pedidoImportacaoAssembler.toErrorEntity(requestDTO, pedidoImportacao);
        pedidoImportacao.setStatus(EnumStatusImportacao.ERRO);
        pedidoImportacao = pedidoImportacaoService.save(pedidoImportacao);
        var dto = pedidoImportacaoAssembler.toDto(pedidoImportacao);
        dto.setSenha(passwordEncoder.decrypt(dto.getSenha()));
        return new ResponseEntity<PedidoImportacaoResponseDTO>(dto, HttpStatus.OK);
    }
}
