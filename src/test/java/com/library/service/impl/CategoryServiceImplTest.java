package com.library.service.impl;

import com.library.dao.BookDAO;
import com.library.dao.CategoryDAO;
import com.library.dto.CategoryTo;
import com.library.model.Book;
import com.library.model.Category;
import com.library.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    private CategoryDAO categoryDAO;
    private BookDAO bookDAO;
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryDAO = mock(CategoryDAO.class);
        bookDAO = mock(BookDAO.class);
        categoryService = new CategoryServiceImpl(categoryDAO, bookDAO);
    }

    @Test
    void update() {
        Category category = new Category(1, "Current");
        category.setName("Updated");
        categoryService.update(category);
        verify(categoryDAO).update(category);
    }

    @Test
    void add() {
        Category category = new Category();
        category.setName("Big4");
        categoryService.add(category);
        verify(categoryDAO).add(category);
    }

    @Test
    void delete() {
        Category category = new Category(1, "ForDelete");
        categoryService.delete(category.getId());
        verify(categoryDAO).delete(category.getId());
    }

    @Test
    void get() {
        Category category = new Category(1, "Current");
        when(categoryDAO.getById(1)).thenReturn(category);
        Category expected = categoryService.get(1);
        verify(categoryDAO).getById(1);
        assertEquals(category.getName(), expected.getName());
    }

    @Test
    void getAll() {
        List<Category> categories = new ArrayList<>();
        Category first = new Category(1, "First");
        Category second = new Category(2, "Second");
        categories.add(first);
        categories.add(second);
        when(categoryDAO.getAll()).thenReturn(categories);

        List<Category> expected = categoryService.getAll();

        verify(categoryDAO).getAll();
        assertEquals(categories.size(), expected.size());
    }

    @Test
    void addBookToCategory() {
        CategoryTo categoryTo = new CategoryTo(1, 3);
        Category category = new Category(1, "New");
        Book book = new Book(3, "Wow");
        when(categoryDAO.getById(1)).thenReturn(category);
        when(bookDAO.getById(3)).thenReturn(book);

       categoryService.addBookToCategory(categoryTo);

        verify(categoryDAO).addBookToCategory(category, book);
    }

    @Test
    void deleteBookFromCategory() {
        CategoryTo categoryTo = new CategoryTo(1, 3);
        Category category = new Category(1, "New");
        Book book = new Book(3, "Wow");
        when(categoryDAO.getById(1)).thenReturn(category);
        when(bookDAO.getById(3)).thenReturn(book);

        categoryService.deleteBookFromCategory(categoryTo);

        verify(categoryDAO).deleteBookFromCategory(category, book);
    }
}