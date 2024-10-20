package com.asesinosdesoftware.javeparking.entities;

import java.time.LocalDateTime;

public class Reserva {

    private int id;
    /*Una manera básica de asignar un atributo de LocalDateTime puede ser a partir de un string de esta manera:
    ISO_LOCAL_DATE_TIME = ISO_LOCAL_DATE + ‘T’ + ISO_LOCAL_TIME
    Un ejemplo sería este:
    LocalDateTime today = LocalDateTime.parse("2019-03-27T10:15:30");
    */
    private LocalDateTime horaEntrada;//Este atributo guarda la fecha y la hora de entrada del vehiculo en la reserva.
    private LocalDateTime horaSalida;//Este atributo guarda la fehca y la hora de salida del vehiculo en la reserva
    private Vehiculo vehiculo; //Vehiculo asociado a la reserva
    private Puesto puesto; //Puesto en el cual se va a alojar el vehiculo dentro del parqueadero

    /**
     * Constructor por parametros de Reserva.
     * @param id
     * @param horaEntrada
     * @param horaSalida
     * @param vehiculo
     * @param puesto
     */
    public Reserva(int id,  LocalDateTime horaEntrada, LocalDateTime horaSalida, Vehiculo vehiculo, Puesto puesto) {
        this.id = id;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.vehiculo = vehiculo;
        this.puesto = puesto;
    }

    /**
     * Constructor vacío de reserva.
     */
    public Reserva() {
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

    public Puesto getPuesto() {
        return puesto;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }
}
