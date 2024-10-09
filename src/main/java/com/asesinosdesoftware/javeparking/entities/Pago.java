package com.asesinosdesoftware.javeparking.entities;

public class Pago {

    private int id; //Este dato lo crea automáticamte el manejador de bases de datos y por tanto no se debe asignar manualmente
    private Reserva reserva; //Reserva asociada al pago
    private float valor; //El valor monetario que debe recibirse en el pago

}
