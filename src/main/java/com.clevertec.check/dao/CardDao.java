package com.clevertec.check.dao;

import com.clevertec.check.bean.Card;

import java.util.Optional;

public interface CardDao {
    Optional<Card> get(long id);
}
