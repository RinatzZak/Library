package com.library.dao;

import com.library.model.Book;
import com.library.model.Category;

import java.util.List;

public interface CategoryDAO {
    Category getById(int categoryId);
    List<Category> getAll();
    void add(Category category);
    void update(Category category);
    void delete(int categoryId);
    void addBookToCategory(Category category, Book book);
    void deleteBookFromCategory(Category category, Book book);
}
