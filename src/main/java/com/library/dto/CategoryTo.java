package com.library.dto;

import java.util.Objects;

public class CategoryTo {
    private Integer id;
    private Integer bookId;

    public CategoryTo() {
    }

    public CategoryTo(Integer id, Integer bookId) {
        this.id = id;
        this.bookId = bookId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryTo that = (CategoryTo) o;
        return Objects.equals(id, that.id) && Objects.equals(bookId, that.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookId);
    }
}
