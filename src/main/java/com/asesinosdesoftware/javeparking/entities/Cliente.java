package com.asesinosdesoftware.javeparking.entities;

public class Cliente {

    private int id; //Este dato lo crea automaticamente el manejador de bases de datos y por tanto no debe asignarse manualmente
    private String cedula; //Cedula del estudiante
    private String nombre; //Nombre del estudiante
    private String apellido; //Apellido del estudiante
    private char universidad; //Determina si está afiliado a la universidad se usa 'e' para estudiante, 'a' para administrativo o n para ninguno
    private String hash; //Hash de la contraseña del cliente


    /**
     * Método contructor por parametros
     * @param cedula
     * @param nombre
     * @param apellido
     * @param universidad
     * @param hash
     */
    public Cliente(String cedula, String nombre, String apellido, Character universidad, String hash) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;

        this.universidad = universidad;
        this.hash = hash;

    }

    /**
     * Método constructor vacío
     */
    public Cliente() {
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

    public Character getUniversidad() {
        return universidad;
    }

    public void setUniversidad(Character universidad) {
        this.universidad = universidad;
    }

    public String getHash() {return hash;}

    public void setHash(String hash) {this.hash = hash;}
}
