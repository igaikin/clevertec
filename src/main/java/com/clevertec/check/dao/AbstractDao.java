package com.clevertec.check.dao;

import java.util.List;
import java.util.Optional;

public interface AbstractDao<T, K> {
    Optional<T> get(K id);

    List<T> getAll();

    Optional<T> create(T entity);

    Optional<T> update(T entity);

    boolean delete(K id);
}
