package com.clevertec_check.service;

import com.clevertec_check.bean.Card;
import com.clevertec_check.bean.Check;
import com.clevertec_check.bean.Product;

import java.util.Map;

public interface CheckService {

    Check createCheck(Map<Product, Integer> cart, Card card);

    Check createCheck(Map<Product, Integer> cart);

}
