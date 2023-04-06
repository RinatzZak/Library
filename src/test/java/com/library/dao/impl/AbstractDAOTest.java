package com.library.dao.impl;

import com.library.dao.AuthorDAO;
import com.library.dao.BookDAO;
import com.library.dao.CategoryDAO;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class AbstractDAOTest {
    protected BookDAO bookDAO;
    protected AuthorDAO authorDAO;
    protected CategoryDAO categoryDAO;
    private Properties properties;
    private final String filename = "h2db.properties";

    @BeforeEach
    void setUp() throws IOException, SQLException {
        setStartProperties();
        prepareDB();
        bookDAO = new BookDAOImpl(filename);
        authorDAO = new AuthorDAOImpl(filename);
        categoryDAO = new CategoryDAOImpl(filename);
    }

    private void setStartProperties() throws IOException {
        properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename);
        properties.load(inputStream);
        inputStream.close();
    }

    private void prepareDB() throws SQLException {
        String prepare = "jdbc:h2:./db/library;INIT=RUNSCRIPT FROM 'classpath:dbH2init.sql';DB_CLOSE_DELAY=-1";
        Connection connection = DriverManager.getConnection(
                prepare,
                properties.getProperty("datasource.username"),
                properties.getProperty("datasource.password")
        );
        connection.close();
    }
}
