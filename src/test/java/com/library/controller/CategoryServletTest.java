package com.library.controller;

import com.library.model.Book;
import com.library.model.Category;
import com.library.to.CategoryTo;
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

class CategoryServletTest extends AbstractControllerTest {

    @Test
    public void testGetAll() throws ServletException, IOException {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "First"));
        categories.add(new Category(2, "Second"));
        when(categoryDAO.getAll()).thenReturn(categories);

        when(request.getServletPath()).thenReturn("/categories/all");

        categoryServlet.doGet(request, response);

        Assertions.assertEquals("[{\"id\":1,\"name\":\"First\"},{\"id\":2,\"name\":\"Second\"}]\r\n", writer.toString());
    }

    @Test
    public void testAdd() throws ServletException, IOException {
        String categoryName = "Fantastic";
        Category created = new Category();
        created.setName(categoryName);
        String body = mapper.writeValueAsString(created);
        StringReader reader = new StringReader(body);

        when(request.getReader()).thenReturn(new BufferedReader(reader));
        when(request.getServletPath()).thenReturn("/categories/add");

        categoryServlet.doPost(request, response);

        verify(categoryDAO).add(created);
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void testUpdate() throws ServletException, IOException {
        Category updated = new Category(1, "Fantastic");
        updated.setName("kek");
        String body = mapper.writeValueAsString(updated);
        StringReader reader = new StringReader(body);

        when(request.getReader()).thenReturn(new BufferedReader(reader));
        when(request.getServletPath()).thenReturn("/categories/update");


        categoryServlet.doPost(request, response);

        verify(categoryDAO).update(updated);

        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    public void testDelete() throws ServletException, IOException {
        when(request.getServletPath()).thenReturn("/categories/delete");

        when(request.getParameter("id")).thenReturn("1");

        categoryServlet.doGet(request, response);

        verify(categoryDAO).delete(1);

        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Test
    public void testGet() throws ServletException, IOException {
        Category category = new Category(1, "Fantastic");
        when(categoryDAO.getById(1)).thenReturn(category);
        when(request.getServletPath()).thenReturn("/categories/get");
        when(request.getParameter("id")).thenReturn("1");

        categoryServlet.doGet(request, response);

        verify(categoryDAO).getById(1);

        Assertions.assertEquals("{\"id\":1,\"name\":\"Fantastic\"}", writer.toString());
    }

    @Test
    public void testDoGetGetAll() throws ServletException, IOException {
        when(request.getServletPath()).thenReturn("/categories/all");

        categoryServlet.doGet(request, response);

        verify(request, times(1)).getServletPath();
        verify(response, times(1)).setContentType("application/json");
        verify(response, times(1)).getWriter();
    }

    @Test
    public void testDeleteBookFromCategory() throws ServletException, IOException  {
        CategoryTo created = new CategoryTo();
        created.setId(1);
        created.setBookId(1);
        String body = mapper.writeValueAsString(created);
        StringReader reader = new StringReader(body);
        Book book = new Book(1, "New");
        Category category = new Category(1, "fantastic");

        when(request.getReader()).thenReturn(new BufferedReader(reader));
        when(bookDAO.getById(1)).thenReturn(book);
        when(categoryDAO.getById(1)).thenReturn(category);
        when(request.getReader()).thenReturn(new BufferedReader(reader));
        when(request.getServletPath()).thenReturn("/categories/book/delete");

        categoryServlet.doPost(request, response);

        verify(categoryDAO).deleteBookFromCategory(category, book);
    }

    @Test
    public void testAddBookToCategory() throws ServletException, IOException  {
        CategoryTo created = new CategoryTo();
        created.setId(1);
        created.setBookId(1);
        String body = mapper.writeValueAsString(created);
        StringReader reader = new StringReader(body);
        Book book = new Book(1, "New");
        Category category = new Category(1, "fantastic");

        when(request.getReader()).thenReturn(new BufferedReader(reader));
        when(bookDAO.getById(1)).thenReturn(book);
        when(categoryDAO.getById(1)).thenReturn(category);
        when(request.getReader()).thenReturn(new BufferedReader(reader));
        when(request.getServletPath()).thenReturn("/categories/book/add");

        categoryServlet.doPost(request, response);

        verify(categoryDAO).addBookToCategory(category, book);
    }
}