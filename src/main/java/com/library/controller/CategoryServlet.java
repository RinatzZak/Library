package com.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.dao.BookDAO;
import com.library.dao.CategoryDAO;
import com.library.dao.impl.BookDAOImpl;
import com.library.dao.impl.CategoryDAOImpl;
import com.library.model.Book;
import com.library.model.Category;
import com.library.dto.CategoryTo;
import com.library.service.BookService;
import com.library.service.CategoryService;
import com.library.service.impl.BookServiceImpl;
import com.library.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/categories/add", "/categories/update", "/categories/get",
        "/categories/delete", "/categories/all", "/categories/book/delete", "/categories/book/add"})
public class CategoryServlet extends HttpServlet {

    private CategoryService categoryService;
    private ObjectMapper mapper;

    public CategoryServlet() {
        this.categoryService = new CategoryServiceImpl();
        this.mapper = new ObjectMapper();
    }

    public CategoryServlet(CategoryService categoryService) {
        this.categoryService = categoryService;
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
            case "/categories/get":
                getCategory(request, response);
                break;
            case "/categories/delete":
                deleteCategories(request, response);
                break;
            case "categories/all":
            default:
                getAllCategories(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        switch (action) {
            case "/categories/add":
                addCategory(request, response);
                break;
            case "/categories/update":
                updateCategory(request, response);
                break;
            case "/categories/book/add":
                addBookToCategory(request, response);
                break;
            case "/categories/book/delete":
                deleteBookFromCategory(request, response);
                break;
        }
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Category category = mapper.readValue(body, Category.class);
            categoryService.update(category);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void addCategory(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            Category category = mapper.readValue(body, Category.class);
            categoryService.add(category);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void getAllCategories(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            List<Category> categories = categoryService.getAll();
            String categoriesOfString = mapper.writeValueAsString(categories);
            PrintWriter pw = response.getWriter();
            pw.println(categoriesOfString);
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteCategories(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            int categoryId = Integer.parseInt(request.getParameter("id"));
            categoryService.delete(categoryId);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void getCategory(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            int categoryId = Integer.parseInt(request.getParameter("id"));
            String category = mapper.writeValueAsString(categoryService.get(categoryId));
            PrintWriter pw = response.getWriter();
            pw.print(category);
            pw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteBookFromCategory(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            CategoryTo categoryTo = mapper.readValue(body, CategoryTo.class);
            categoryService.deleteBookFromCategory(categoryTo);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void addBookToCategory(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            CategoryTo categoryTo = mapper.readValue(body, CategoryTo.class);
            categoryService.addBookToCategory(categoryTo);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
