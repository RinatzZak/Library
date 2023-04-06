package com.library.dao;

import com.library.model.Author;

import java.sql.SQLException;
import java.util.List;

public interface AuthorDAO {
    Author getById(int authorId);
    List<Author> getAll() throws SQLException;
    void add(Author author);
    void update(Author author);
    void delete (int authorId);
}
