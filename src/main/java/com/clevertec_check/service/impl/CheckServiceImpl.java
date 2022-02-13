package com.clevertec_check.service.impl;

import com.clevertec_check.bean.Card;
import com.clevertec_check.bean.Check;
import com.clevertec_check.bean.Product;

import java.math.BigDecimal;
import java.util.Map;

public class CheckServiceImpl {

    private static final int REQUIRED_NUMBER_OF_PROMO_PRODUCT = 5;
    private static final BigDecimal PROMO_DISCOUNT = BigDecimal.TEN;
    private static BigDecimal totalPromoDiscount = BigDecimal.ZERO;

    private Check createCheckBase(Map<Product, Integer> cart) {
        Check check = new Check();
        boolean countPromo = isApplyPromoDiscount(cart);
        check.setOnPromo(countPromo);
        check.setPromoDiscount(PROMO_DISCOUNT);
        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            check.addLine(getProductLine(entry, countPromo));
        }
        return check;
    }

    private boolean isApplyPromoDiscount(Map<Product, Integer> cart) {
        int numberOfPromoProducts = 0;
        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            if (entry.getKey().isOnPromo()) {
                numberOfPromoProducts += entry.getValue();
            }
            if (numberOfPromoProducts >= REQUIRED_NUMBER_OF_PROMO_PRODUCT) {
                return true;
            }
        }
        return false;
    }

    public Check createCheck(Map<Product, Integer> cart, Card card) {
        Check check = createCheckBase(cart);
        check.setTotalLine(getTotalLine(cart, card.getDiscount()));
        return check;
    }

    public Check createCheck(Map<Product, Integer> cart) {
        Check check = createCheckBase(cart);
        check.setTotalLine(getTotalLine(cart));
        return check;
    }

    private static Check.Line getProductLine(Map.Entry<Product, Integer> entry, boolean countPromo) {
        BigDecimal total = entry.getKey().getCost().multiply(BigDecimal.valueOf(entry.getValue()));
        Check.Line line = new Check.Line(entry.getValue(), entry.getKey(), total, entry.getKey().isOnPromo());
        if (countPromo && entry.getKey().isOnPromo()) {
            BigDecimal cost = entry.getKey().getCost();
            BigDecimal promoAmount = cost.multiply(PROMO_DISCOUNT).movePointLeft(2);
            cost = cost.subtract(promoAmount);
            line.setPromoCost(cost);

            BigDecimal productPromoDiscount = total.multiply(PROMO_DISCOUNT).movePointLeft(2);
            total = total.subtract(productPromoDiscount);
            line.setTotal(total);
            totalPromoDiscount = totalPromoDiscount.add(productPromoDiscount);
        }
        return line;
    }

    private Check.TotalLine getTotalLine(Map<Product, Integer> cart) {
        return getTotalLine(cart, BigDecimal.ZERO);
    }

    private Check.TotalLine getTotalLine(Map<Product, Integer> cart, BigDecimal discountPercent) {
        BigDecimal totalCost = BigDecimal.ZERO;
        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            BigDecimal productTotal = entry.getKey().getCost().multiply(BigDecimal.valueOf(entry.getValue()));
            totalCost = totalCost.add(productTotal);
        }
        BigDecimal discountAmount = totalCost.multiply(discountPercent).movePointLeft(2);
        BigDecimal costWithDiscount = totalCost.subtract(discountAmount);
        discountAmount = discountAmount.add(totalPromoDiscount);
        return new Check.TotalLine(discountPercent, discountAmount, costWithDiscount);
    }
}
