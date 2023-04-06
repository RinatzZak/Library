package com.library.service;

import com.library.dto.CategoryTo;
import com.library.model.Book;
import com.library.model.Category;

import java.util.List;

public interface CategoryService {
    void update(Category category);
    void add(Category category);
    void delete(int categoryId);
    Category get(int categoryId);
    List<Category> getAll();
    void addBookToCategory(CategoryTo categoryTo);
    void deleteBookFromCategory(CategoryTo categoryTo);
}
