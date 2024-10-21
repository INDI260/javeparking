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
     * @param suscripcion
     * @param valor
     * @param fechaPago
     * @param metodoPago
     */
    public PagoSuscripcion(Suscripcion suscripcion, BigDecimal valor, LocalDateTime fechaPago, String metodoPago) {
        this.suscripcion = suscripcion;
        this.valor = valor;
        this.fecha = fechaPago;
        this.metodoPago = metodoPago;
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

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fecha = fechaPago;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
}

