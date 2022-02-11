package com.exadel.sandbox.team2.serivce.base;

import java.util.List;
import java.util.Optional;

public interface CrudService<T> {

    Optional<T> findById(Long id);

    List<T> findAll();

    T save(T t);

    T update(T t);

    void delete(Long id);



}
