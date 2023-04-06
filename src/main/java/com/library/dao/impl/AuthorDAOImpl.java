package com.library.dao.impl;

import com.library.dao.AuthorDAO;
import com.library.model.Author;
import com.library.util.DB.DBConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAOImpl implements AuthorDAO {

    DBConnector connector;

    public AuthorDAOImpl() {
        connector = DBConnector.getInstance();
    }

    public AuthorDAOImpl(String filename) {
        connector = DBConnector.getInstance(filename);
    }


    @Override
    public Author getById(int authorId) {
        Author author = null;

        String query = "SELECT name FROM authors WHERE author_id = ?";

        try (Connection connection = connector.getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, authorId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                author = new Author(authorId, name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return author;
    }

    @Override
    public List<Author> getAll() {
        List<Author> authors = new ArrayList<>();

        try (Connection connection = connector.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT author_id, name FROM authors")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("author_id");
                String name = rs.getString("name");
                authors.add(new Author(id, name));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return authors;
    }

    @Override
    public void add(Author author) {

        try (Connection connection = connector.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO authors (name) VALUES (?)")) {
            ps.setString(1, author.getName());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating author failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Author author) {

        try (Connection connection = connector.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "UPDATE authors SET name = ? WHERE author_id = ?")) {
            ps.setString(1, author.getName());
            ps.setInt(2, author.getId());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating author failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int authorId) {

        try (Connection connection = connector.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "DELETE FROM authors WHERE author_id = ?")) {
            ps.setInt(1, authorId);

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting author failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
