package com.clevertec.check;

import com.clevertec.check.exception.CheckException;
import com.clevertec.check.service.impl.CheckServiceImpl;
import com.clevertec.check.bean.Check;
import com.clevertec.check.bean.Product;
import com.clevertec.check.connection.ConnectionManager;
import com.clevertec.check.service.CardService;
import com.clevertec.check.service.CartService;
import com.clevertec.check.service.CheckService;
import com.clevertec.check.service.impl.CardServiceImpl;
import com.clevertec.check.service.impl.CartServiceImpl;
import com.clevertec.check.util.FileUtil;
import com.clevertec.check.util.FormatterUtil;
import com.clevertec.check.util.ReaderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

public class CheckRunner {
    public static final Logger LOG = LogManager.getLogger(CheckRunner.class);
    private static String IN;
    private static String OUT;
    private static boolean READ_FILE;
    private static boolean WRITE_FILE;
    private static final CardService cardService = new CardServiceImpl();
    private static final CartService cartService = new CartServiceImpl();
    private static final CheckService checkService = new CheckServiceImpl();
    public static void main(String[] args) {
        try {
            init();
            if (READ_FILE) {
                args = FileUtil.readArguments(IN);
            }
            ReaderUtil.validate(args);
            Map<Product, Integer> cart = cartService.getCart(ReaderUtil.getData(args));
            Optional<Long> cardId = ReaderUtil.getCardId(args);
            Check check = cardId.map(id -> checkService.createCheck(cart, cardService.get(id)))
                    .orElseGet(() -> checkService.createCheck(cart));
            String checkContent = FormatterUtil.getFormattedCheck(check);
            if (WRITE_FILE) {
                FileUtil.writeFile(FormatterUtil.getFormattedCheck(check), OUT);
            }
            System.out.println(checkContent);
        } catch (CheckException e) {
            System.err.println(e.getMessage());//FIXME logger!
        } catch (Exception e) {
            e.printStackTrace();//FIXME logger!
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
        LOG.info(String.format("[%s] - Read file - %s", CheckRunner.class, READ_FILE));
        LOG.info(String.format("From - %s", IN));
        LOG.info(String.format("Write file - %s", WRITE_FILE));
        LOG.info(String.format("To - %s", OUT));
    }
}
