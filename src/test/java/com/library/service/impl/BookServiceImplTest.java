package com.library.service.impl;

import com.library.dao.AuthorDAO;
import com.library.dao.BookDAO;
import com.library.dto.BookTo;
import com.library.model.Author;
import com.library.model.Book;
import com.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {
    private BookDAO bookDAO;
    private AuthorDAO authorDAO;
    private BookService bookService;

    @BeforeEach
    void setUp() {
        authorDAO = mock(AuthorDAO.class);
        bookDAO = mock(BookDAO.class);
        bookService = new BookServiceImpl(bookDAO);
    }

    @Test
    void get() {
        Book book = new Book(1, "New");
        when(bookDAO.getById(1)).thenReturn(book);

        Book expected = bookService.get(1);

        verify(bookDAO).getById(1);

        assertEquals(book.getName(), expected.getName());
    }

    @Test
    void getAll() {
        Book first = new Book(1, "First");
        Book second = new Book(2, "Second");
        List<Book> books = new ArrayList<>();
        books.add(first);
        books.add(second);
        when(bookDAO.getAll()).thenReturn(books);

        List<Book> expected = bookService.getAll();

        verify(bookDAO).getAll();

        assertEquals(books.size(), expected.size());
    }

    @Test
    void add() {
        BookTo created2 = new BookTo();
        created2.setName("Book");
        Book book = new Book();
        book.setId(created2.getId());
        book.setName(created2.getName());
        bookService.add(created2);
        verify(bookDAO).add(book);
    }

    @Test
    void update() {
        BookTo created2 = new BookTo(1, "New", 3);
        created2.setName("Update");
        Book book = new Book();
        book.setId(created2.getId());
        book.setName(created2.getName());
        book.setAuthor(null);
        bookService.update(created2);
        verify(bookDAO).update(book);
    }

    @Test
    void delete() {
        Book book = new Book(1, "Book");
        bookService.delete(book.getId());
        verify(bookDAO).delete(book.getId());
    }
}