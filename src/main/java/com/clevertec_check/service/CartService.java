package com.clevertec_check.service;

import com.clevertec_check.bean.Product;

import java.util.Map;

public interface CartService {
    Map<Product, Integer> getCart(Map<Long, Integer> data);
}
