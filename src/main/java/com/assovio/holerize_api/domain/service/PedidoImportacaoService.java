package com.assovio.holerize_api.domain.service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.assovio.holerize_api.domain.dao.PedidoImportacaoDAO;
import com.assovio.holerize_api.domain.model.PedidoImportacao;
import com.assovio.holerize_api.domain.model.Enums.EnumErrorType;
import com.assovio.holerize_api.domain.model.Enums.EnumStatusImportacao;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PedidoImportacaoService extends GenericService<PedidoImportacao, PedidoImportacaoDAO, Long> {

    public Optional<PedidoImportacao> getByUuid(String uuid){
        return dao.findFirstByUuid(uuid);
    }

    public Optional<PedidoImportacao> getNext() {
        return dao.findFirstByStatusOrderByIdAsc(EnumStatusImportacao.NA_FILA);
    }

    public Page<PedidoImportacao> getByFilters(Long usuarioId, String nome, String cpf, EnumStatusImportacao status, EnumErrorType tipoErro, Date dataInicial,
            Pageable pageable) {
        var cpfWithoutMask = cpf;
        if (cpf != null)
            cpfWithoutMask = cpf.replaceAll("[^0-9]", "").replaceFirst("^0+", "");

        String statusValue = null;
        if (status != null)
            statusValue = status.name();

        String tipoErroValue = null;
        if (tipoErro != null)
            tipoErroValue = tipoErro.name();

        return dao.findByFilters(usuarioId, nome, cpfWithoutMask, statusValue, tipoErroValue, dataInicial, pageable);
    }

    @Override
    public void logicalDelete(PedidoImportacao entity) {
        for (var pedidoExecucao : entity.getPedidosExecucao()) {
            pedidoExecucao.setDeletedAt(OffsetDateTime.now().toInstant().atOffset(ZoneOffset.ofHours(3)));
        }
        super.logicalDelete(entity);
    }

}
