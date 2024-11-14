package com.asesinosdesoftware.javeparking.entities;

import java.time.LocalDateTime;

public class RegistroOp{
    private LocalDateTime horaEntrada;
    private Vehiculo vehiculo;
    private Puesto puesto;

    // Getters y Setters
    public Vehiculo getVehiculo() {
        return vehiculo; // Método para obtener el vehículo
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo; // Método para establecer el vehículo
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalDateTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }
}



