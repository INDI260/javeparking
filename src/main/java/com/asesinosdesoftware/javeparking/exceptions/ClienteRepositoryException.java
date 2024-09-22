package com.asesinosdesoftware.javeparking.exceptions;

public class ClienteRepositoryException extends Exception {

    private String detalle;

    public ClienteRepositoryException(String a){
        detalle = a;
    }
    public String toString(){
        return "Excepción de Repositorio del cliente["  + detalle + "]";
    }
}
