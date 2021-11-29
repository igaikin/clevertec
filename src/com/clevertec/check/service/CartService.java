package com.clevertec.check.service;

import com.clevertec.check.bean.Product;
import com.clevertec.check.dao.ProductDao;
import com.clevertec.check.dao.impl.ProductDaoFileImpl;
import com.clevertec.check.service.exception.CheckException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CartService {
    private static final ProductDao productDao = new ProductDaoFileImpl();

    public static Map<Product, Integer> getCart(Map<Long, Integer> data) {
        Map<Product, Integer> cart = new HashMap<>();
        for (Map.Entry<Long, Integer> entry : data.entrySet()) {
            long id = entry.getKey();
            int quantity = entry.getValue();
            Optional<Product> optionalProduct = productDao.getById(id);
            Product product = optionalProduct.orElseThrow(() -> new CheckException("No product with id: " + id));
            cart.put(product, quantity);
        }
        return cart;
    }
}
