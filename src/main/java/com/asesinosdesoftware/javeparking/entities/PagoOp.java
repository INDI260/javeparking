package com.asesinosdesoftware.javeparking.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PagoOp extends Pago {

    private int id; // Este dato lo crea automáticamente el manejador de bases de datos
    private Vehiculo vehiculo; // Vehículo asociado al pago
    private BigDecimal valor; // El valor monetario del pago
    private LocalDateTime fecha; // Fecha y hora del pago
    private String metodoPago; // Método de pago (ej. "Online", "Efectivo", etc.)

    /**
     * Método constructor por parámetros
     * @param id
     * @param vehiculo
     * @param valor
     * @param fecha
     * @param metodoPago
     */
    public PagoOp(int id, Vehiculo vehiculo, BigDecimal valor, LocalDateTime fecha, String metodoPago) {
        this.id = id;
        this.vehiculo = vehiculo;
        this.valor = valor;
        this.fecha = fecha;
        this.metodoPago = metodoPago;
    }

    /**
     * Método constructor vacío
     */
    public PagoOp() {
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
}
