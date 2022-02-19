package com.clevertec.check.dao.impl.jdbc;

import com.clevertec.check.bean.Product;
import com.clevertec.check.connection.ConnectionManager;
import com.clevertec.check.dao.ProductDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDaoJdbcImpl implements ProductDao {
    private static final String PRODUCT_ALL = "SELECT id, description, cost, promo FROM products ";
    private static final String CREATE_PRODUCT = "INSERT INTO products (description, cost, promo) VALUES (?, ?, ?)";
    private static final String GET_ALL = PRODUCT_ALL + "ORDER BY id";
    private static final String GET_BY_ID = PRODUCT_ALL + "WHERE id = ?";
    private static final String UPDATE_PRODUCT = "UPDATE products SET description = ?, cost = ?, promo = ? WHERE id = ?";
    private static final String DELETE_PRODUCT = "DELETE FROM products WHERE id = ?";
    private final ConnectionManager connectionManager = ConnectionManager.getInstance();

    @Override
    public Optional<Product> create(Product product) {
        try {
            Connection connection = connectionManager.getConnection();
            ResultSet keys;
            try (PreparedStatement statement = connection.prepareStatement(CREATE_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, product.getDescription());
                statement.setBigDecimal(2, product.getCost());
                statement.setBoolean(3, product.isOnPromo());
                statement.executeUpdate();
                keys = statement.getGeneratedKeys();
            }
            if (keys.next()) {
                product.setId(keys.getLong("id"));
                return Optional.of(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();//FIXME logger!
        }
        throw new RuntimeException("Couldn't create card: " + product);
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        try {
            Connection connection = connectionManager.getConnection();
            ResultSet resultSet;
            try (Statement statement = connection.createStatement()) {
                resultSet = statement.executeQuery(GET_ALL);
            }
            while (resultSet.next()) {
                products.add(processProduct(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();//FIXME logger!
        }
        return products;
    }

    private Product processProduct(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong("id"));
        product.setDescription(resultSet.getString("description"));
        product.setCost(resultSet.getBigDecimal("cost"));
        product.setOnPromo(resultSet.getBoolean("promo"));
        return product;
    }

    @Override
    public Optional<Product> get(Long id) {
        Product product = null;
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(GET_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                product = processProduct(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();//FIXME logger!
        }
        return Optional.ofNullable(product);
    }

    @Override
    public Optional<Product> update(Product product) {
        Connection connection = connectionManager.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCT);
            statement.setString(1, product.getDescription());
            statement.setBigDecimal(2, product.getCost());
            statement.setBoolean(3, product.isOnPromo());
            statement.setLong(4, product.getId());
            int rowsUpdated = statement.executeUpdate();
            JdbcUtil.checkRowsUpdated(rowsUpdated, 1);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();//FIXME logger!
        } finally {
            JdbcUtil.setAutoCommitTrue(connection);
        }
        return Optional.ofNullable(product);
    }

    @Override
    public boolean delete(Long id) {
        Connection connection = connectionManager.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT);
            statement.setLong(1, id);
            int rowsUpdated = statement.executeUpdate();
            JdbcUtil.checkRowsUpdated(rowsUpdated, 1);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();//FIXME logger!
        } finally {
            JdbcUtil.setAutoCommitTrue(connection);
        }
        return true;
    }
}
