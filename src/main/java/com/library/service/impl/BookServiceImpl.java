package com.library.service.impl;

import com.library.dao.BookDAO;
import com.library.dao.impl.BookDAOImpl;
import com.library.dto.BookTo;
import com.library.model.Author;
import com.library.model.Book;
import com.library.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {
    private BookDAO bookDAO;

    public BookServiceImpl() {
        this.bookDAO = new BookDAOImpl();
    }

    public BookServiceImpl(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public Book get(int bookId) {
        return bookDAO.getById(bookId);
    }

    @Override
    public List<Book> getAll() {
        return bookDAO.getAll();
    }

    @Override
    public void add(BookTo bookTo) {
        Book book = new Book();
        book.setName(bookTo.getName());
        bookDAO.add(book);
    }

    @Override
    public void update(BookTo bookTo) {
        Book book = new Book();
        book.setId(bookTo.getId());
        book.setName(bookTo.getName());
        book.setAuthor(new Author(bookTo.getAuthor_id(), null));
        bookDAO.update(book);
    }

    @Override
    public void delete(int bookId) {
        bookDAO.delete(bookId);
    }
}
