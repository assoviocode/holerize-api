package com.assovio.holerize_api.domain.dao;

import org.springframework.data.repository.CrudRepository;

import com.assovio.holerize_api.domain.model.EnumStatusImportacao;
import com.assovio.holerize_api.domain.model.PedidoImportacao;

public interface PedidoImportacaoDAO extends CrudRepository<PedidoImportacao, Long>{
    
    PedidoImportacao findFirstByStatusOrderByIdAsc(EnumStatusImportacao status);
}
