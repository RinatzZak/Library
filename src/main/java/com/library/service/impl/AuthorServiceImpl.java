package com.library.service.impl;

import com.library.dao.AuthorDAO;
import com.library.dao.impl.AuthorDAOImpl;
import com.library.model.Author;
import com.library.service.AuthorService;

import java.sql.SQLException;
import java.util.List;

public class AuthorServiceImpl implements AuthorService {

    private AuthorDAO authorDAO;

    public AuthorServiceImpl() {
        this.authorDAO = new AuthorDAOImpl();
    }

    public AuthorServiceImpl(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
    }

    @Override
    public Author get(int authorId) {
        return authorDAO.getById(authorId);
    }

    @Override
    public List<Author> getAll() {
        try {
            return authorDAO.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(Author author) {
        authorDAO.add(author);
    }

    @Override
    public void update(Author author) {
        authorDAO.update(author);
    }

    @Override
    public void delete(int authorId) {
        authorDAO.delete(authorId);
    }
}
