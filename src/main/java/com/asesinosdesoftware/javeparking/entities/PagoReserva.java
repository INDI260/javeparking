package com.asesinosdesoftware.javeparking.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PagoReserva extends Pago {



    private int id; // Este dato lo crea automáticamente el manejador de bases de datos
    private Reserva reserva; // Reserva asociada al pago (opcional)
    private BigDecimal valor; // El valor monetario que debe recibirse en el pago
    private LocalDateTime fecha;//Este atributo guarda la fecha y la hora de entrada del vehículo en la reserva.
    private String metodoPago;

    /**
     * Método constructor por parámetros
     * @param id
     * @param reserva
     * @param valor
     * @param fechaPago
     */
    public PagoReserva(int id, Reserva reserva, BigDecimal valor, LocalDateTime fechaPago) {
        this.id = id;
        this.reserva = reserva;
        this.valor = valor;
        this.fecha = fechaPago;
    }

    /**
     * Método constructor vacío
     */
    public PagoReserva() {
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

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getFecha() {
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


