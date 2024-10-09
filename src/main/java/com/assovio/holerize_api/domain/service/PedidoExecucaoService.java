package com.assovio.holerize_api.domain.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.assovio.holerize_api.domain.dao.PedidoExecucaoDAO;
import com.assovio.holerize_api.domain.model.PedidoExecucao;
import com.assovio.holerize_api.domain.model.Enums.EnumStatusImportacao;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PedidoExecucaoService extends GenericService<PedidoExecucao, PedidoExecucaoDAO, Long> {
    
    public Optional<PedidoExecucao> getNext(){
        return dao.findFirstByStatusOrderByIdAsc(EnumStatusImportacao.NA_FILA);
    }

    public Optional<PedidoExecucao> getByUuid(String uuid) {
        return dao.findFirstByUuid(uuid);
    }
}
