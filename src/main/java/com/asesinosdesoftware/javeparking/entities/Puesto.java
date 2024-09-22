package com.asesinosdesoftware.javeparking.entities;

public class Puesto {

    private int id;
    private char tamano;//El tamaño del puesto, puede ser grande mediano o pequeño (g, m, p)
    private boolean disponibilidad;

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
