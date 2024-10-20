package com.asesinosdesoftware.javeparking.entities;

import java.time.LocalDate;

public class Suscripcion {
    private int id;
    private Cliente cliente;
    private LocalDate fechaInicio; // Fecha de inicio
    private LocalDate fechaFin; // Fecha de finalización
    private String estado; // Activa o inactiva

    // Constructor vacío
    public Suscripcion() {}

    // Constructor con parámetros
    public Suscripcion(int id, Cliente cliente, LocalDate fechaInicio, LocalDate fechaFin) {
        this.id = id;
        this.cliente = cliente;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

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
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", estado='" + estado + '\'' +
                '}';
    }
}