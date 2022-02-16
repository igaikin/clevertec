package com.clevertec.check.service;

import com.clevertec.check.bean.Card;
import com.clevertec.check.bean.Check;
import com.clevertec.check.bean.Product;

import java.util.Map;

public interface CheckService {

    Check createCheck(Map<Product, Integer> cart, Card card);

    Check createCheck(Map<Product, Integer> cart);

}
