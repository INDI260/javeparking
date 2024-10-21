package com.asesinosdesoftware.javeparking.entities;

import java.time.LocalDate;

public class Suscripcion {
    private int id;
    private Cliente cliente;
    private Vehiculo vehiculo; // Agregado: Atributo para el vehículo
    private LocalDate fechaInicio; // Fecha de inicio
    private LocalDate fechaFin; // Fecha de finalización
    private String estado; // Activa o inactiva

    /**
     * Método constructor por parámetros
     * @param id
     * @param cliente
     * @param vehiculo
     * @param fechaInicio
     * @param fechaFin
     */
    public Suscripcion(int id, Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio, LocalDate fechaFin) {
        this.id = id;
        this.cliente = cliente;
        this.vehiculo = vehiculo; // Inicializa el vehículo
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    /**
     * Método constructor vacío
     */
    // Constructor vacío
    public Suscripcion() {}

    // Getters y Setters
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

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
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