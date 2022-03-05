package ru.clevertec.check.service;

import ru.clevertec.check.bean.Card;
import ru.clevertec.check.bean.Check;
import ru.clevertec.check.bean.Product;

import java.util.Map;

public interface CheckService {

    Check createCheck(Map<Product, Integer> cart, Card card);

    Check createCheck(Map<Product, Integer> cart);

}
