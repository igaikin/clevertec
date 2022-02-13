package com.clevertec_check.service.impl;

import com.clevertec_check.bean.Product;
import com.clevertec_check.dao.ProductDao;
import com.clevertec_check.dao.impl.ProductDaoJdbcImpl;
import com.clevertec_check.exception.CheckException;
import com.clevertec_check.service.CartService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CartServiceImpl implements CartService {
    private static final ProductDao productDao = new ProductDaoJdbcImpl();

    public Map<Product, Integer> getCart(Map<Long, Integer> data) {
        Map<Product, Integer> cart = new HashMap<>();
        for (Map.Entry<Long, Integer> entry : data.entrySet()) {
            long id = entry.getKey();
            int quantity = entry.getValue();
            Optional<Product> optionalProduct = productDao.get(id);
            Product product = optionalProduct.orElseThrow(() -> new CheckException("No product with id: " + id));
            cart.put(product, quantity);
        }
        return cart;
    }
}
