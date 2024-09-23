package com.assovio.holerize_api.domain.service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assovio.holerize_api.domain.dao.PedidoImportacaoDAO;
import com.assovio.holerize_api.domain.model.PedidoImportacao;
import com.assovio.holerize_api.domain.model.Enums.EnumStatusImportacao;

import jakarta.transaction.Transactional;

@Service
public class PedidoImportacaoService {
    
    @Autowired
    private PedidoImportacaoDAO dao;

    public Optional<PedidoImportacao> getNext() {
        return dao.findFirstByStatusOrderByIdAsc(EnumStatusImportacao.NOVO);
    }

    public Optional<PedidoImportacao> getById(Long id) {
        return dao.findById(id);
    }

    @Transactional
    public PedidoImportacao save(PedidoImportacao pedidoImportacao){
        return dao.save(pedidoImportacao);
    }

    @Transactional
    public void Delete(Long id){
        dao.deleteById(id);
    }

    @Transactional
    public void logicalDelete(PedidoImportacao pedidoImportacao){
        pedidoImportacao.setDeleteAt(OffsetDateTime.now().toInstant().atOffset(ZoneOffset.ofHours(3)));
        save(pedidoImportacao);
    }
}
