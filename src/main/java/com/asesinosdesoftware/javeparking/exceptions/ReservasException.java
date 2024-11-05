package com.asesinosdesoftware.javeparking.exceptions;

public class ReservasException extends Exception {

    private String detalle;

    public ReservasException(String a){
        detalle = a;
    }
    public String toString(){
        return "Excepción de ReservaC["  + detalle + "]";
    }
}
