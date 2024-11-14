package com.asesinosdesoftware.javeparking.entities;

public class Vehiculo {

    private int id; // Este dato lo crea automáticamente el manejador de bases de datos
    private String placa; // Placa del vehículo
    private char tamano; // El tamaño del vehículo. Puede ser grande, mediano o pequeño (g, m, p)
    private char tipo; // Tipo de vehículo
    private int clienteid;
    private Parqueadero parqueadero; // Parqueadero asociado al vehículo

    /**
     * Método constructor por parámetros de vehiculo.
     * @param id
     * @param placa
     * @param tamano
     * @param tipo
     * @param clienteid
     */
    public Vehiculo(int id, String placa, char tamano, char tipo, int clienteid) {
        this.id = id;
        this.placa = placa;
        this.tamano = tamano;
        this.tipo = tipo;
        this.clienteid = clienteid;
    }

    /**
     * Constructor vacío de vehículo.
     */
    public Vehiculo() {

    }

    /* Getters y setters */
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

    public int getClienteid() {
        return clienteid;
    }

    public void setClienteid(int clienteid) {
        this.clienteid = clienteid;
    }

    public Parqueadero getParqueadero() {
        return parqueadero;
    }

    /**
     * Método para asignar un parqueadero al vehículo.
     * @param parqueadero: Objeto Parqueadero asociado al vehículo.
     */
    public void setParqueadero(Parqueadero parqueadero) {
        this.parqueadero = parqueadero;
    }
}
