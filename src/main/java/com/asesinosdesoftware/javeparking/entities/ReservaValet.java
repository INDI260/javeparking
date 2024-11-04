package com.asesinosdesoftware.javeparking.entities;

import java.time.LocalDateTime;

public class ReservaValet {

    private int idCliente;
    private String placaVehiculo;
    private LocalDateTime fechaHoraReserva;
    private String metodoPago;
    private String estado;

    public ReservaValet(int idCliente, String placaVehiculo, LocalDateTime fechaHoraReserva, String metodoPago, String estado) {
        this.idCliente = idCliente;
        this.placaVehiculo = placaVehiculo;
        this.fechaHoraReserva = fechaHoraReserva;
        this.metodoPago = metodoPago;
        this.estado = estado;
    }

    // Getters y Setters

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getPlacaVehiculo() {
        return placaVehiculo;
    }

    public void setPlacaVehiculo(String placaVehiculo) {
        this.placaVehiculo = placaVehiculo;
    }

    public LocalDateTime getFechaHoraReserva() {
        return fechaHoraReserva;
    }

    public void setFechaHoraReserva(LocalDateTime fechaHoraReserva) {
        this.fechaHoraReserva = fechaHoraReserva;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}