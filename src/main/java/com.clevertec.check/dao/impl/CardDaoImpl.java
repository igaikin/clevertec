package com.clevertec.check.dao.impl;

import com.clevertec.check.bean.Card;
import com.clevertec.check.dao.CardDao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class CardDaoImpl implements CardDao {
    private static final HashMap<Long, Card> CARDS;

    static {
        CARDS = new HashMap<>();
        CARDS.put(1111L, new Card(1111, BigDecimal.valueOf(3.0)));
        CARDS.put(2222L, new Card(2222, BigDecimal.valueOf(5.0)));
        CARDS.put(3333L, new Card(3333, BigDecimal.valueOf(7.0)));
    }

    @Override
    public Card create(Card card) {
        CARDS.put(card.getId(), card);
        return card;
    }

    @Override
    public List<Card> getAll(Card card) {
        List<Card> cards = new ArrayList<>();
        cards.add(card);
        return cards;
    }

    @Override
    public Optional<Card> get(long id) {
        return Optional.of(CARDS.get(id));
    }

    @Override
    public Card update(Card card) {
        Card updateCard = CARDS.get(card.getId());
        updateCard.setDiscount(card.getDiscount());
        return updateCard;
    }

    @Override
    public boolean delete(Long id) {
        CARDS.remove(id);
        return true;
    }
}
