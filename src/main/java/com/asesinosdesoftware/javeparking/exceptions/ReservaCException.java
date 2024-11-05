package com.asesinosdesoftware.javeparking.exceptions;

public class ReservaCException extends Exception {

    private String detalle;

    public ReservaCException(String a){
        detalle = a;
    }
    public String toString(){
        return "Excepción de ReservaC["  + detalle + "]";
    }
}
