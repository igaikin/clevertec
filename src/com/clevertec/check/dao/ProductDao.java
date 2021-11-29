package com.clevertec.check.dao;

import com.clevertec.check.bean.Product;

import java.util.Optional;

public interface ProductDao {
    Optional<Product> getById(long id);
}
