package com.library.util.DB;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBConnector {
    private static Properties properties;
    private final static String POSTGRES = "db.properties";

    private static volatile DBConnector INSTANCE;

    private DBConnector() {
        setStartProperties();
        registerDriver();
    }

    private DBConnector(String filename) {
        setStartProperties(filename);
        registerDriver();
    }

    private void registerDriver() {
        try {
            Class.forName(properties.getProperty("datasource.driver-class-name"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("driver not found");
        }
    }

    private void setStartProperties() {
        try {
            properties = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(POSTGRES);
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException | NullPointerException e) {
            throw new RuntimeException("Reading property failed", e);
        }
    }

    private void setStartProperties(String filename) {
        try {
            properties = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException | NullPointerException e) {
            throw new RuntimeException("Reading property failed", e);
        }
    }

    public static DBConnector getInstance() {
        if (INSTANCE == null) {
            synchronized (DBConnector.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DBConnector();
                }
            }
        }
        return INSTANCE;
    }

    public static DBConnector getInstance(String filename) {
        if (INSTANCE == null) {
            synchronized (DBConnector.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DBConnector(filename);
                }
            }
        }
        return INSTANCE;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                properties.getProperty("datasource.url"),
                properties.getProperty("datasource.username"),
                properties.getProperty("datasource.password")
        );
    }
}
