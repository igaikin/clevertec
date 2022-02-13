package com.clevertec_check.dao.impl;

import com.clevertec_check.bean.Card;
import com.clevertec_check.connection.ConnectionManager;
import com.clevertec_check.dao.CardDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CardDaoJdbcImpl implements CardDao {
    private static final String CARD_ALL = "SELECT id, discount FROM cards ";
    private static final String CREATE_CARD = "INSERT INTO cards  (id, discount) VALUES (?, ?)";
    private static final String GET_ALL = CARD_ALL + "ORDER BY id";
    private static final String GET_BY_ID = CARD_ALL + "WHERE id = ?";
    private static final String UPDATE_CARD = "UPDATE cards SET id = ?, discount = ? WHERE id = ?";
    private static final String DELETE_CARD = "DELETE FROM cards WHERE id = ?;";

    private final ConnectionManager connectionManager = ConnectionManager.getInstance();

    private Card processCard(ResultSet resultSet) throws SQLException {
        Card card = new Card();
        card.setId(resultSet.getLong("id"));
        card.setDiscount(resultSet.getBigDecimal("discount"));
        return card;
    }

    @Override
    public Card create(Card card) {
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE_CARD, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, card.getId());
            statement.setBigDecimal(2, card.getDiscount());
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                card.setId(keys.getLong(1));
                return card;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Couldn't create card: " + card);
    }

    @Override
    public List<Card> getAll(Card card) {
        List<Card> cards = new ArrayList<>();
        try {
            Connection connection = connectionManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL);
            while (resultSet.next()) {
                cards.add(processCard(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public Optional<Card> get(Long id) {
        Card card = null;
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                card = processCard(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(card);
    }

    @Override
    public Card update(Card card) {
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_CARD);
            statement.setLong(1, card.getId());
            statement.setBigDecimal(2, card.getDiscount());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return card;
    }

    @Override
    public boolean delete(Long id) {
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_CARD);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
