package ru.clevertec.check.dao.impl.file;

import ru.clevertec.check.bean.Card;
import ru.clevertec.check.dao.CardDao;

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

public class CardDaoFileImpl implements CardDao {
    private static final Map<Long, Card> CARDS = new HashMap<>();
    private static final String SOURCE_FILE = "src/main/resources/in/cards.txt";
    private static final String SEPARATOR = ", ";
    private static final int ID_POSITION = 0;
    private static final int DISCOUNT_POSITION = 1;
    private static FileTime lastUpdateTime;

    private static void readFile() {
        try {
            Files.lines(Paths.get(SOURCE_FILE)).forEach(line -> {
                String[] arr = line.split(SEPARATOR);
                long id = Long.parseLong(arr[ID_POSITION]);
                BigDecimal discount = BigDecimal.valueOf(Double.parseDouble(arr[DISCOUNT_POSITION]));
                CARDS.put(id, new Card(id, discount));
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
        return CARDS.values()
                .stream()
                .map(card -> card.getId() + SEPARATOR + card.getDiscount())
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
    public Optional<Card> create(Card card) {
        readFileIfNeed();
        CARDS.put(card.getId(), card);
        writeFile();
        return Optional.ofNullable(get(card.getId()).orElseThrow(() -> new RuntimeException("Can't create entity:" + card)));
    }

    @Override
    public List<Card> getAll() {
        readFileIfNeed();
        return new ArrayList<>(CARDS.values());
    }

    @Override
    public Optional<Card> get(Long id) {
        readFileIfNeed();
        return Optional.ofNullable(CARDS.get(id));
    }

    @Override
    public Optional<Card> update(Card card) {
        readFileIfNeed();
        Card cardToUpdate = CARDS.get(card.getId());
        cardToUpdate.setDiscount(card.getDiscount());
        writeFile();
        return Optional.of(cardToUpdate);
    }

    @Override
    public boolean delete(Long id) {
        readFileIfNeed();
        Card deletedEntity = CARDS.remove(id);
        writeFile();
        return deletedEntity != null;
    }
}