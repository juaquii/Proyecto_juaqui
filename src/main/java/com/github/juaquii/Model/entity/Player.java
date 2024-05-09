package com.github.juaquii.Model.entity;

import java.util.Objects;

public class Player extends Manager {
    private String Idj;
    private String Media;
    private String Posicion;
    private String HabilidadEspecial;

    public Player() {
    }

    public Player(String idj, String media, String posicion, String habilidadEspecial) {
        Idj = idj;
        Media = media;
        Posicion = posicion;
        HabilidadEspecial = habilidadEspecial;
    }

    public String getIdj() {
        return Idj;
    }

    public void setIdj(String idj) {
        Idj = idj;
    }

    public String getMedia() {
        return Media;
    }

    public void setMedia(String media) {
        Media = media;
    }

    public String getPosicion() {
        return Posicion;
    }

    public void setPosicion(String posicion) {
        Posicion = posicion;
    }

    public String getHabilidadEspecial() {
        return HabilidadEspecial;
    }

    public void setHabilidadEspecial(String habilidadEspecial) {
        HabilidadEspecial = habilidadEspecial;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Idj);
    }

    @Override
    public String toString() {
        return "Player{" +
                "Idj='" + Idj + '\'' +
                ", Media='" + Media + '\'' +
                ", Posicion='" + Posicion + '\'' +
                ", HabilidadEspecial='" + HabilidadEspecial + '\'' +
                '}';
    }
}
