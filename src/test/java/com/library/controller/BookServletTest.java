package com.library.controller;

import com.library.model.Book;
import com.library.to.BookTo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class BookServletTest extends AbstractControllerTest {

    @Test
    public void testGetAll() throws ServletException, IOException {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1, "New"));
        books.add(new Book(2, "Up"));

        when(bookDAO.getAll()).thenReturn(books);

        when(request.getServletPath()).thenReturn("/books/all");

        bookServlet.doGet(request, response);

        Assertions.assertEquals("[{\"id\":1,\"name\":\"New\"},{\"id\":2,\"name\":\"Up\"}]\r\n", writer.toString());
    }

    @Test
    public void testAdd() throws ServletException, IOException {
        BookTo created = new BookTo();
        created.setName("New");
        String body = mapper.writeValueAsString(created);
        StringReader reader = new StringReader(body);
        Book book = new Book();
        book.setName(created.getName());

        when(request.getReader()).thenReturn(new BufferedReader(reader));
        when(request.getServletPath()).thenReturn("/books/add");

        bookServlet.doPost(request, response);

        verify(bookDAO).add(book);
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void testUpdate() throws ServletException, IOException {
        BookTo created = new BookTo(1, "New", 3);
        created.setName("New2");
        String body = mapper.writeValueAsString(created);
        StringReader reader = new StringReader(body);
        Book book = new Book();
        book.setId(created.getId());
        book.setName(created.getName());
        book.setAuthor(authorDAO.getById(3));

        when(request.getReader()).thenReturn(new BufferedReader(reader));
        when(request.getServletPath()).thenReturn("/books/update");

        bookServlet.doPost(request, response);

        verify(bookDAO).update(book);
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void testDelete() throws ServletException, IOException {
        when(request.getServletPath()).thenReturn("/books/delete");

        when(request.getParameter("id")).thenReturn("1");

        bookServlet.doGet(request, response);

        verify(bookDAO).delete(1);

        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Test
    public void testGet() throws ServletException, IOException {
        Book book = new Book(1, "New");
        when(bookDAO.getById(1)).thenReturn(book);
        when(request.getServletPath()).thenReturn("/books/get");
        when(request.getParameter("id")).thenReturn("1");

        bookServlet.doGet(request, response);

        verify(bookDAO).getById(1);

        Assertions.assertEquals("{\"id\":1,\"name\":\"New\"}", writer.toString());
    }

    @Test
    public void testDoGetGetAll() throws ServletException, IOException {
        when(request.getServletPath()).thenReturn("/books/all");

        bookServlet.doGet(request, response);

        verify(request, times(1)).getServletPath();
        verify(response, times(1)).setContentType("application/json");
        verify(response, times(1)).getWriter();
    }
}