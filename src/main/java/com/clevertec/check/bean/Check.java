package com.clevertec.check.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class Check {
    private final List<Line> lines = new ArrayList<>();
    private TotalLine totalLine;
    private boolean isOnPromo;
    private BigDecimal promoDiscount;

    public void addLine(Line line) {
        lines.add(line);
    }

    @Data
    public static class Line {
        private int quantity;
        private String description;
        private BigDecimal fullCost;
        private BigDecimal promoCost;
        private boolean promo;
        private BigDecimal total;

        public Line(int quantity, Product product, BigDecimal total, boolean promo) {
            this.quantity = quantity;
            this.description = product.getDescription();
            this.fullCost = product.getCost();
            this.total = total;
            this.promo = promo;
        }
    }

    @Data
    @AllArgsConstructor
    public static class TotalLine {
        private BigDecimal discountPercent;
        private BigDecimal discountAmount;
        private BigDecimal totalCost;
    }
}
