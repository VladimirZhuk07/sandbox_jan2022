package com.exadel.sandbox.team2.serivce.base;

import com.exadel.sandbox.team2.domain.base.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public abstract class CRUDServiceImpl<E extends BaseEntity> implements CRUDService<E> {

    @Autowired
    private  CrudRepository<E, Long> repository;

    @Override
    public Optional<E> findById(Long id) {
        return repository.findById(id);
    }

    public List<E> findAll() {
        List<E> list = new ArrayList<>();
        repository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public E save(E e) {
        return repository.save(e);
    }

    @Override
    public E update(E e) {
        return repository.save(e);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
