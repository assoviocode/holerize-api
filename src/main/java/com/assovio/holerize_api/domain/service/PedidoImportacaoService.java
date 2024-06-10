package com.assovio.holerize_api.domain.service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assovio.holerize_api.domain.dao.PedidoImportacaoDAO;
import com.assovio.holerize_api.domain.model.EnumStatusImportacao;
import com.assovio.holerize_api.domain.model.PedidoImportacao;

import jakarta.transaction.Transactional;

@Service
public class PedidoImportacaoService {
    
    @Autowired
    private PedidoImportacaoDAO dao;

    public PedidoImportacao GetNext(){
        return this.dao.findFirstByStatusOrderByIdAsc(EnumStatusImportacao.NOVO);
    }

    public PedidoImportacao GetById(Long id){
        return this.dao.findById(id).orElse(null);
    }

    @Transactional
    public PedidoImportacao Save(PedidoImportacao pedidoImportacao){
        return this.dao.save(pedidoImportacao);
    }

    @Transactional
    public void Delete(Long id){
        this.dao.deleteById(id);
    }

    @Transactional
    public void LogicalDelete(PedidoImportacao pedidoImportacao){
        pedidoImportacao.setDeleteAt(OffsetDateTime.now().toInstant().atOffset(ZoneOffset.ofHours(3)));
        this.Save(pedidoImportacao);
    }
}
