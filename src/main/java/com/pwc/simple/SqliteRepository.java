package com.pwc.simple;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SqliteRepository<E> {

    private final Class<E> clazz;
    private String fileName;
    private String regex;

    public SqliteRepository(Class<E> clazz) {
        this.clazz = clazz;
    }

    public List<E> findAll(String query) {
        Connection connection = null;
        ResultSet tagSet;
        List<E> entityList = new ArrayList<>();
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:./data.db");
            tagSet = connection.createStatement().executeQuery(query);
            while (tagSet != null && tagSet.next()) {
                E entity = this.clazz.newInstance();
                for (Field f : entity.getClass().getFields()) {
                    try {
                        if (f.getType() == int.class) {
                            f.set(entity, tagSet.getInt(f.getName()));
                        }
                        if (f.getType() == String.class) {
                            f.set(entity, tagSet.getString(f.getName()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                entityList.add(entity);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return entityList;
    }

    public void save(Collection<E> entities) {
        String tableName = this.clazz.getName().split("\\.")[this.clazz.getName().split("\\.").length - 1] + "s";
        SqliteBD.execute("delete from %s", tableName);
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:data.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Statement statement = null;

        try {
            statement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            statement.addBatch(String.format("delete from %s", tableName));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (E entity : entities) {
            String values = entity.toString();
            try {
                statement.addBatch(String.format("insert into %s values(%s)", tableName, values));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public E first(String query) {
        List<E> all = findAll(query);
        return all.stream().findFirst().orElse(null);
    }
}
