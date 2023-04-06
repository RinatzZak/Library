package com.library.dto;

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
}
