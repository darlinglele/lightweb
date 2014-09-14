package com.pwc.simple;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqliteBD {
    private static Connection connection;

    public static void execute(String query, Object... args) {
        String q = String.format(query, args);
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:./data.db");
            connection.createStatement().execute(q);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    public static ResultSet executeQuery(String query, Object... args) throws Exception {
        String q = String.format(query, args);
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:./data.db");
            resultSet = connection.createStatement().executeQuery(q);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.close();
        }
        return resultSet;
    }

    public static void executeMany(String query, Object... args) {
        if (connection != null) {
            String q = String.format(query, args);
            try {
                connection.createStatement().execute(q);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:./data.db");
                executeMany(query, args);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
