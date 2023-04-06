package com.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.dao.AuthorDAO;
import com.library.dao.BookDAO;
import com.library.dao.CategoryDAO;
import org.junit.jupiter.api.BeforeEach;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AbstractControllerTest {

    protected AuthorDAO authorDAO;
    protected BookDAO bookDAO;
    protected CategoryDAO categoryDAO;
    protected AuthorServlet authorServlet;
    protected BookServlet bookServlet;
    protected CategoryServlet categoryServlet;
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    protected ObjectMapper mapper;
    protected StringWriter writer;

    @BeforeEach
    public void setUp() throws IOException {
        authorDAO = mock(AuthorDAO.class);
        bookDAO = mock(BookDAO.class);
        categoryDAO = mock(CategoryDAO.class);
        authorServlet = new AuthorServlet(authorDAO);
        bookServlet = new BookServlet(bookDAO);
        categoryServlet = new CategoryServlet(categoryDAO, bookDAO);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        writer = new StringWriter();
        mapper = new ObjectMapper();

        when(response.getWriter()).thenReturn(new PrintWriter(writer));
    }
}
