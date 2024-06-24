package com.assovio.holerize_api.domain.service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assovio.holerize_api.domain.dao.PedidoImportacaoDAO;
import com.assovio.holerize_api.domain.exceptions.BusinessException;
import com.assovio.holerize_api.domain.exceptions.RegisterNotFound;
import com.assovio.holerize_api.domain.model.EnumStatusImportacao;
import com.assovio.holerize_api.domain.model.PedidoImportacao;

import jakarta.transaction.Transactional;

@Service
public class PedidoImportacaoService {
    
    @Autowired
    private PedidoImportacaoDAO dao;

    public PedidoImportacao GetNext() throws BusinessException {
        return this.dao.findFirstByStatusOrderByIdAsc(EnumStatusImportacao.NOVO).orElseThrow(() -> new RegisterNotFound("Registro não encontrado"));
    }

    public PedidoImportacao GetById(Long id) throws BusinessException {
        return this.dao.findById(id).orElseThrow(() -> new RegisterNotFound("Registro não encontrado"));
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
