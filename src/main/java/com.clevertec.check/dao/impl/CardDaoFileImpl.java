package com.clevertec.check.dao.impl;

import com.clevertec.check.bean.Card;
import com.clevertec.check.dao.CardDao;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CardDaoFileImpl implements CardDao {
    private static final Map<Long, Card> CARDS = new HashMap<>();

    static {
        try {
            Files.lines(Paths.get("src/main/resources/in/cards.txt")).forEach(line -> {
                String[] arr = line.split(", ");
                long id = Long.parseLong(arr[0]);
                BigDecimal discount = BigDecimal.valueOf(Double.parseDouble(arr[1]));
                CARDS.put(id, new Card(id, discount));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Optional<Card> get(long id) {
        return Optional.of(CARDS.get(id));
    }
}
