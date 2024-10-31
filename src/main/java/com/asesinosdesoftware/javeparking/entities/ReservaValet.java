package com.asesinosdesoftware.javeparking.entities;

import java.time.LocalDateTime;

public class ReservaValet {
    private int id; // Identificador único de la reserva
    private Cliente cliente; // Cliente que realiza la reserva
    private Vehiculo vehiculo; // Vehículo a ser estacionado
    private LocalDateTime fechaHoraReserva; // Fecha y hora de la reserva
    private String metodoPago; // Método de pago seleccionado (Tarjeta, PayPal, etc.)
    private String estado; // Estado de la reserva (Pendiente, Confirmada, Cancelada)

    /**
     * constructor parametros de reserva del valet parking
     * @param cliente
     * @param vehiculo
     * @param fechaHoraReserva
     * @param metodoPago
     * @param estado
     */
    public ReservaValet(Cliente cliente, Vehiculo vehiculo, LocalDateTime fechaHoraReserva, String metodoPago, String estado) {
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.fechaHoraReserva = fechaHoraReserva;
        this.metodoPago = metodoPago;
        this.estado = estado;
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

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
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