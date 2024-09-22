package com.asesinosdesoftware.javeparking.entities;

public class Cliente {

    private int id;
    private String cedula;
    private String nombre;
    private String apellido;
    private char universidad;

    public Cliente(int id, String cedula, String nombre, String apellido, char universidad) {
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.universidad = universidad;
    }

    public Cliente() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public char getUniversidad() {
        return universidad;
    }

    public void setUniversidad(char universidad) {
        this.universidad = universidad;
    }
}
