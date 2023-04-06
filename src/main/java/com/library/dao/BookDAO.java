package com.library.dao;

import com.library.model.Book;

import java.util.List;

public interface BookDAO {
    Book getById(int bookId);
    List<Book> getAll();
    void add(Book book);
    void update(Book book);
    void delete(int bookId);
}
