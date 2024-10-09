package com.asesinosdesoftware.javeparking.exceptions;

public class InicioDeSesionException extends Exception {

    private String detalle;

    public InicioDeSesionException(String a){
        detalle = a;
    }
    public String toString(){
        return "Excepción de Inicio de sesion["  + detalle + "]";
    }
}
