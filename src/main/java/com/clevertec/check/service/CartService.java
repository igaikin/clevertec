package com.clevertec.check.service;

import com.clevertec.check.bean.Product;

import java.util.Map;

public interface CartService {
    Map<Product, Integer> getCart(Map<Long, Integer> data);
}
