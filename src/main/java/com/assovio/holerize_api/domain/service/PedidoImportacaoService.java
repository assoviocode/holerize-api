package com.assovio.holerize_api.domain.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.assovio.holerize_api.domain.dao.PedidoImportacaoDAO;
import com.assovio.holerize_api.domain.model.PedidoImportacao;
import com.assovio.holerize_api.domain.model.Enums.EnumStatusImportacao;

@Service
public class PedidoImportacaoService extends GenericService<PedidoImportacao, PedidoImportacaoDAO, Long> {

    public Optional<PedidoImportacao> getNext() {
        return dao.findFirstByStatusOrderByIdAsc(EnumStatusImportacao.NOVO);
    }

    public Page<PedidoImportacao> getByFilters(EnumStatusImportacao status, Date dataInicial,
            Pageable pageable) {
        return dao.findByFilters(status, dataInicial, pageable);
    }
}
