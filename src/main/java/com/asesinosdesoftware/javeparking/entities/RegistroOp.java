package com.asesinosdesoftware.javeparking.entities;

import java.time.LocalDateTime;

public class RegistroOp extends Vehiculo {
    private LocalDateTime horaEntrada;

    // Getters y Setters
    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalDateTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }
}



