package com.assovio.holerize_api.domain.dao;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.assovio.holerize_api.domain.model.PedidoImportacao;
import com.assovio.holerize_api.domain.model.Enums.EnumStatusImportacao;

public interface PedidoImportacaoDAO extends CrudRepository<PedidoImportacao, Long>{
    
    Optional<PedidoImportacao> findFirstByStatusOrderByIdAsc(EnumStatusImportacao status);

    @Query(value = 
        "SELECT * from pedido_importacao pi WHERE " +
        "(pi.usuario_id = :usuarioId) AND " +
        "(:status IS NULL OR pi.status = :status) AND " +
        "(:dataInicial IS NULL OR pi.updated_at >= STR_TO_DATE(:dataInicial, '%Y-%m-%d'))", nativeQuery = true)
    Page<PedidoImportacao> findByFilters(Long usuarioId, EnumStatusImportacao status, Date dataInicial,
            Pageable pageable);

    Optional<PedidoImportacao> findFirstByUuid(UUID uuid);
}
