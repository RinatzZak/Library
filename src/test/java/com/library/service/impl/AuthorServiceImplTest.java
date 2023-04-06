package com.library.service.impl;

import com.library.dao.AuthorDAO;
import com.library.model.Author;
import com.library.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AuthorServiceImplTest {
    private AuthorService authorService;
    private AuthorDAO authorDAO;

    @BeforeEach
    void setUp() {
        authorDAO = mock(AuthorDAO.class);
        authorService = new AuthorServiceImpl(authorDAO);
    }

    @Test
    void get() {
        Author author = new Author(1, "New");
        when(authorDAO.getById(1)).thenReturn(author);

        Author expected = authorService.get(1);

        verify(authorDAO).getById(1);

        assertEquals(author.getName(), expected.getName());
    }

    @Test
    void getAll() throws SQLException {
        Author first = new Author(1, "First");
        Author second = new Author(2, "Second");
        List<Author> authors = new ArrayList<>();
        authors.add(first);
        authors.add(second);
        when(authorDAO.getAll()).thenReturn(authors);

        List<Author> expected = authorService.getAll();

        verify(authorDAO).getAll();

        assertEquals(authors.size(), expected.size());
    }

    @Test
    void add() {
        Author author = new Author();
        author.setName("New");
        authorService.add(author);
        verify(authorDAO).add(author);
    }

    @Test
    void update() {
        Author updated = new Author(1, "New");
        updated.setName("Updated");
        authorService.update(updated);
        verify(authorDAO).update(updated);
    }

    @Test
    void delete() {
        Author author = new Author(2, "DeleteThis");
        authorService.delete(author.getId());
        verify(authorDAO).delete(author.getId());
    }
}