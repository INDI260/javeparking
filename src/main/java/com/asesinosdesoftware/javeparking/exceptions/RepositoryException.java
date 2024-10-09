package com.asesinosdesoftware.javeparking.exceptions;

public class RepositoryException extends Exception {

    private String detalle;

    public RepositoryException(String a){
        detalle = a;
    }
    public String toString(){
        return "Excepción de Repositorio["  + detalle + "]";
    }
}
