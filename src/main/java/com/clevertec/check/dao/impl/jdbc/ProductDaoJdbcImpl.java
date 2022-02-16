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

public class ProductDaoJdbcImpl implements ProductDao {//FIXME
    private static final String PRODUCT_ALL = "SELECT id, description, cost, promo FROM products ";
    private static final String CREATE_PRODUCT = "INSERT INTO products  (id, description, cost, promo) "//FIXME id
            + "VALUES (?, ?, ?, ?)";
    private static final String GET_ALL = PRODUCT_ALL + "ORDER BY id";
    private static final String GET_BY_ID = PRODUCT_ALL + "WHERE id = ?";
    private static final String UPDATE_PRODUCT = "UPDATE products SET id = ?, description = ?, cost = ?, promo = ? "//FIXME set id
            + "WHERE id = ?";
    private static final String DELETE_PRODUCT = "DELETE FROM products WHERE id = ?";

    private final ConnectionManager connectionManager = ConnectionManager.getInstance();

    private Product processProduct(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong("id"));
        product.setDescription(resultSet.getString("description"));
        product.setCost(resultSet.getBigDecimal("cost"));
        product.setOnPromo(resultSet.getBoolean("promo"));
        return product;
    }

    @Override
    public Product create(Product product) {
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE_PRODUCT, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, product.getId());
            statement.setString(2, product.getDescription());
            statement.setBigDecimal(3, product.getCost());
            statement.setBoolean(4, product.isOnPromo());
            statement.executeUpdate();
            ResultSet keys = statement.getGeneratedKeys();
            if (keys.next()) {
                product.setId(keys.getLong(1));
                return product;
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
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL);
            while (resultSet.next()) {
                products.add(processProduct(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();//FIXME logger!
        }
        return products;
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
    public Product update(Product product) {
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCT);
            statement.setLong(1, product.getId());
            statement.setString(2, product.getDescription());
            statement.setBigDecimal(3, product.getCost());
            statement.setBoolean(4, product.isOnPromo());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();//FIXME logger!
        }
        return product;
    }

    @Override
    public boolean delete(Long id) {
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();//FIXME logger!
        }
        return true;
    }
}
