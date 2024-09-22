package com.asesinosdesoftware.javeparking.exceptions;

public class VehiculoRepositoryException extends Exception {
    private String detalle;

    public VehiculoRepositoryException(String a){
        detalle = a;
    }
    public String toString(){
        return "Excepción de Repositorio del vehiculo["  + detalle + "]";
    }
}
