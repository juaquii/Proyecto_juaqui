package com.github.juaquii.Model.entity;

import com.github.juaquii.Model.entity.Enum.Calidad;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.jar.Attributes;

public class Manager {
    private int ID;
    private String Pais;
    private String Nombre;
    private Calidad calidad;
    private List<Player> Players;

    public Manager() {
    }

    public Manager(int ID, String pais, String nombre, Calidad calidad, List<Player> players) {
        this.ID = ID;
        Pais = pais;
        Nombre = nombre;
        this.calidad = calidad;
        Players = players;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPais() {
        return Pais;
    }

    public void setPais(String pais) {
        Pais = pais;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public Calidad getCalidad() {
        return calidad;
    }

    public void setCalidad(Calidad calidad) {
        this.calidad = calidad;
    }

    public List<Player> getPlayers() {
        return Players;
    }

    public void setPlayers(List<Player> players) {
        Players = players;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "ID=" + ID +
                ", Pais='" + Pais + '\'' +
                ", Nombre='" + Nombre + '\'' +
                ", calidad=" + calidad +
                ", Players=" + Players +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manager manager = (Manager) o;
        return ID == manager.ID && Objects.equals(Pais, manager.Pais) && Objects.equals(Nombre, manager.Nombre) && calidad == manager.calidad && Objects.equals(Players, manager.Players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, Pais, Nombre, calidad, Players);
    }
}