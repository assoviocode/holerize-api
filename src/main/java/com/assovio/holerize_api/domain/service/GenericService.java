package com.assovio.holerize_api.domain.service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import com.assovio.holerize_api.domain.model.TimeStamp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Transactional
public abstract class GenericService<Entity extends TimeStamp, Dao extends CrudRepository<Entity, TypeId>, TypeId> {
    
    @Autowired
    protected Dao dao;

    @PersistenceContext
    protected EntityManager entityManager;

    public List<Entity> getAll(){
        return StreamSupport.stream(dao.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Optional<Entity> getById(TypeId id){
        return dao.findById(id);
    }

    public Entity save(Entity entity){
        var savedEntity = dao.save(entity);
        entityManager.flush();
        entityManager.refresh(savedEntity);
        return savedEntity;
    }

    public void delete(Entity entity){
        dao.delete(entity);
    }

    public void logicalDelete(Entity entity){
        entity.setDeletedAt(OffsetDateTime.now().toInstant().atOffset(ZoneOffset.ofHours(3)));
        dao.save(entity);
        entityManager.flush();
    }
}
