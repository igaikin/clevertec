package com.clevertec.check.service;

import com.clevertec.check.bean.Card;
import com.clevertec.check.dao.CardDao;
import com.clevertec.check.dao.impl.CardDaoFileImpl;
import com.clevertec.check.exception.CheckException;

import java.util.Optional;

public class CardService {
    private static final CardDao cardDao = new CardDaoFileImpl();

    public static Card getCard(long id) throws RuntimeException {
        Optional<Card> optionalCard = cardDao.getById(id);
        return optionalCard.orElseThrow(() -> new CheckException("No card with id: " + id));
    }
}
