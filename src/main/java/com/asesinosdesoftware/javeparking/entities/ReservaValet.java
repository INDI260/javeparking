package com.asesinosdesoftware.javeparking.entities;

import java.time.LocalDateTime;

public class ReservaValet {

    private int id;
    private LocalDateTime horaEntrada; // Hora de entrada del vehículo
    private LocalDateTime horaSalida; // Hora de salida del vehículo
    private Vehiculo vehiculo; // Vehículo asociado a la reserva
    private Cliente cliente; // Cliente que realizó la reserva
    private Puesto puesto;//Puesto asignado al vehiculo
    private String numeroReserva; // Número único de la reserva para el valet

    /**
     * Constructor por parámetros de ReservaValet.
     * @param id
     * @param horaEntrada
     * @param horaSalida
     * @param vehiculo
     * @param cliente
     * @param puesto
     */
    public ReservaValet(int id, LocalDateTime horaEntrada, LocalDateTime horaSalida, Vehiculo vehiculo, Cliente cliente, Puesto puesto) {
        this.id = id;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.vehiculo = vehiculo;
        this.cliente = cliente;
        this.puesto = puesto;
        this.numeroReserva = generarNumeroReserva();
    }

    /**
     * Constructor vacío de ReservaValet.
     */
    public ReservaValet() {
    }

    /* Getters y Setters */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalDateTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public LocalDateTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalDateTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }

    private String generarNumeroReserva() {
        // Generar un número de reserva único, en este caso puede ser un UUID o un método secuencial
        return "VAL-" + java.util.UUID.randomUUID().toString().substring(0, 8); // Ejemplo de formato
    }
}