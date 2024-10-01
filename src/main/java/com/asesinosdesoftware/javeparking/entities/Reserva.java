package com.asesinosdesoftware.javeparking.entities;

import java.util.Date;

public class Reserva {

    private int id; //Este dato lo crea automáticamte el manejador de bases de datos y por tanto no se debe asignar manualmente
    private Date fecha; //Fecha en la cual se realizó la reserva
    private String horaEntrada; //Hora de entrada del vehículo al parqueadero
    private String horaSalida; //Hora de salida del vehiculo al parqueadero
    private Vehiculo vehiculo; //Vehiculo asociado a la reserva
    private Puesto puesto; //Puesto en el cual se va a alojar el vehiculo dentro del parqueadero

    public Reserva(int id, Date fecha, String horaEntrada, String horaSalida, Vehiculo vehiculo, Puesto puesto) {
        this.id = id;
        this.fecha = fecha;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.vehiculo = vehiculo;
        this.puesto = puesto;
    }

    public Reserva() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
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
