package ru.clevertec.check.service.impl;

import ru.clevertec.check.bean.Card;
import ru.clevertec.check.dao.CardDao;
import ru.clevertec.check.dao.impl.file.CardDaoFileImpl;
import ru.clevertec.check.exception.CardCreateException;
import ru.clevertec.check.exception.CheckException;
import ru.clevertec.check.service.CardService;

import java.util.List;
import java.util.Optional;

public class CardServiceImpl implements CardService {
    private static final CardDao cardDao = new CardDaoFileImpl();

    @Override
    public Optional<Card> create(Card card) {
        cardDao.get(card.getId()).ifPresent(c -> {
            throw new CardCreateException("Card with id: " + card.getId() + " already exists!");
        });
        return cardDao.create(card);
    }

    @Override
    public List<Card> getAll() {
        return cardDao.getAll();
    }

    @Override
    public Card get(long id) {
        return cardDao.get(id).orElseThrow(() -> new CheckException("No card with id: " + id));
    }

    @Override
    public Optional<Card> update(Card card) {
        return cardDao.update(card);
    }

    @Override
    public void delete(long id) {
        if (!cardDao.delete(id)) {
            throw new RuntimeException();//FIXME message
        }
    }
}
