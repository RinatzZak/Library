package com.library.dao.impl;

import com.library.model.Book;
import com.library.model.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDAOImplDAOTest extends AbstractDAOTest {

    @Test
    void getById() {
        Category expected = new Category(1, "Самые читаемые книги");
        Category current = categoryDAO.getById(1);
        Assertions.assertEquals(expected.getName(), current.getName());
    }

    @Test
    void getAll() {
        List<Category> categoryList = categoryDAO.getAll();
        Assertions.assertEquals(6, categoryList.size());
    }

    @Test
    void add() {
        Category created = new Category();
        created.setName("New");
        categoryDAO.add(created);
        Assertions.assertEquals(created.getName(), categoryDAO.getById(7).getName());
    }

    @Test
    void update() {
        Category updated = categoryDAO.getById(1);
        updated.setName("Updated");
        categoryDAO.update(updated);
        Assertions.assertEquals("Updated", categoryDAO.getById(1).getName());
    }

    @Test
    void delete() {
        Assertions.assertEquals(6, categoryDAO.getAll().size());
        categoryDAO.delete(1);
        Assertions.assertEquals(5, categoryDAO.getAll().size());
    }

    @Test
    void addBookToCategory() {
        Book book = bookDAO.getById(16);
        Category category = categoryDAO.getById(5);
        categoryDAO.addBookToCategory(category, book);
        assertTrue(categoryDAO.getById(1).getBooks().contains(book));
    }

    @Test
    void deleteBookFromCategory() {
        Book book = bookDAO.getById(16);
        Category category = categoryDAO.getById(1);
        categoryDAO.deleteBookFromCategory(category, book);
        assertFalse(categoryDAO.getById(1).getBooks().contains(book));
    }
}