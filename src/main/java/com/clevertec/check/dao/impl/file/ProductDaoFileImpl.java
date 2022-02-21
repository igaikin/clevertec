package com.clevertec.check.dao.impl.file;

import com.clevertec.check.bean.Product;
import com.clevertec.check.dao.ProductDao;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


public class ProductDaoFileImpl implements ProductDao {
    static final Map<Long, Product> PRODUCTS = new HashMap<>();
    static final String SOURCE_FILE = "src/main/resources/in/products.txt";
    private static final String SEPARATOR = ", ";
    private static final int ID_POSITION = 0;
    private static final int DESCRIPTION_POSITION = 1;
    private static final int COST_POSITION = 2;
    private static final int PROMO_POSITION = 3;
    private static FileTime lastUpdateTime;

    private static void readFile() {
        try {
            Files.lines(Paths.get(SOURCE_FILE)).forEach(line -> {
                String[] arr = line.split(SEPARATOR);
                long id = Long.parseLong(arr[ID_POSITION]);
                String description = arr[DESCRIPTION_POSITION];
                BigDecimal discount = BigDecimal.valueOf(Double.parseDouble(arr[COST_POSITION]));
                boolean promo = Boolean.parseBoolean(arr[PROMO_POSITION]);
                PRODUCTS.put(id, new Product(id, description, discount, promo));
            });
            lastUpdateTime = Files.getLastModifiedTime(Paths.get(SOURCE_FILE));
        } catch (IOException e) {
            e.printStackTrace();//FIXME logger!//FIXME no any stacktraces
        }
    }

    private static void readFileIfNeed() {
        try {
            FileTime actualTime = Files.getLastModifiedTime(Paths.get(SOURCE_FILE));
            if (!actualTime.equals(lastUpdateTime)) {
                readFile();
            }
        } catch (IOException e) {
            e.printStackTrace();//FIXME logger!
        }
    }

    private static List<String> getLines() {
        return PRODUCTS.values()
                .stream()
                .map(product -> product.getId() + SEPARATOR
                        + product.getDescription() + SEPARATOR
                        + product.getCost() + SEPARATOR
                        + product.isOnPromo())
                .collect(Collectors.toList());
    }

    private static void writeFile() {
        try {
            Files.write(Paths.get(SOURCE_FILE), getLines());
        } catch (IOException e) {
            e.printStackTrace();//FIXME logger!
        }
    }

    @Override
    public Optional<Product> create(Product product) {
        readFileIfNeed();
        PRODUCTS.put(product.getId(), product);
        writeFile();
        return Optional.empty();
    }

    @Override
    public List<Product> getAll() {
        readFileIfNeed();
        return new ArrayList<>(PRODUCTS.values());
    }

    @Override
    public Optional<Product> get(Long id) {
        readFileIfNeed();
        return Optional.ofNullable(PRODUCTS.get(id));
    }

    @Override
    public Optional<Product> update(Product product) {
        readFileIfNeed();
        Product productToUpdate = PRODUCTS.get(product.getId());
        productToUpdate.setDescription(product.getDescription());
        productToUpdate.setCost(product.getCost());
        productToUpdate.setOnPromo(product.isOnPromo());
        writeFile();
        return Optional.of(productToUpdate);
    }

    @Override
    public boolean delete(Long id) {
        readFileIfNeed();
        Product deletedEntity = PRODUCTS.remove(id);
        writeFile();
        return deletedEntity != null;
    }
}
