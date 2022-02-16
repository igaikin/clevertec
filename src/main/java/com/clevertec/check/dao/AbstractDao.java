package com.clevertec.check.dao;

import java.util.List;
import java.util.Optional;

public interface AbstractDao<T, K> {
    Optional<T> get(K id);

    List<T> getAll();

    T create(T entity);//FIXME Optional?

    T update(T entity);//FIXME Optional?

    boolean delete(K id);
}
