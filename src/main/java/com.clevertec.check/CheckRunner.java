package com.clevertec.check;

import com.clevertec.check.bean.Check;
import com.clevertec.check.bean.Product;
import com.clevertec.check.exception.CheckException;
import com.clevertec.check.service.CardService;
import com.clevertec.check.service.CartService;
import com.clevertec.check.service.CheckService;
import com.clevertec.check.service.impl.CardServiceImpl;
import com.clevertec.check.util.FileUtil;
import com.clevertec.check.util.FormatterUtil;
import com.clevertec.check.util.ReaderUtil;

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
    private static final CardService cardService = new CardServiceImpl();

    public static void main(String[] args) {
        try {
            init();
            if (READ_FILE) {
                args = FileUtil.readArguments(IN);
            }
            ReaderUtil.validate(args);
            Map<Product, Integer> cart = CartService.getCart(ReaderUtil.getData(args));
            Optional<Long> cardId = ReaderUtil.getCardId(args);
            CheckService checkService = new CheckService();
            Check check = (Check) cardId.map(id -> checkService.createCheck(cart, cardService.get(id)))
                    .orElseGet(() -> checkService.createCheck(cart));
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
        properties.load(new FileReader("src/main/resources/config.properties"));
        READ_FILE = Boolean.parseBoolean(properties.getProperty("read_file"));
        WRITE_FILE = Boolean.parseBoolean(properties.getProperty("write_file"));
        IN = properties.getProperty("input_file");
        OUT = properties.getProperty("output_file");
        System.out.println("Read file - " + READ_FILE);
        System.out.println("From - " + IN);
        System.out.println("Write file - " + WRITE_FILE);
        System.out.println("To - " + OUT);
    }
}
