package com.clevertec.check.service.impl;

import com.clevertec.check.bean.Card;
import com.clevertec.check.dao.CardDao;
import com.clevertec.check.dao.impl.CardDaoFileImpl;
import com.clevertec.check.exception.CardCreateException;
import com.clevertec.check.exception.CardUpdateException;
import com.clevertec.check.exception.CheckException;
import com.clevertec.check.service.CardService;

import java.util.List;
import java.util.Optional;

public class CardServiceImpl implements CardService {
    private static final CardDao cardDao = new CardDaoFileImpl();

    @Override
    public Card create(Card card) {
        Optional<Card> existing = cardDao.get(card.getId());
        if (existing != null) {
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
