package com.asesinosdesoftware.javeparking.entities;

public class Pago {

    public enum TipoPago {
        RESERVA,
        SUSCRIPCION
    }

    private int id; // Este dato lo crea automáticamente el manejador de bases de datos
    private Reserva reserva; // Reserva asociada al pago (opcional)
    private Suscripcion suscripcion; // Suscripción asociada al pago (opcional)
    private float valor; // El valor monetario que debe recibirse en el pago
    private java.sql.Date fechaPago; // Fecha en que se realizó el pago
    private TipoPago tipoPago; // Tipo de pago: RESERVA o SUSCRIPCION

    // Constructor
    public Pago(float valor, Reserva reserva, Suscripcion suscripcion, TipoPago tipoPago) {
        this.valor = valor;
        this.reserva = reserva;
        this.suscripcion = suscripcion;
        this.fechaPago = new java.sql.Date(System.currentTimeMillis());
        this.tipoPago = tipoPago;
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

    public Suscripcion getSuscripcion() {
        return suscripcion;
    }

    public void setSuscripcion(Suscripcion suscripcion) {
        this.suscripcion = suscripcion;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public java.sql.Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(java.sql.Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public TipoPago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(TipoPago tipoPago) {
        this.tipoPago = tipoPago;
    }
}


