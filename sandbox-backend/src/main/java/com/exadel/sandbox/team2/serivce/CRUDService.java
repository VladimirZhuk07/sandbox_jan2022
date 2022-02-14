package com.exadel.sandbox.team2.serivce;

import java.util.List;
import java.util.Optional;

public interface CRUDService<E> {

    Optional<E> findById(Long id);

    List<E> findAll();

    E save(E e);

    E update(E e);

    void delete(Long id);
}
