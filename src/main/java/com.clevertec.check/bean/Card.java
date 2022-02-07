package com.clevertec.check.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Card {
    private long id;
    private BigDecimal discount;
}
