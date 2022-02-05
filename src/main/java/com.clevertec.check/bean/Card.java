package com.clevertec.check.bean;

import java.math.BigDecimal;

public class Card {
    private long id;
    private BigDecimal discount;

    public Card(long id, BigDecimal discount) {
        this.id = id;
        this.discount = discount;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
