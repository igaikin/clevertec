package com.clevertec.check.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Check {
    private final List<Line> lines = new ArrayList<>();
    private TotalLine totalLine;
    private boolean isOnPromo;
    private BigDecimal promoDiscount;

    public BigDecimal getPromoDiscount() {
        return promoDiscount;
    }

    public void setPromoDiscount(BigDecimal promoDiscount) {
        this.promoDiscount = promoDiscount;
    }

    public boolean isOnPromo() {
        return isOnPromo;
    }

    public void setOnPromo(boolean onPromo) {
        isOnPromo = onPromo;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void addLine(Line line) {
        lines.add(line);
    }

    public TotalLine getTotalLine() {
        return totalLine;
    }

    public void setTotalLine(TotalLine totalLine) {
        this.totalLine = totalLine;
    }

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

        public BigDecimal getPromoCost() {
            return promoCost;
        }

        public void setPromoCost(BigDecimal promoCost) {
            this.promoCost = promoCost;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public BigDecimal getFullCost() {
            return fullCost;
        }

        public void setFullCost(BigDecimal fullCost) {
            this.fullCost = fullCost;
        }

        public boolean getPromo() {
            return promo;
        }

        public void setPromo(boolean promo) {
            this.promo = promo;
        }

        public BigDecimal getTotal() {
            return total;
        }

        public void setTotal(BigDecimal total) {
            this.total = total;
        }
    }

    public static class TotalLine {
        private BigDecimal discountPercent;
        private BigDecimal discountAmount;
        private BigDecimal totalCost;

        public TotalLine(BigDecimal discountPercent, BigDecimal discountAmount, BigDecimal totalCost) {
            this.discountPercent = discountPercent;
            this.discountAmount = discountAmount;
            this.totalCost = totalCost;
        }

        public BigDecimal getDiscountPercent() {
            return discountPercent;
        }

        public void setDiscountPercent(BigDecimal discountPercent) {
            this.discountPercent = discountPercent;
        }

        public BigDecimal getDiscountAmount() {
            return discountAmount;
        }

        public void setDiscountAmount(BigDecimal discountAmount) {
            this.discountAmount = discountAmount;
        }

        public BigDecimal getTotalCost() {
            return totalCost;
        }

        public void setTotalCost(BigDecimal totalCost) {
            this.totalCost = totalCost;
        }
    }
}
