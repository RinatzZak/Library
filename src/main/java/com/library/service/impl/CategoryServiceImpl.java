package com.library.service.impl;

import com.library.dao.BookDAO;
import com.library.dao.CategoryDAO;
import com.library.dao.impl.BookDAOImpl;
import com.library.dao.impl.CategoryDAOImpl;
import com.library.dto.CategoryTo;
import com.library.model.Book;
import com.library.model.Category;
import com.library.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDAO categoryDAO;
    private BookDAO bookDAO;

    public CategoryServiceImpl() {
        this.categoryDAO = new CategoryDAOImpl();
        this.bookDAO = new BookDAOImpl();
    }

    public CategoryServiceImpl(CategoryDAO categoryDAO, BookDAO bookDAO) {
        this.categoryDAO = categoryDAO;
        this.bookDAO = bookDAO;
    }

    @Override
    public void update(Category category) {
        categoryDAO.update(category);
    }

    @Override
    public void add(Category category) {
        categoryDAO.add(category);
    }

    @Override
    public void delete(int categoryId) {
        categoryDAO.delete(categoryId);
    }

    @Override
    public Category get(int categoryId) {
        return categoryDAO.getById(categoryId);
    }

    @Override
    public List<Category> getAll() {
        return categoryDAO.getAll();
    }

    @Override
    public void addBookToCategory(CategoryTo categoryTo) {
        Category category = categoryDAO.getById(categoryTo.getId());
        Book book = bookDAO.getById(categoryTo.getBookId());
        categoryDAO.addBookToCategory(category, book);
    }

    @Override
    public void deleteBookFromCategory(CategoryTo categoryTo) {
        Category category = categoryDAO.getById(categoryTo.getId());
        Book book = bookDAO.getById(categoryTo.getBookId());
        categoryDAO.deleteBookFromCategory(category, book);
    }
}
