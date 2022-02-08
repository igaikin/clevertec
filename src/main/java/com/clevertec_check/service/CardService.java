package com.clevertec_check.service;

import com.clevertec_check.bean.Card;

import java.util.List;

public interface CardService {
    Card create(Card card);

    List<Card> getAll(Card card);

    Card get(long id);

    Card update(Card card);

    boolean delete(long id);
}
