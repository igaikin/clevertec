package com.clevertec.check.service;

import com.clevertec.check.service.exception.CheckException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ReaderUtil {
    private static final String INPUT_FORMAT = String.format("[id]-[quantity] card-[number card]%n"
            + "example: 12-1 8-2 5-1 card-1234");
    public static final String NO_ARGS =
            String.format("No arguments. Please use proper input format:%n" + INPUT_FORMAT);
    private static final String INVALID_INPUT =
            String.format("Invalid input format. Please, enter id, number of products and client card in format:%n"
                    + INPUT_FORMAT);

    public static void validate(String[] args) {
        if (args.length == 0) {
            throw new CheckException(NO_ARGS);
        }
    }

    public static Map<Long, Integer> getData(String[] args) {
        Map<Long, Integer> data = new HashMap<>();
        for (String arg : args) {
            if (!arg.startsWith("card-")) {
                long id = getProductId(arg);
                int quantity = getQuantity(arg);
                data.put(id, quantity);
            }
        }
        return data;
    }


    private static int getQuantity(String arg) {
        try {
            String quantityStr = arg.substring(arg.indexOf('-') + 1);
            return Integer.parseInt(quantityStr);
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            throw new CheckException(INVALID_INPUT);
        }
    }

    private static long getProductId(String arg) {
        try {
            String idStr = arg.substring(0, arg.indexOf('-'));
            return Long.parseLong(idStr);
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            throw new CheckException(INVALID_INPUT);
        }
    }

    public static Optional<Long> getCardId(String[] args) {
        String arg = args[args.length - 1];
        if (arg.startsWith("card-")) {
            try {
                String idStr = arg.substring(arg.indexOf('-') + 1);
                return Optional.of(Long.valueOf(idStr));
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                throw new CheckException(INVALID_INPUT);
            }
        }
        return Optional.empty();
    }
}
