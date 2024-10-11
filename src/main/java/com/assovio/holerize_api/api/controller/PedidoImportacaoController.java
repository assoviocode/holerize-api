package com.assovio.holerize_api.api.controller;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import com.assovio.holerize_api.api.infra.security.AESUtil;
import com.assovio.holerize_api.domain.exceptions.InvalidOperationException;
import com.assovio.holerize_api.domain.exceptions.RegisterNotFoundException;
import com.assovio.holerize_api.domain.exceptions.SaldoInsuficienteException;
import com.assovio.holerize_api.domain.model.PedidoExecucao;
import com.assovio.holerize_api.domain.model.PedidoImportacao;
import com.assovio.holerize_api.domain.model.Enums.EnumErrorType;
import com.assovio.holerize_api.domain.model.Enums.EnumStatusImportacao;
import com.assovio.holerize_api.domain.service.PedidoImportacaoService;
import com.assovio.holerize_api.domain.service.UsuarioService;
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
    public ResponseEntity<PedidoImportacaoResponseDTO> store(@AuthenticationPrincipal UserDetails usuarioLogado, @RequestBody @PedidoImportacaoStoreValid PedidoImportacaoRequestDTO requestDTO) throws Exception {
        
        var optionalUsuario = usuarioService.getUsuarioByLogin(usuarioLogado.getUsername());

        if (!optionalUsuario.isPresent())
            throw new InvalidOperationException("Usuário não encontrado!");

        var usuario = optionalUsuario.get();
        int newSaldo = optionalUsuario.get().getCreditos() - requestDTO.getQuantidadeAnosSolicitados();
        
        if (newSaldo < 0)
            throw new SaldoInsuficienteException("Saldo insuficiente para importação");

        usuario.setCreditos(newSaldo);
        usuario = usuarioService.save(usuario);

        PedidoImportacao newPedidoImportacao = pedidoImportacaoAssembler.toStoreEntity(requestDTO);
        var dataAte = getPeriodoAte(newPedidoImportacao.getMesDe(), newPedidoImportacao.getAnoDe(), newPedidoImportacao.getQuantidadeAnosSolicitados());
        newPedidoImportacao.setMesAte(dataAte.getMonthValue());
        newPedidoImportacao.setAnoAte(dataAte.getYear());
        newPedidoImportacao.setUsuario(usuario);
        newPedidoImportacao.setSenha(passwordEncoder.encrypt(newPedidoImportacao.getSenha()));
        Set<PedidoExecucao> setPedidosExecucao = new HashSet<>();
        setPedidosExecucao.add(new PedidoExecucao(newPedidoImportacao));
        newPedidoImportacao.setPedidosExecucao(setPedidosExecucao);
        newPedidoImportacao = pedidoImportacaoService.save(newPedidoImportacao);
        var dto = pedidoImportacaoAssembler.toDto(newPedidoImportacao);
        dto.setSenha(passwordEncoder.decrypt(dto.getSenha()));
        return new ResponseEntity<PedidoImportacaoResponseDTO>(dto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<PedidoImportacaoResponseDTO>> index(
        @AuthenticationPrincipal UserDetails usuario,
        @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
        @RequestParam(name = "size", required = false, defaultValue = "30") Integer size,
        @RequestParam(name = "nome", required = false) String nome,
        @RequestParam(name = "cpf", required = false) String cpf,
        @RequestParam(name = "status", required = false) EnumStatusImportacao status,
        @RequestParam(name = "data_inicial", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dataInicial,
        @RequestParam(name = "tipo_erro", required = false) EnumErrorType tipoErro
    ) throws InvalidOperationException {
        var usuarioLogado = usuarioService.getUsuarioByLogin(usuario.getUsername());
        if (!usuarioLogado.isPresent())
            throw new InvalidOperationException("Usuário não identificado");
            
        Pageable pageable = PageRequest.of(page, size);
        Page<PedidoImportacao> pedidoImportacaoPage = pedidoImportacaoService.getByFilters(usuarioLogado.get().getId(), nome, cpf, status, tipoErro, dataInicial, pageable);
        Page<PedidoImportacaoResponseDTO> response = pedidoImportacaoAssembler.toPageDTO(pedidoImportacaoPage);
        return new ResponseEntity<Page<PedidoImportacaoResponseDTO>>(response, HttpStatus.OK);
    }

    @GetMapping("{uuid}")
    public ResponseEntity<PedidoImportacaoResponseDTO> show(@PathVariable String uuid) throws Exception {
        var optionalPedido = pedidoImportacaoService.getByUuid(uuid);

        if (!optionalPedido.isPresent())
            throw new RegisterNotFoundException("Pedido de importação não encontrado");

        var pedido = optionalPedido.get();
        pedido.setSenha(passwordEncoder.decrypt(pedido.getSenha()));
        return new ResponseEntity<PedidoImportacaoResponseDTO>(pedidoImportacaoAssembler.toDto(optionalPedido.get()), HttpStatus.OK);
    }
    
    
    @PutMapping("{uuid}")
    public ResponseEntity<PedidoImportacaoResponseDTO> update(@PathVariable String uuid, @AuthenticationPrincipal UserDetails usuarioLogado, @RequestBody @PedidoImportacaoUpdateValid PedidoImportacaoRequestDTO requestDTO) throws Exception {
        var optionalPedido = pedidoImportacaoService.getByUuid(uuid);

        if (!optionalPedido.isPresent())
            throw new RegisterNotFoundException("Pedido de importação não encontrado");

        if (!optionalPedido.get().getStatus().equals(EnumStatusImportacao.NA_FILA))
            throw new InvalidOperationException("Status atual do pedido é inválido para esta operação");

        var optionalUsuario = usuarioService.getUsuarioByLogin(usuarioLogado.getUsername());

        if (!optionalUsuario.isPresent())
            throw new InvalidOperationException("Usuário não encontrado!");

        var usuario = optionalUsuario.get();
        var pedidoImportacao = optionalPedido.get();
        int newSaldo = optionalUsuario.get().getCreditos() + (pedidoImportacao.getQuantidadeAnosSolicitados() - requestDTO.getQuantidadeAnosSolicitados());
            
        if (newSaldo < 0)
            throw new SaldoInsuficienteException("Saldo insuficiente para importação");

        usuario.setCreditos(newSaldo);
        usuario = usuarioService.save(usuario);
        pedidoImportacao = pedidoImportacaoAssembler.toUpdateEntity(requestDTO, pedidoImportacao);
        var dataAte = getPeriodoAte(pedidoImportacao.getMesDe(), pedidoImportacao.getAnoDe(), pedidoImportacao.getQuantidadeAnosSolicitados());
        pedidoImportacao.setMesAte(dataAte.getMonthValue());
        pedidoImportacao.setAnoAte(dataAte.getYear());
        pedidoImportacao.setSenha(passwordEncoder.encrypt(pedidoImportacao.getSenha()));
        for (var pedidoExecucao : pedidoImportacao.getPedidosExecucao()) {
            pedidoExecucao.setStatus(pedidoImportacao.getStatus());
            pedidoExecucao.setMesDe(pedidoImportacao.getMesDe());
            pedidoExecucao.setAnoDe(pedidoImportacao.getAnoDe());
            pedidoExecucao.setMesAte(pedidoImportacao.getMesAte());
            pedidoExecucao.setAnoAte(pedidoImportacao.getAnoAte());
            pedidoExecucao.setLog(pedidoImportacao.getLog());
            pedidoExecucao.setTipoErro(pedidoImportacao.getTipoErro());
        }
        pedidoImportacao = pedidoImportacaoService.save(pedidoImportacao);
        var dto = pedidoImportacaoAssembler.toDto(pedidoImportacao);
        dto.setSenha(passwordEncoder.decrypt(dto.getSenha()));
        return new ResponseEntity<PedidoImportacaoResponseDTO>(dto, HttpStatus.OK);
    }

    @DeleteMapping("{uuid}")
    public ResponseEntity<?> destroy(@AuthenticationPrincipal UserDetails usuarioLogado, @PathVariable String uuid) throws RegisterNotFoundException, InvalidOperationException {
        var optionalPedido = pedidoImportacaoService.getByUuid(uuid);
        
        if (!optionalPedido.isPresent())
            throw new RegisterNotFoundException("Pedido de importação não encontrado");
        else if (!optionalPedido.get().getStatus().equals(EnumStatusImportacao.NA_FILA)){
            throw new InvalidOperationException("Pedido de importação não possui status válido para esta operação");
        }

        var pedidoImportacao = optionalPedido.get();
        var usuario = usuarioService.getUsuarioByLogin(usuarioLogado.getUsername()).get();
        usuario.setCreditos(usuario.getCreditos() + pedidoImportacao.getQuantidadeAnosSolicitados());
        usuarioService.save(usuario);
        pedidoImportacaoService.logicalDelete(pedidoImportacao);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private ZonedDateTime getPeriodoAte(int mesDe, int anoDe, int quantidadeAnosSolicitados){
        var dataDe = LocalDate.of(anoDe, mesDe, 1);
        ZoneOffset offset = ZoneOffset.ofHours(-3);
        ZonedDateTime dataDeOffset = dataDe.atStartOfDay(offset);
        var dataAteOffset = dataDeOffset.minusYears(quantidadeAnosSolicitados);
        dataAteOffset = dataAteOffset.plusMonths(1);
        return dataAteOffset;
    }
}
