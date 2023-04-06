package com.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.service.AuthorService;
import com.library.service.BookService;
import com.library.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AbstractControllerTest {

    protected AuthorService authorService;
    protected BookService bookService;
    protected CategoryService categoryService;
    protected AuthorServlet authorServlet;
    protected BookServlet bookServlet;
    protected CategoryServlet categoryServlet;
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    protected ObjectMapper mapper;
    protected StringWriter writer;

    @BeforeEach
    public void setUp() throws IOException {
        authorService = mock(AuthorService.class);
        bookService = mock(BookService.class);
        categoryService = mock(CategoryService.class);
        authorServlet = new AuthorServlet(authorService);
        bookServlet = new BookServlet(bookService);
        categoryServlet = new CategoryServlet(categoryService);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        writer = new StringWriter();
        mapper = new ObjectMapper();

        when(response.getWriter()).thenReturn(new PrintWriter(writer));
    }
}
