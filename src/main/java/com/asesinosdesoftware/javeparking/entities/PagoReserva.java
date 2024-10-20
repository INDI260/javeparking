package com.asesinosdesoftware.javeparking.entities;

import java.time.LocalDateTime;

public class PagoReserva {



    private int id; // Este dato lo crea automáticamente el manejador de bases de datos
    private Reserva reserva; // Reserva asociada al pago (opcional)
    private double valor; // El valor monetario que debe recibirse en el pago
    private LocalDateTime fechaPago;//Este atributo guarda la fecha y la hora de entrada del vehículo en la reserva.
    private String metodoPago;

    public PagoReserva() {
    }

    public PagoReserva(int id, Reserva reserva, double valor, LocalDateTime fechaPago) {
        this.id = id;
        this.reserva = reserva;
        this.valor = valor;
        this.fechaPago = fechaPago;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
}


