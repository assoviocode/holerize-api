package com.assovio.holerize_api.domain.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.assovio.holerize_api.domain.model.PedidoImportacao;

public interface PedidoImportacaoDAO extends CrudRepository<PedidoImportacao, Long>{
    
    @Query(value = "SELECT pi FROM PedidoImportacao pi WHERE pi.status = 'NOVO' ORDER BY pi.id ASC")
    PedidoImportacao findNexWithStatusNOVO();
}
