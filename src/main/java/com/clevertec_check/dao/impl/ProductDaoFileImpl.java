package com.clevertec_check.dao.impl;

import com.clevertec_check.bean.Product;
import com.clevertec_check.dao.ProductDao;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductDaoFileImpl implements ProductDao {
    private static final Map<Long, Product> PRODUCTS = new HashMap<>();

    static {
        try {
            Files.lines(Paths.get("src/main/resources/in/products.txt")).forEach(line -> {
                String[] arr = line.split(", ");
                long id = Long.parseLong(arr[0]);
                String description = arr[1];
                BigDecimal cost = BigDecimal.valueOf(Double.parseDouble(arr[2]));
                boolean isOnPromo = Boolean.parseBoolean(arr[3]);
                PRODUCTS.put(id, new Product(id, description, cost, isOnPromo));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Product create(Product product) {
        return null;
    }

    @Override
    public List<Product> getAll(Product product) {
        return null;
    }

    @Override
    public Optional<Product> get(Long id) {
        return Optional.of(PRODUCTS.get(id));
    }

    @Override
    public Product update(Product product) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}