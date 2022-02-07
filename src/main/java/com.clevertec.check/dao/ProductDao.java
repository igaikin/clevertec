package com.clevertec.check.dao;

import com.clevertec.check.bean.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Product create(Product product);
    List<Product> getAll(Product product);
    Optional<Product> get(long id);
    Product update(Product product);
    boolean delete(Long id);
}
