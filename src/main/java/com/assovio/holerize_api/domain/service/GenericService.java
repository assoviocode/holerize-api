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

public abstract class GenericService<Entity extends TimeStamp, Dao extends CrudRepository<Entity, TypeId>, TypeId> {
    
    @Autowired
    protected Dao dao;

    public List<Entity> getAll(){
        return StreamSupport.stream(dao.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Optional<Entity> getById(TypeId id){
        return dao.findById(id);
    }

    public Entity save(Entity entity){
        return dao.save(entity);
    }

    public void delete(Entity entity){
        dao.delete(entity);
    }

    public void logicalDelete(Entity entity){
        entity.setDeleteAt(OffsetDateTime.now().toInstant().atOffset(ZoneOffset.ofHours(3)));
        save(entity);
    }
}
