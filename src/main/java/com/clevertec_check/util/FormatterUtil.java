package com.clevertec_check.util;

import com.clevertec_check.bean.Check;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatterUtil {

    public static final String LINE_SEPARATOR =
            String.format("+-----+--------------------------------------------+----------+----------+%n");

    public static String getFormattedCheck(Check check) {
        StringBuilder output = new StringBuilder();
        output.append(getHeaderLine()).append(LINE_SEPARATOR);
        check.getLines().forEach(line -> output.append(getProductLine(check, line)));
        output.append(LINE_SEPARATOR).append(getTotalLine(check.getTotalLine()));
        return output.toString();
    }

    private static String getHeaderLine() {
        LocalDateTime time = LocalDateTime.now();
        return String.format("%n                               CASH  RECEIPT%n"
                        + "                                SUPERMARKET%n"
                        + "             ___    ***    *   *   ****    *****   ****    ___%n"
                        + "           ___     *   *   *   *   *   *   *       *   *     ___%n"
                        + "         ___       *       *   *   *   *   *       *   *       ___%n"
                        + "       ___          ***    *   *   ****    ****    ****          ___%n"
                        + "         ___           *   *   *   *       *       * *         ___%n"
                        + "           ___     *   *   *   *   *       *       *  *      ___%n"
                        + "             ___    ***     ***    *       *****   *   *   ___%n%n"
                        + "                      ADDRESS: You will not pass by us%n"
                        + "            TEL: +375 29 2713248      E-mail: gaikin@icloud.com%n%n"
                        + " Date: %s                                          Time: %s %n"
                        + LINE_SEPARATOR
                        + "| QTY |  DESCRIPTION                               |   COST   |   TOTAL  |%n",
                time.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                time.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    private static String getProductLine(Check check, Check.Line line) {
        String cost = getFormattedMoney(line.getFullCost());
        String totalCost = getFormattedMoney(line.getTotal());
        String promoCost = getFormattedMoney(line.getPromoCost());
        if (check.isOnPromo() && line.isPromo()) {
            return String.format("|%4d | %-43s|%9s |          |%n"
                            + "|     | PROMO PRODUCT Discount %.0f%%                 |%9s |%9s |%n"
                            + LINE_SEPARATOR,
                    line.getQuantity(), line.getDescription(), cost, check.getPromoDiscount(), promoCost, totalCost);
        }
        return String.format("|%4d | %-43s|%9s |%9s |%n" + LINE_SEPARATOR,
                line.getQuantity(), line.getDescription(), cost, totalCost);
    }

    private static String getFormattedMoney(BigDecimal fullCost) {
        return "$" + String.format("%.2f", fullCost);
    }

    private static String getTotalLine(Check.TotalLine totalLine) {
        String total = getFormattedMoney(totalLine.getTotalCost());
        if (totalLine.getDiscountPercent().compareTo(BigDecimal.ZERO) > 0) {
            String discountAmount = getFormattedMoney(totalLine.getDiscountAmount());
            String discountPercent = String.format("%.0f", totalLine.getDiscountPercent()) + "%";
            return String.format("| APPlY DISCOUNT CARD:%3s  DISCOUNT AMOUNT:%6s  |     TOTAL:%9s |%n"
                            + "+--------------------------------------------------+---------------------+%n",
                    discountPercent, discountAmount, total);
        } else {
            return String.format("|                                                  |     TOTAL:%9s |%n"
                    + "+--------------------------------------------------+---------------------+%n", total);
        }
    }
}
