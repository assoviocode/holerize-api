package com.assovio.holerize_api.domain.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.assovio.holerize_api.domain.model.PedidoExecucao;
import com.assovio.holerize_api.domain.model.Enums.EnumStatusImportacao;

public interface PedidoExecucaoDAO extends CrudRepository<PedidoExecucao, Long>{

    Optional<PedidoExecucao> findFirstByStatusOrderByIdAsc(EnumStatusImportacao naFila);

    Optional<PedidoExecucao> findFirstByUuid(String uuid);
    
}
