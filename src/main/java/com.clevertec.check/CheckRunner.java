package com.clevertec.check;

import com.clevertec.check.util.FileUtil;
import com.clevertec.check.bean.Check;
import com.clevertec.check.bean.Product;
import com.clevertec.check.service.CardService;
import com.clevertec.check.service.CartService;
import com.clevertec.check.service.CheckService;
import com.clevertec.check.service.FormatterUtil;
import com.clevertec.check.service.ReaderUtil;
import com.clevertec.check.exception.CheckException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

public class CheckRunner {
    private static String IN;
    private static String OUT;
    private static boolean READ_FILE;
    private static boolean WRITE_FILE;

    public static void main(String[] args) {
        try {
            init();
            if (READ_FILE) {
                args = FileUtil.readArguments(IN);
            }
            ReaderUtil.validate(args);
            Map<Product, Integer> cart = CartService.getCart(ReaderUtil.getData(args));
            Optional<Long> cardId = ReaderUtil.getCardId(args);
            Check check = cardId.map(id -> CheckService.createCheck(cart, CardService.getCard(id)))
                    .orElseGet(() -> CheckService.createCheck(cart));
            String checkContent = FormatterUtil.getFormattedCheck(check);
            if (WRITE_FILE) {
                FileUtil.writeFile(FormatterUtil.getFormattedCheck(check), OUT);
            }
            System.out.println(checkContent);
        } catch (CheckException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void init() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader("config.properties"));
        READ_FILE = Boolean.parseBoolean(properties.getProperty("read_file"));
        WRITE_FILE = Boolean.parseBoolean(properties.getProperty("write_file"));
        IN = properties.getProperty("input_file");
        OUT = properties.getProperty("output_file");
        System.out.println("Read file - " + READ_FILE);
        System.out.println("Write file - " + WRITE_FILE);
        System.out.println("From - " + IN);
        System.out.println("To - " + OUT);
    }
}
