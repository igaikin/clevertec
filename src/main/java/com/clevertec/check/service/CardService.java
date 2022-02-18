package com.clevertec.check.service;

import com.clevertec.check.bean.Card;

import java.util.List;
import java.util.Optional;

public interface CardService {
    Optional<Card> create(Card card);

    List<Card> getAll();

    Card get(long id);

    Optional<Card> update(Card card);

    void delete(long id);
}
