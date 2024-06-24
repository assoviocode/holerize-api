package com.assovio.holerize_api.domain.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.assovio.holerize_api.domain.model.EnumStatusImportacao;
import com.assovio.holerize_api.domain.model.PedidoImportacao;

public interface PedidoImportacaoDAO extends CrudRepository<PedidoImportacao, Long>{
    
    Optional<PedidoImportacao> findFirstByStatusOrderByIdAsc(EnumStatusImportacao status);
}
