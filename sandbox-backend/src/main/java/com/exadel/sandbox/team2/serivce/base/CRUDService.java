package com.exadel.sandbox.team2.serivce.base;

import java.util.List;
import java.util.Optional;

public interface CRUDService<E, D> {

    Optional<E> findById(Long id);

    List<E> findAll();

    E save(E e, D d);

    E update(E e, D d, long id);

    void delete(Long id);
}
