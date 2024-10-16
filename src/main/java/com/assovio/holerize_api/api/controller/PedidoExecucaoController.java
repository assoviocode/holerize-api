package com.assovio.holerize_api.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assovio.holerize_api.api.assembler.PedidoExecucaoAssembler;
import com.assovio.holerize_api.api.dto.request.PedidoExecucaoRequestDTO;
import com.assovio.holerize_api.api.dto.response.PedidoExecucaoResponseDTO;
import com.assovio.holerize_api.api.infra.security.AESUtil;
import com.assovio.holerize_api.domain.exceptions.InvalidOperationException;
import com.assovio.holerize_api.domain.exceptions.RegisterNotFoundException;
import com.assovio.holerize_api.domain.model.Enums.EnumErrorType;
import com.assovio.holerize_api.domain.model.Enums.EnumStatusImportacao;
import com.assovio.holerize_api.domain.service.PedidoExecucaoService;
import com.assovio.holerize_api.domain.service.PedidoImportacaoService;
import com.assovio.holerize_api.domain.validator.pedidoexecucao.PedidoExecucaoErrorValid;
import com.assovio.holerize_api.domain.validator.pedidoexecucao.PedidoExecucaoFinishValid;
import com.assovio.holerize_api.domain.validator.pedidoexecucao.PedidoExecucaoStoreValid;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("pedidosExecucao")
public class PedidoExecucaoController {

    private PedidoImportacaoService pedidoImportacaoService;
    private PedidoExecucaoService pedidoExecucaoService;
    private PedidoExecucaoAssembler pedidoExecucaoAssembler;
    private AESUtil passwordEncoder;

    @PostMapping
    public ResponseEntity<PedidoExecucaoResponseDTO> store(
            @RequestBody @PedidoExecucaoStoreValid PedidoExecucaoRequestDTO requestDTO) throws Exception {
        var optionalPedidoImportacao = pedidoImportacaoService.getByUuid(requestDTO.getPedidoImportacaoUuid());

        if (!optionalPedidoImportacao.isPresent())
            throw new RegisterNotFoundException("Pedido de importação não encontrado");
        else if (!optionalPedidoImportacao.get().getStatus().equals(EnumStatusImportacao.EM_ANDAMENTO))
            throw new InvalidOperationException("Pedido de importação possui status que impede esta operação");

        var pedidoExecucao = pedidoExecucaoAssembler.toStoreEntity(requestDTO);
        pedidoExecucao.setPedidoImportacao(optionalPedidoImportacao.get());
        pedidoExecucaoService.save(pedidoExecucao);
        var dto = pedidoExecucaoAssembler.toDto(pedidoExecucao);
        dto.setSenha(passwordEncoder.decrypt(dto.getSenha()));
        return new ResponseEntity<PedidoExecucaoResponseDTO>(dto, HttpStatus.CREATED);
    }

    @GetMapping("proximo")
    public ResponseEntity<PedidoExecucaoResponseDTO> next() throws Exception {
        var optionalPedido = pedidoExecucaoService.getNext();

        if (!optionalPedido.isPresent())
            throw new RegisterNotFoundException("Não encontrado próximo registro na fila");

        var pedidoExecucao = optionalPedido.get();
        pedidoExecucao.setStatus(EnumStatusImportacao.EM_ANDAMENTO);
        pedidoExecucao.getPedidoImportacao().setStatus(EnumStatusImportacao.EM_ANDAMENTO);
        pedidoExecucao = pedidoExecucaoService.save(pedidoExecucao);

        var dto = pedidoExecucaoAssembler.toDto(pedidoExecucao);
        dto.setSenha(passwordEncoder.decrypt(dto.getSenha()));
        return new ResponseEntity<PedidoExecucaoResponseDTO>(dto, HttpStatus.OK);
    }

    @PatchMapping("{uuid}/finalizado")
    public ResponseEntity<PedidoExecucaoResponseDTO> finish(@PathVariable String uuid,
            @RequestBody @PedidoExecucaoFinishValid PedidoExecucaoRequestDTO requestDTO) throws Exception {
        var optionalPedidoExecucao = pedidoExecucaoService.getByUuid(uuid);

        if (!optionalPedidoExecucao.isPresent())
            throw new RegisterNotFoundException("Pedido de importação não encontrado");
        else if (!optionalPedidoExecucao.get().getStatus().equals(EnumStatusImportacao.EM_ANDAMENTO))
            throw new InvalidOperationException("Status da importação inválido para a operação atual!");

        var pedidoExecucao = optionalPedidoExecucao.get();
        pedidoExecucao = pedidoExecucaoAssembler.toFinishEntity(requestDTO, pedidoExecucao);
        pedidoExecucao.setStatus(EnumStatusImportacao.CONCLUIDO);
        pedidoExecucao.getPedidoImportacao().setStatus(EnumStatusImportacao.CONCLUIDO);

        if (pedidoExecucao.getPedidoImportacao().getQuantidadeAnosBaixados() > pedidoExecucao.getPedidoImportacao().getQuantidadeAnosSolicitados())
            pedidoExecucao.getPedidoImportacao().setQuantidadeAnosBaixados(pedidoExecucao.getPedidoImportacao().getQuantidadeAnosSolicitados());

        pedidoExecucao = pedidoExecucaoService.save(pedidoExecucao);
        var dto = pedidoExecucaoAssembler.toDto(pedidoExecucao);
        dto.setSenha(passwordEncoder.decrypt(dto.getSenha()));
        return new ResponseEntity<PedidoExecucaoResponseDTO>(dto, HttpStatus.OK);
    }

    @PatchMapping("{uuid}/erro")
    public ResponseEntity<PedidoExecucaoResponseDTO> error(@PathVariable String uuid,
            @RequestBody @PedidoExecucaoErrorValid PedidoExecucaoRequestDTO requestDTO) throws Exception {
        var optionalPedidoExecucao = pedidoExecucaoService.getByUuid(uuid);

        if (!optionalPedidoExecucao.isPresent())
            throw new RegisterNotFoundException("Pedido de importação não encontrado");
        else if (!optionalPedidoExecucao.get().getStatus().equals(EnumStatusImportacao.EM_ANDAMENTO))
            throw new InvalidOperationException("Status da importação inválido para a operação atual!");

        var pedidoExecucao = optionalPedidoExecucao.get();
        pedidoExecucao = pedidoExecucaoAssembler.toErrorEntity(requestDTO, pedidoExecucao);
        pedidoExecucao.setStatus(EnumStatusImportacao.ERRO);
        if (!pedidoExecucao.getTipoErro().equals(EnumErrorType.EXECUCAO)
                && !pedidoExecucao.getTipoErro().equals(EnumErrorType.SERVICO_INDISPONIVEL)) {
            pedidoExecucao.getPedidoImportacao().setLog(pedidoExecucao.getLog());
            pedidoExecucao.getPedidoImportacao().setTipoErro(pedidoExecucao.getTipoErro());
            pedidoExecucao.getPedidoImportacao().setStatus(EnumStatusImportacao.ERRO);
        }
        pedidoExecucao = pedidoExecucaoService.save(pedidoExecucao);
        var dto = pedidoExecucaoAssembler.toDto(pedidoExecucao);
        dto.setSenha(passwordEncoder.decrypt(dto.getSenha()));
        return new ResponseEntity<PedidoExecucaoResponseDTO>(dto, HttpStatus.OK);
    }
}
