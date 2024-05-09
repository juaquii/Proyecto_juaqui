package com.github.juaquii.Model.entity.Enum;

public enum Calidad {
    bronze("bronze"),
    plata("plata"),
    oro("oro");

    private String Rarety;
    Calidad(String rarety){
        Rarety = rarety;
    }
    public String getRarety(){
        return Rarety;
    }
}
