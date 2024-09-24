package com.asesinosdesoftware.javeparking.entities;

public class Vehiculo {

    private int id; //Este dato lo crea automáticamte el manejador de bases de datos y por tanto no se debe asignar manualmente
    private String placa; //Placa del vehiculo
    private char tamano;//El tamaño del vehiculo. Puede ser grande mediano o pequeño (g, m, p)
    private char tipo; //Tipo de vehiculo

    public Vehiculo(int id, String placa, char tamano, char tipo) {
        this.id = id;
        this.placa = placa;
        this.tamano = tamano;
        this.tipo = tipo;
    }

    public Vehiculo(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public char getTamano() {
        return tamano;
    }

    public void setTamano(char tamano) {
        this.tamano = tamano;
    }

    public char getTipo() {
        return tipo;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }
}
