package com.clevertec_check.dao.impl;

import com.clevertec_check.bean.Product;
import com.clevertec_check.dao.ProductDao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ProductDaoImpl implements ProductDao {
    private static final HashMap<Long, Product> PRODUCTS;

    static {
        PRODUCTS = new HashMap<>();
        PRODUCTS.put(1L, new Product(1, "Milk", BigDecimal.valueOf(2.99), false));
        PRODUCTS.put(2L, new Product(2, "Butter", BigDecimal.valueOf(4.99), false));
        PRODUCTS.put(3L, new Product(3, "Sugar", BigDecimal.valueOf(2.49), false));
        PRODUCTS.put(4L, new Product(4, "Bread", BigDecimal.valueOf(3.49), true));
        PRODUCTS.put(5L, new Product(5, "French Bread", BigDecimal.valueOf(1.99), false));
        PRODUCTS.put(6L, new Product(6, "Potato", BigDecimal.valueOf(2.99), false));
        PRODUCTS.put(7L, new Product(7, "Sweet Paper", BigDecimal.valueOf(1.69), false));
        PRODUCTS.put(8L, new Product(8, "Cucumber Organic", BigDecimal.valueOf(2.19), false));
        PRODUCTS.put(9L, new Product(9, "Tomatoes", BigDecimal.valueOf(2.99), false));
        PRODUCTS.put(10L, new Product(10, "Bananas", BigDecimal.valueOf(0.99), false));
        PRODUCTS.put(11L, new Product(11, "Orange", BigDecimal.valueOf(1.49), false));
        PRODUCTS.put(12L, new Product(12, "Apple", BigDecimal.valueOf(0.99), true));
        PRODUCTS.put(13L, new Product(13, "Beef Meat", BigDecimal.valueOf(3.99), false));
        PRODUCTS.put(14L, new Product(14, "Pork Meat", BigDecimal.valueOf(4.49), false));
        PRODUCTS.put(15L, new Product(15, "Turkey", BigDecimal.valueOf(2.34), true));
        PRODUCTS.put(16L, new Product(16, "Chicken", BigDecimal.valueOf(1.99), false));
        PRODUCTS.put(17L, new Product(17, "Cola", BigDecimal.valueOf(1.99), true));
        PRODUCTS.put(18L, new Product(18, "Mineral Water", BigDecimal.valueOf(0.69), true));
        PRODUCTS.put(19L, new Product(19, "Juice", BigDecimal.valueOf(4.69), true));
        PRODUCTS.put(20L, new Product(20, "Coffee", BigDecimal.valueOf(10.99), false));
        PRODUCTS.put(21L, new Product(21, "Tea", BigDecimal.valueOf(2.99), false));
    }

    @Override
    public Product create(Product product) {
        PRODUCTS.put(product.getId(), product);
        return product;
    }

    @Override
    public List<Product> getAll(Product product) {
        List<Product> products = new ArrayList<>();
        products.add(product);
        return products;
    }

    @Override
    public Optional<Product> get(Long id) {
        return Optional.of(PRODUCTS.get(id));
    }

    @Override
    public Product update(Product product) {
        Product updateProduct = PRODUCTS.get(product.getId());
        updateProduct.setDescription(product.getDescription());
        updateProduct.setCost(product.getCost());
        updateProduct.setOnPromo(product.isOnPromo());
        return updateProduct;
    }

    @Override
    public boolean delete(Long id) {
        PRODUCTS.remove(id);
        return true;
    }
}
