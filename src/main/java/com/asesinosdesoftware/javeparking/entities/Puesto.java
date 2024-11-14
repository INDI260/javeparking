package com.asesinosdesoftware.javeparking.entities;

public class Puesto {

    private int id; //Este dato lo crea automáticamte el manejador de bases de datos y por tanto no se debe asignar manualmente
    private char tamano; //El tamaño del puesto, puede ser grande mediano o pequeño (g, m, p)
    private boolean disponibilidad; //Detemina si el puesto está disponible u ocupado (false para disponible, true para ocupado)
    private int parqueaderoID;

    /**
     * Método constructor por parámetros
     * @param id
     * @param tamano
     * @param disponibilidad
     */
    public Puesto(int id, char tamano, boolean disponibilidad) {
        this.id = id;
        this.tamano = tamano;
        this.disponibilidad = disponibilidad;
    }



    /**
     * Método constructor vacío
     */
    public Puesto() {
    }

    /*Getters y setters*/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getTamano() {
        return tamano;
    }

    public void setTamano(char tamano) {
        this.tamano = tamano;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public int getParqueaderoID() {
        return parqueaderoID;
    }

    public void setParqueaderoID(int parqueaderoID) {
        this.parqueaderoID = parqueaderoID;
    }
}
