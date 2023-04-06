package com.library.dao.impl;

import com.library.dao.CategoryDAO;
import com.library.model.Book;
import com.library.model.Category;
import com.library.util.DB.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {
    DBConnector connector;

    public CategoryDAOImpl() {
        connector = DBConnector.getInstance();
    }

    public CategoryDAOImpl(String filename) {
        connector = DBConnector.getInstance(filename);
    }

    @Override
    public Category getById(int categoryId) {
        Category category = null;
        List<Book> books = new ArrayList<>();
        try (Connection connection = connector.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT category_name, b.book_id, b.book_name FROM categories " +
                             "LEFT JOIN book_category bc on categories.category_id = bc.category_id " +
                             "LEFT JOIN books b on bc.book_id = b.book_id WHERE categories.category_id=?")) {
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("category_name");
                String bookName = rs.getString("book_name");
                int bookId = rs.getInt("book_id");
                category = new Category(categoryId, name);
                books.add(new Book(bookId, bookName));
                category.setBooks(books);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return category;
    }

    @Override
    public List<Category> getAll() {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = connector.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT category_id, category_name FROM categories")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("category_id");
                String name = rs.getString("category_name");
                categories.add(new Category(id, name));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }

    @Override
    public void add(Category category) {

        try (Connection connection = connector.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO categories (category_name) VALUES (?)")) {
            ps.setString(1, category.getName());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating category failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Category category) {

        try (Connection connection = connector.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "UPDATE categories SET category_name = ? WHERE category_id = ?")) {
            ps.setString(1, category.getName());
            ps.setInt(2, category.getId());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating category failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int categoryId) {

        try (Connection connection = connector.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "DELETE FROM categories WHERE category_id = ?")) {
            ps.setInt(1, categoryId);

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting category failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addBookToCategory(Category category, Book book) {
        try (Connection connection = connector.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO book_category (book_id, category_id) VALUES (?, ?)")) {
            ps.setInt(1, book.getId());
            ps.setInt(2, category.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteBookFromCategory(Category category, Book book) {
        try (Connection connection = connector.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "DELETE FROM book_category WHERE book_id=? and category_id=?")) {
            ps.setInt(1, book.getId());
            ps.setInt(2, category.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
