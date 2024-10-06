package com.asesinosdesoftware.javeparking.entities;

public class Empleado {

    private int id;
    private String cedula;
    private String nombre;
    private String apellido;
    private String hash;//Hash de la contraseña del empleado

    /**
     * Método constructor por parametros
     * @param cedula
     * @param nombre
     * @param apellido
     * @param hash
     */
    public Empleado(String cedula, String nombre, String apellido, String hash) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.hash = hash;
    }

    /**
     * Método constructor vacío
     */
    public Empleado() {
    }

    /*Getters y Setters*/
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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
