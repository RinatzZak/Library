package com.library.controller;

import com.library.model.Author;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class AuthorServletTest extends AbstractControllerTest {

    @Test
    public void testGetAll() throws ServletException, IOException, SQLException {
        List<Author> authorList = new ArrayList<>();
        authorList.add(new Author(1, "Tom"));
        authorList.add(new Author(2, "Jerry"));
        when(authorDAO.getAll()).thenReturn(authorList);

        when(request.getServletPath()).thenReturn("/authors/all");

        authorServlet.doGet(request, response);

        Assertions.assertEquals("[{\"id\":1,\"name\":\"Tom\"},{\"id\":2,\"name\":\"Jerry\"}]\r\n", writer.toString());
    }

    @Test
    public void testAdd() throws ServletException, IOException {
        String authorName = "Tolkien";
        Author created = new Author();
        created.setName(authorName);
        String body = mapper.writeValueAsString(created);
        StringReader reader = new StringReader(body);

        when(request.getReader()).thenReturn(new BufferedReader(reader));
        when(request.getServletPath()).thenReturn("/authors/add");

        authorServlet.doPost(request, response);

        verify(authorDAO).add(created);
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void testUpdate() throws ServletException, IOException {
        Author updated = new Author(1, "Tolkien");
        updated.setName("kek");
        String body = mapper.writeValueAsString(updated);
        StringReader reader = new StringReader(body);

        when(request.getReader()).thenReturn(new BufferedReader(reader));
        when(request.getServletPath()).thenReturn("/authors/update");


        authorServlet.doPost(request, response);

        verify(authorDAO).update(updated);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void testDelete() throws ServletException, IOException {
        when(request.getServletPath()).thenReturn("/authors/delete");

        when(request.getParameter("id")).thenReturn("1");

        authorServlet.doGet(request, response);

        verify(authorDAO).delete(1);

        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Test
    public void testGet() throws ServletException, IOException {
        Author author = new Author(1, "Tolkien");
        when(authorDAO.getById(1)).thenReturn(author);
        when(request.getServletPath()).thenReturn("/authors/get");
        when(request.getParameter("id")).thenReturn("1");

        authorServlet.doGet(request, response);

        verify(authorDAO).getById(1);

        Assertions.assertEquals("{\"id\":1,\"name\":\"Tolkien\"}", writer.toString());
    }


    @Test
    public void testDoGetGetAll() throws ServletException, IOException {
        when(request.getServletPath()).thenReturn("/authors/all");

        authorServlet.doGet(request, response);

        verify(request, times(1)).getServletPath();
        verify(response, times(1)).setContentType("application/json");
        verify(response, times(1)).getWriter();
    }
}