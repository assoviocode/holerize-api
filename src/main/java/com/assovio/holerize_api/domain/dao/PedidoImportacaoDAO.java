package com.assovio.holerize_api.domain.dao;

import java.util.Date;
import java.util.Optional;

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
        "(:nome IS NULL OR pi.nome LIKE CONCAT('%', :nome, '%')) AND " +
        "(:cpf IS NULL OR pi.cpf LIKE CONCAT(:cpf, '%')) AND " +
        "(:status IS NULL OR pi.status = :status) AND " +
        "(:tipoErro IS NULL OR pi.tipo_erro = :tipoErro) AND " +
        "(:dataInicial IS NULL OR pi.updated_at >= STR_TO_DATE(:dataInicial, '%Y-%m-%d')) AND " +
        "deleted_at IS NULL", nativeQuery = true)
    Page<PedidoImportacao> findByFilters(Long usuarioId, String nome, String cpf, String status, String tipoErro, Date dataInicial,
            Pageable pageable);

    Optional<PedidoImportacao> findFirstByUuid(String uuid);
}
