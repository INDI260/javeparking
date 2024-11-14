package com.asesinosdesoftware.javeparking.exceptions;

public class ServiceException extends Exception {

    private String detalle;

    public ServiceException(String a){
        detalle = a;
    }
    public String toString(){
        return "Excepción de ReservaC["  + detalle + "]";
    }
}
