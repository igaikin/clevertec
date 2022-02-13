package com.clevertec_check.service.impl;

import com.clevertec_check.bean.Card;
import com.clevertec_check.dao.CardDao;
import com.clevertec_check.dao.impl.CardDaoJdbcImpl;
import com.clevertec_check.exception.CardCreateException;
import com.clevertec_check.exception.CardUpdateException;
import com.clevertec_check.exception.CheckException;
import com.clevertec_check.service.CardService;

import java.util.List;
import java.util.Optional;

public class CardServiceImpl implements CardService {
    private static final CardDao cardDao = new CardDaoJdbcImpl();

    @Override
    public Card create(Card card) {
        Optional<Card> existing = cardDao.get(card.getId());
        if (existing.isPresent()) {//TODO
            throw new CardCreateException("Card with id: " + card.getId() + " already exists!");
        }
        return cardDao.create(card);
    }

    @Override
    public List<Card> getAll(Card card) {
        return cardDao.getAll(card);
    }

    @Override
    public Card get(long id) throws RuntimeException {
        Optional<Card> optionalCard = cardDao.get(id);
        return optionalCard.orElseThrow(() -> new CheckException("No card with id: " + id));
    }

    @Override
    public Card update(Card card) {
        Optional<Card> existing = cardDao.get(card.getId());
        if (!existing.isPresent()) {
            throw new CardUpdateException("Card with id: " + card.getId() + " does not exist");
        }
        return cardDao.update(card);
    }

    @Override
    public boolean delete(long id) {
        return cardDao.delete(id);
    }
}