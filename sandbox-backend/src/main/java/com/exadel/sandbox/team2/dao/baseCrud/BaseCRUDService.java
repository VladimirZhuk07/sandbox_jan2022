package com.exadel.sandbox.team2.dao.baseCrud;

import com.exadel.sandbox.team2.domain.base.BaseEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public abstract class BaseCRUDService <E extends BaseEntity, I> {

    protected CrudRepository<E,I> repository;

    public BaseCRUDService(CrudRepository<E,I> repository){
        this.repository = repository;
    }


    public Optional<E> findById(I i) {
        return repository.findById(i);
    }

    public List<E> findAll() {
        List<E> list = new ArrayList<>();
        repository.findAll().forEach(list::add);
        return list;
    }

    public E save(E e) {
        return repository.save(e);
    }

    public E update(E e) {
        return repository.save(e);
    }

    public void delete(I i) {
        repository.deleteById(i);
    }


}
