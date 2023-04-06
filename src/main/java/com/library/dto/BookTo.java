package com.library.dto;

import java.util.Objects;

public class BookTo {
    private Integer id;
    private String name;
    private Integer author_id;

    public BookTo() {
    }

    public BookTo(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public BookTo(Integer id, String name, Integer author_id) {
        this.id = id;
        this.name = name;
        this.author_id = author_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookTo bookTo = (BookTo) o;
        return Objects.equals(id, bookTo.id) && Objects.equals(name, bookTo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
