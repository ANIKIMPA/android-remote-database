package com.niovan.webservicedatabase.models;

public class Persona {
    public String nombre;
    public String fechaNacimiento;
    public char genero;
    public int valoracion;
    public String urlPhoto;

    public Persona(String nombre, String fechaNacimiento, char genero, int valoracion, String photo) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.valoracion = valoracion;
        this.urlPhoto = photo;
    }
}
