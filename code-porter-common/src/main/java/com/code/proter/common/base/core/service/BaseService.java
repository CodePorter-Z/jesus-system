package com.code.proter.common.base.core.service;


import com.code.proter.common.base.core.repository.BaseRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseService<ID extends Number, T extends Serializable, R extends BaseRepository<ID, T>> {

    R getRepository();

    default Optional<T> findById(ID id) {
        return getRepository().findById(id);
    }

    default List<T> findAll() {
        return getRepository().findAll();
    }

    default T save(T entity) {
        return getRepository().save(entity);
    }

    default List<T> save(List<T> entities) {
        return getRepository().saveAll(entities);
    }

    default void delete(T entity) {
        getRepository().delete(entity);
    }

    default void delete(ID id) {
        getRepository().deleteById(id);
    }
}