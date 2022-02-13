package com.clevertec_check.service;

import com.clevertec_check.bean.Check;
import com.clevertec_check.bean.Product;

import java.util.Map;

public interface CheckService {
    Check createCheckBase(Map<Product, Integer> cart);

    boolean isApplyPromoDiscount(Map<Product, Integer> cart);

}
