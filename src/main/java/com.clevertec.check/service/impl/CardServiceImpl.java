package com.clevertec.check.service.impl;

import com.clevertec.check.bean.Card;
import com.clevertec.check.dao.CardDao;
import com.clevertec.check.dao.impl.CardDaoFileImpl;
import com.clevertec.check.exception.CheckException;
import com.clevertec.check.service.CardService;

import java.util.Optional;

public class CardServiceImpl implements CardService {
    private static final CardDao cardDao = new CardDaoFileImpl();

    public static Card getCard(long id) throws RuntimeException {
        Optional<Card> optionalCard = cardDao.get(id);
        return optionalCard.orElseThrow(() -> new CheckException("No card with id: " + id));
    }
}
