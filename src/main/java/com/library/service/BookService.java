package com.library.service;

import com.library.dto.BookTo;
import com.library.model.Author;
import com.library.model.Book;

import java.util.List;

public interface BookService {
    Book get(int bookId);
    List<Book> getAll();
    void add(BookTo bookTo);
    void update(BookTo bookTo);
    void delete(int bookId);
}
