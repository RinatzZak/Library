package com.library.dao.impl;

import com.library.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class BookDAOImplDAOTest extends AbstractDAOTest {

    @Test
    void getById() {
        Book expected = new Book(1, "Евгений Онегин");
        Book current = bookDAO.getById(1);
        Assertions.assertEquals(expected.getName(), current.getName());
    }

    @Test
    void getAll() {
        List<Book> books = bookDAO.getAll();
        Assertions.assertEquals(17, books.size());
    }

    @Test
    void add() {
        Book created = new Book();
        created.setName("New");
        bookDAO.add(created);
        Assertions.assertEquals(created.getName(), bookDAO.getById(18).getName());
    }

    @Test
    void update() {
        Book updated = bookDAO.getById(1);
        updated.setName("Updated");
        bookDAO.update(updated);
        Assertions.assertEquals("Updated", bookDAO.getById(1).getName());
    }

    @Test
    void delete() {
        Assertions.assertEquals(17, bookDAO.getAll().size());
        bookDAO.delete(1);
        Assertions.assertEquals(16, bookDAO.getAll().size());
    }
}