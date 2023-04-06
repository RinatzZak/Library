package com.library.dao.impl;

import com.library.dao.AuthorDAO;
import com.library.dao.BookDAO;
import com.library.model.Author;
import com.library.model.Book;
import com.library.util.DB.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAOImpl implements BookDAO {
    DBConnector connector;
    AuthorDAO authorDAO;

    public BookDAOImpl() {
        connector = DBConnector.getInstance();
        authorDAO = new AuthorDAOImpl();
    }

    public BookDAOImpl(String filename) {
        connector = DBConnector.getInstance(filename);
        authorDAO = new AuthorDAOImpl(filename);
    }


    @Override
    public Book getById(int bookId) {
        Book book = null;
        try (Connection connection = connector.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT book_name, a.author_id FROM books " +
                             "LEFT JOIN authors a on books.author_id = a.author_id " +
                             "WHERE book_id = ?")) {
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String bookName = rs.getString("book_name");
                int authorId = rs.getInt("author_id");
                Author author = authorDAO.getById(authorId);
                book = new Book(bookId, bookName);
                book.setAuthor(author);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return book;
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        try (Connection connection = connector.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT book_id, book_name FROM books")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                String bookName = rs.getString("book_name");
                books.add(new Book(bookId, bookName));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    @Override
    public void add(Book book) {
        try (Connection connection = connector.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO books (book_name) VALUES (?)")) {
            ps.setString(1, book.getName());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating book failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(Book book) {
        try (Connection connection = connector.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "UPDATE books SET book_name = ?, author_id = ? WHERE book_id = ?")) {
            ps.setString(1, book.getName());
            ps.setInt(2, book.getAuthor().getId());
            ps.setInt(3, book.getId());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating book failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(int bookId) {
        try (Connection connection = connector.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "DELETE FROM books WHERE book_id = ?")) {
            ps.setInt(1, bookId);

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting book failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
