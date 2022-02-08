package com.clevertec_check.dao;

import java.util.List;
import java.util.Optional;

public interface AbstractDao<T, K> {
    T create(T entity);

    List<T> getAll(T entity);

    Optional<T> get(K id);

    T update(T entity);

    boolean delete(K id);
}
