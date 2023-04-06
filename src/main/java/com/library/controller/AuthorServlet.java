package com.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.dao.AuthorDAO;
import com.library.dao.impl.AuthorDAOImpl;
import com.library.model.Author;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/authors/add", "/authors/update", "/authors/get",
        "/authors/delete", "/authors/all"})
public class AuthorServlet extends HttpServlet {

    private AuthorDAO authorDAO;
    private ObjectMapper mapper;

    public AuthorServlet() {
        this.authorDAO = new AuthorDAOImpl();
        this.mapper = new ObjectMapper();
    }

    public AuthorServlet(AuthorDAO authorDAO) {
        this.authorDAO = authorDAO;
        this.mapper = new ObjectMapper();
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        switch (action == null ? "all" : action) {
            case "/authors/get":
                getAuthor(request, response);
                break;
            case "/authors/delete":
                deleteAuthor(request, response);
                break;
            case "authors/all":
            default:
                getAllAuthors(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        switch (action) {
            case "/authors/add":
                addAuthor(request, response);
                break;
            case "/authors/update":
                updateAuthor(request, response);
                break;
        }
    }

    private void getAuthor(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            int authorId = Integer.parseInt(request.getParameter("id"));
            String authorOfString = mapper.writeValueAsString(authorDAO.getById(authorId));
            PrintWriter pw = response.getWriter();
            pw.print(authorOfString);
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getAllAuthors(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            List<Author> authors = authorDAO.getAll();
            String authorsOfString = mapper.writeValueAsString(authors);
            PrintWriter pw = response.getWriter();
            pw.println(authorsOfString);
            pw.close();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteAuthor(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            int authorId = Integer.parseInt(request.getParameter("id"));
            authorDAO.delete(authorId);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void addAuthor(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Author author = mapper.readValue(body, Author.class);
            authorDAO.add(author);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void updateAuthor(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Author author = mapper.readValue(body, Author.class);
            authorDAO.update(author);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
