package com.library.service;

import com.library.model.Author;

import java.util.List;

public interface AuthorService {
    Author get(int authorId);
    List<Author> getAll();
    void add(Author author);
    void update(Author author);
    void delete(int authorId);
}
