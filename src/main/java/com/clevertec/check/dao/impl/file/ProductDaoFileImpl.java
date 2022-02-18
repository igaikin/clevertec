package com.clevertec.check.dao.impl.file;

import com.clevertec.check.bean.Product;
import com.clevertec.check.dao.ProductDao;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
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
            e.printStackTrace();//FIXME logger!
        }
    }

    @Override
    public Optional<Product> create(Product product) {
        return Optional.empty();
    }

    @Override
    public List<Product> getAll() {
        return Collections.emptyList();
    }

    @Override
    public Optional<Product> get(Long id) {
        return Optional.of(PRODUCTS.get(id));
    }

    @Override
    public Optional<Product> update(Product product) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
