package com.asesinosdesoftware.javeparking.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Suscripcion {
    private int id;
    private Cliente cliente;
    private Vehiculo vehiculo; // Atributo para el vehículo asociado
    private LocalDateTime fechaInicio; // Fecha de inicio
    private LocalDateTime fechaFin; // Fecha de finalización
    private String estado; // Activa o inactiva
    private Parqueadero parqueadero; // Se agrega el campo parqueadero

    // Métodos getter y setter
    public Parqueadero getParqueadero() {
        return parqueadero;
    }

    public void setParqueadero(Parqueadero parqueadero) {
        this.parqueadero = parqueadero;
    }

    /**
     * Método constructor por parámetros
     * @param id
     * @param cliente
     * @param vehiculo
     * @param fechaInicio
     * @param fechaFin
     */
    public Suscripcion(int id, Cliente cliente, Vehiculo vehiculo, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        this.id = id;
        this.cliente = cliente;
        this.vehiculo = vehiculo; // Inicializa el vehículo
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    /**
     * Método constructor vacío
     */
    public Suscripcion() {}

    /* Getters y Setters */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo; // Método para obtener el vehículo
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo; // Método para establecer el vehículo
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado; // Se puede establecer el estado desde el controlador
    }

    @Override
    public String toString() {
        return "Suscripcion{" +
                "id=" + id +
                ", cliente=" + cliente.getNombre() + " " + cliente.getApellido() +
                ", vehiculo=" + (vehiculo != null ? vehiculo.getPlaca() : "N/A") + // Muestra la placa del vehículo
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", estado='" + estado + '\'' +
                '}';
    }
}
