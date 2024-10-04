package com.assovio.holerize_api.domain.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.assovio.holerize_api.domain.dao.PedidoImportacaoDAO;
import com.assovio.holerize_api.domain.model.PedidoImportacao;
import com.assovio.holerize_api.domain.model.Enums.EnumStatusImportacao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class PedidoImportacaoService extends GenericService<PedidoImportacao, PedidoImportacaoDAO, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public PedidoImportacao save(PedidoImportacao entity){
        PedidoImportacao savedEntity = dao.save(entity);
        entityManager.flush();
        entityManager.clear();
        return getById(savedEntity.getId()).orElse(null);
    }

    public Optional<PedidoImportacao> getByUuid(String uuid){
        return dao.findFirstByUuid(uuid);
    }

    public Optional<PedidoImportacao> getNext() {
        return dao.findFirstByStatusOrderByIdAsc(EnumStatusImportacao.NOVO);
    }

    public Page<PedidoImportacao> getByFilters(Long usuarioId, String cpf, EnumStatusImportacao status, Date dataInicial,
            Pageable pageable) {
        return dao.findByFilters(usuarioId, cpf.replaceAll("[^0-9]", "").replaceFirst("^0+", ""), status, dataInicial, pageable);
    }
}
