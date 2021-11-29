package com.clevertec.check.dao.impl;

import com.clevertec.check.bean.Card;
import com.clevertec.check.dao.CardDao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Optional;

public class CardDaoImpl implements CardDao {
    private static final HashMap<Long, Card> CARDS;

    static {
        CARDS = new HashMap<>();
        CARDS.put(1111L, new Card(1111, BigDecimal.valueOf(3.0)));
        CARDS.put(2222L, new Card(2222, BigDecimal.valueOf(5.0)));
        CARDS.put(3333L, new Card(3333, BigDecimal.valueOf(7.0)));
    }

    public Optional<Card> getById(long id) {
        return Optional.of(CARDS.get(id));
    }
}
