package com.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.dao.BookDAO;
import com.library.dao.impl.BookDAOImpl;
import com.library.model.Author;
import com.library.model.Book;
import com.library.dto.BookTo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/books/add", "/books/update", "/books/get",
        "/books/delete", "/books/all"})
public class BookServlet extends HttpServlet {

    private BookDAO bookDAO;
    private ObjectMapper mapper;

    public BookServlet() {
        this.bookDAO = new BookDAOImpl();
        this.mapper = new ObjectMapper();
    }

    public BookServlet(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
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
            case "/books/get":
                getBook(request, response);
                break;
            case "/books/delete":
                deleteBook(request, response);
                break;
            case "books/all":
            default:
                getAllBooks(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        switch (action) {
            case "/books/add":
                addBook(request, response);
                break;
            case "/books/update":
                updateBook(request, response);
                break;
        }
    }

    private void getBook(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            int bookId = Integer.parseInt(request.getParameter("id"));
            String book = mapper.writeValueAsString(bookDAO.getById(bookId));
            PrintWriter pw = response.getWriter();
            pw.print(book);
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getAllBooks(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            List<Book> books = bookDAO.getAll();
            String booksOfString = mapper.writeValueAsString(books);
            PrintWriter pw = response.getWriter();
            pw.println(booksOfString);
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            int bookId = Integer.parseInt(request.getParameter("id"));
            bookDAO.delete(bookId);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void addBook(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            BookTo bookTo = mapper.readValue(body, BookTo.class);
            Book book = new Book();
            book.setName(bookTo.getName());
            bookDAO.add(book);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void updateBook(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            BookTo bookTo = mapper.readValue(body, BookTo.class);
            Book book = new Book();
            book.setId(bookTo.getId());
            book.setName(bookTo.getName());
            book.setAuthor(new Author(bookTo.getAuthor_id(), null));
            bookDAO.update(book);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
