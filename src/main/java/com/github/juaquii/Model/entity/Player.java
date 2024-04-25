package com.github.juaquii.Model.entity;

import java.util.Objects;

public class Player {
    private String isbn;
    private String title;
    private Manager author;

    public Player(String isbn, String title, Manager author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public Player() {
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Manager getAuthor() {
        return author;
    }

    public void setAuthor(Manager author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player book = (Player) o;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author.getDni() +","+author.getName()+
                '}';
    }
}
