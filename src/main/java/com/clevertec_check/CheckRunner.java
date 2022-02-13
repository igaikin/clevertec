package com.clevertec_check;

import com.clevertec_check.bean.Check;
import com.clevertec_check.bean.Product;
import com.clevertec_check.connection.ConnectionManager;
import com.clevertec_check.exception.CheckException;
import com.clevertec_check.service.CardService;
import com.clevertec_check.service.CartService;
import com.clevertec_check.service.impl.CardServiceImpl;
import com.clevertec_check.service.impl.CartServiceImpl;
import com.clevertec_check.service.impl.CheckServiceImpl;
import com.clevertec_check.util.FileUtil;
import com.clevertec_check.util.FormatterUtil;
import com.clevertec_check.util.ReaderUtil;

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
    private static final CartService cartService = new CartServiceImpl();

    public static void main(String[] args) {
        try {
            init();
            if (READ_FILE) {
                args = FileUtil.readArguments(IN);
            }
            ReaderUtil.validate(args);
            Map<Product, Integer> cart = cartService.getCart(ReaderUtil.getData(args));
            Optional<Long> cardId = ReaderUtil.getCardId(args);
            CheckServiceImpl checkServiceImpl = new CheckServiceImpl();
            Check check = cardId.map(id -> checkServiceImpl.createCheck(cart, cardService.get(id)))
                    .orElseGet(() -> checkServiceImpl.createCheck(cart));
            String checkContent = FormatterUtil.getFormattedCheck(check);
            if (WRITE_FILE) {
                FileUtil.writeFile(FormatterUtil.getFormattedCheck(check), OUT);
            }
            System.out.println(checkContent);
        } catch (CheckException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.getInstance().tearDown();
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
