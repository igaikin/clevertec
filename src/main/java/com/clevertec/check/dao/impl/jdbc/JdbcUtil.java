package com.clevertec.check.dao.impl.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcUtil {

    public static void checkRowsUpdated(int rowsUpdated, int expectedRows) {
        if (rowsUpdated < expectedRows) {
            throw new RuntimeException("Not updated");
        } else if (rowsUpdated > expectedRows) {
            throw new RuntimeException("you created a VERY big problem");//FIXME message
        }
    }

    public static void setAutoCommitTrue(Connection connection) {
        try {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();//FIXME logger!
        }
    }
}
