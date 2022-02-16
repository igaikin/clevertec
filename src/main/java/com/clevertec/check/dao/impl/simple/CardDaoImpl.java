package com.clevertec.check.dao.impl.simple;

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
        return get(card.getId()).orElseThrow(() -> new RuntimeException("Can't create entity:" + card));
    }

    @Override
    public List<Card> getAll() {
        return new ArrayList<>(CARDS.values());
    }

    @Override
    public Optional<Card> get(Long id) {
        return Optional.ofNullable(CARDS.get(id));
    }

    @Override
    public Card update(Card card) {
        Card cardToUpdate = CARDS.get(card.getId());
        cardToUpdate.setDiscount(card.getDiscount());
        return cardToUpdate;
    }

    @Override
    public boolean delete(Long id) {
        Card deletedEntity = CARDS.remove(id);
        return deletedEntity != null;
    }
}
