package com.github.juaquii.Model.entity;

import java.util.List;
import java.util.Objects;

public class Team {
    private String Idequipo;
    private String Nombreequipo;
    private String Mediaequipo;
    private List<Manager> Managers;

    public Team() {

    }

    public String getIdequipo() {
        return Idequipo;
    }

    public void setIdequipo(String idequipo) {
        Idequipo = idequipo;
    }

    public String getNombreequipo() {
        return Nombreequipo;
    }

    public void setNombreequipo(String nombreequipo) {
        Nombreequipo = nombreequipo;
    }

    public String getMediaequipo() {
        return Mediaequipo;
    }

    public void setMediaequipo(String mediaequipo) {
        Mediaequipo = mediaequipo;
    }
    public List<Manager> getManager() {
        return Managers;
    }

    public void setManager(List<Manager> manager) {
        this.Managers = manager;
    }

    @Override
    public int hashCode() {
            return Objects.hash(Idequipo);
        }
    @Override
    public String toString() {
        return "Team{" +
                "Idequipo='" + Idequipo + '\'' +
                ", Nombreequipo='" + Nombreequipo + '\'' +
                ", Mediaequipo='" + Mediaequipo + '\'' +
                ", Managers=" + Managers +
                '}';
    }
}

