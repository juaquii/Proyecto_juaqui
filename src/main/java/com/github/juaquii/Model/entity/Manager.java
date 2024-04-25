package com.github.juaquii.Model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Manager {
    private String dni;
    private String name;
    private List<Player> books;

    public Manager(String dni, String name, List<Player> books) {
        this.dni = dni;
        this.name = name;
        this.books = books;
    }

    public Manager() {
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Player> getBooks() {
        return books;
    }

    public void setBooks(List<Player> books) {
        this.books = books;
    }
    public void addBook(Player book){
        if(books==null){
            books = new ArrayList<>();
        }
        if(!books.contains(book)){
            books.add(book);
        }
    }
    public void removeBook(Player book){
        if(books!=null){
            books.remove(book);
        }
    }
    public Player getBook(Player book){
        Player result=null;
        if(books!=null){
            int i=books.indexOf(book);
            result = books.get(i);
        }
        return result;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manager author = (Manager) o;
        return Objects.equals(dni, author.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    @Override
    public String toString() {
        return "Author{" +
                "dni='" + dni + '\'' +
                ", name='" + name + '\'' +
                ", books=" + books +
                '}';
    }
}
