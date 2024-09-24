package com.asesinosdesoftware.javeparking.entities;

public class Puesto {

    private int id; //Este dato lo crea automáticamte el manejador de bases de datos y por tanto no se debe asignar manualmente
    private char tamano; //El tamaño del puesto, puede ser grande mediano o pequeño (g, m, p)
    private boolean disponibilidad; //Detemina si el puesto está disponible u ocupado (false para disponible, true para ocupado)

    public Puesto(int id, char tamano, boolean disponibilidad) {
        this.id = id;
        this.tamano = tamano;
        this.disponibilidad = disponibilidad;
    }

    public Puesto() {
    }

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
}
