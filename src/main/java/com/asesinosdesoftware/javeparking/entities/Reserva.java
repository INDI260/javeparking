package com.asesinosdesoftware.javeparking.entities;

import java.util.Date;

public class Reserva {

    private int id;
    private Date fecha;
    private String horaEntrada;
    private String horaSalida;
    private Vehiculo vehiculo;
    private Puesto puesto;
}
