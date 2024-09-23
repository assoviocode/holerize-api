package com.assovio.holerize_api.domain.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.assovio.holerize_api.domain.model.PedidoImportacao;
import com.assovio.holerize_api.domain.model.Enums.EnumStatusImportacao;

public interface PedidoImportacaoDAO extends CrudRepository<PedidoImportacao, Long>{
    
    Optional<PedidoImportacao> findFirstByStatusOrderByIdAsc(EnumStatusImportacao status);
}
