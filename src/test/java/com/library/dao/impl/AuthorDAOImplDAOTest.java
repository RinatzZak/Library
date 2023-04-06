package com.library.dao.impl;

import com.library.model.Author;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

class AuthorDAOImplDAOTest extends AbstractDAOTest {

    @Test
    void getById() {
        Author expected = new Author(1, "Александр Сергеевич Пушкин");
        Author current = authorDAO.getById(1);
        Assertions.assertEquals(expected.getName(), current.getName());
    }

    @Test
    void getAll() throws SQLException {
        List<Author> authors = authorDAO.getAll();
        Assertions.assertEquals(6, authors.size());
    }

    @Test
    void add() throws SQLException {
        Author created = new Author();
        created.setName("test");
        authorDAO.add(created);
        Assertions.assertEquals(7, authorDAO.getAll().size());
        Assertions.assertEquals("test", authorDAO.getById(7).getName());
    }

    @Test
    void update() {
        Author author = authorDAO.getById(1);
        author.setName("Wow");
        authorDAO.update(author);
        Assertions.assertEquals("Wow", authorDAO.getById(1).getName());
    }

    @Test
    void delete() throws SQLException {
        authorDAO.delete(1);
        Assertions.assertEquals(5, authorDAO.getAll().size());
    }
}