package com.clevertec_check.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Product {

    private long id;
    private String description;
    private BigDecimal cost;
    private boolean isOnPromo;

    public Product() {
    }
}
