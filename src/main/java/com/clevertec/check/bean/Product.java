package com.clevertec.check.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Product {
    private long id;
    private String description;
    private BigDecimal cost;
    private boolean isOnPromo;

}
