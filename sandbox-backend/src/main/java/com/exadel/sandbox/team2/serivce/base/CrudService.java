package com.exadel.sandbox.team2.serivce.base;

import java.util.List;
import java.util.Optional;

public interface CrudService<T,D> {

    Optional<T> findById(Long id);

    List<T> findAll();

    D save(D d);

    D update(D d, long id);

    void delete(Long id);



}
