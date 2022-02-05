package com.clevertec.check.bean;

import java.math.BigDecimal;

public class Product {

    private long id;
    private String description;
    private BigDecimal cost;
    private boolean isOnPromo;

    public Product(long id, String description, BigDecimal cost, boolean promo) {
        this.id = id;
        this.description = description;
        this.cost = cost;
        this.isOnPromo = promo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public boolean isOnPromo() {
        return isOnPromo;
    }

    public void setOnPromo(boolean onPromo) {
        this.isOnPromo = onPromo;
    }
}
