package com.clevertec.check.dao.impl.jdbc;

import com.clevertec.check.bean.Card;
import com.clevertec.check.connection.ConnectionManager;
import com.clevertec.check.dao.CardDao;

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
    private static final String CREATE_CARD = "INSERT INTO cards  (discount) VALUES (?)";
    private static final String GET_ALL = CARD_ALL + "ORDER BY id";
    private static final String GET_BY_ID = CARD_ALL + "WHERE id = ?";
    private static final String UPDATE_CARD = "UPDATE cards SET discount = ? WHERE id = ?";
    private static final String DELETE_CARD = "DELETE FROM cards WHERE id = ?";
    private final ConnectionManager connectionManager = ConnectionManager.getInstance();

    @Override
    public Card create(Card card) {
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE_CARD, Statement.RETURN_GENERATED_KEYS);
            statement.setBigDecimal(1, card.getDiscount());
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                card.setId(keys.getLong("id"));
                return card;
            }
        } catch (SQLException e) {
            e.printStackTrace();//FIXME logger!
        }
        throw new RuntimeException("Couldn't create card: " + card);
    }

    @Override
    public List<Card> getAll() {
        List<Card> cards = new ArrayList<>();
        try {
            Connection connection = connectionManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL);
            while (resultSet.next()) {
                cards.add(processCard(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();//FIXME logger!
        }
        return cards;
    }

    private Card processCard(ResultSet resultSet) throws SQLException {
        Card card = new Card();
        card.setId(resultSet.getLong("id"));
        card.setDiscount(resultSet.getBigDecimal("discount"));
        return card;
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
            e.printStackTrace();//FIXME logger!
        }
        return Optional.ofNullable(card);
    }

    @Override
    public Card update(Card card) {
        Connection connection = connectionManager.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(UPDATE_CARD);
            statement.setBigDecimal(1, card.getDiscount());
            statement.setLong(2, card.getId());
            int rowsUpdated = statement.executeUpdate();
            checkRowsUpdated(rowsUpdated, 1);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();//FIXME logger!
        } finally {
            setAutoCommitTrue(connection);
        }
        return get(card.getId()).orElseThrow(RuntimeException::new);//FIXME message
    }

    private void checkRowsUpdated(int rowsUpdated, int expectedRows) {
        if (rowsUpdated < expectedRows) {
            throw new RuntimeException("Not updated");
        } else if (rowsUpdated > expectedRows) {
            throw new RuntimeException("...");//FIXME message
        }
    }

    private void setAutoCommitTrue(Connection connection) {
        try {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();//FIXME logger!
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_CARD);
            statement.setLong(1, id);
            statement.executeUpdate();//FIXME chek rows, autocommit, return true, false
        } catch (SQLException e) {
            e.printStackTrace();//FIXME logger!
        }
        return true;
    }
}
