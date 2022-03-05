package ru.clevertec.check.service;

import ru.clevertec.check.bean.Product;

import java.util.Map;

public interface CartService {
    Map<Product, Integer> getCart(Map<Long, Integer> data);
}
