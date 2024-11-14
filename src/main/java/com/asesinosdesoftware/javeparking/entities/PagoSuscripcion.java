package com.asesinosdesoftware.javeparking.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PagoSuscripcion extends Pago{

    private int id; // Este dato lo crea automáticamente el manejador de bases de datos
    private Suscripcion suscripcion; // Suscripcion asociada al pago
    private BigDecimal valor; // El valor monetario que debe recibirse en el pago
    private LocalDateTime fecha; // Este atributo guarda la fecha y la hora del pago
    private String metodoPago; // Método de pago (ej: Tarjeta de Crédito, PayPal)

    /**
     * Método constructor por parámetros
     *
     */
    public PagoSuscripcion() {

    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Suscripcion getSuscripcion() {
        return suscripcion;
    }

    public void setSuscripcion(Suscripcion suscripcion) {
        this.suscripcion = suscripcion;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getFechaPago() {
        return fecha;
    }

    public void setFecha(LocalDateTime fechaPago) {
        this.fecha = fechaPago;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
}

