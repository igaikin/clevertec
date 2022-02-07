package com.clevertec.check.dao;

import com.clevertec.check.bean.Card;

import java.util.List;
import java.util.Optional;

public interface CardDao {
    Card create(Card card);

    List<Card> getAll(Card card);

    Optional<Card> get(long id);

    Card update(Card card);

    boolean delete(Long id);
}
