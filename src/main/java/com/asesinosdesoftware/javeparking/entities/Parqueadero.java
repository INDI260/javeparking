package com.asesinosdesoftware.javeparking.entities;

import java.math.BigDecimal;
import java.util.List;

public class Parqueadero {

    private int id; //Este dato lo crea automáticamte el manejador de bases de datos y por tanto no se debe asignar manualmente
    private List<Puesto> puestos; //Lista de puestos del parqueadero
    private BigDecimal TarifaPequeno; //Tarifa cobrada por hora para un puesto pequeño
    private BigDecimal TarifaMediano; //Tarifa cobrada por hora para un puesto mediano
    private BigDecimal TarifaGrande; //Tarifa cobrada por hora para un puesto grande

    /*Getters y setters*/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Puesto> getPuestos() {
        return puestos;
    }

    public void setPuestos(List<Puesto> puestos) {
        this.puestos = puestos;
    }

    public BigDecimal getTarifaPequeno() {
        return TarifaPequeno;
    }

    public void setTarifaPequeno(BigDecimal tarifaPequeno) {
        TarifaPequeno = tarifaPequeno;
    }

    public BigDecimal getTarifaMediano() {
        return TarifaMediano;
    }

    public void setTarifaMediano(BigDecimal tarifaMediano) {
        TarifaMediano = tarifaMediano;
    }

    public BigDecimal getTarifaGrande() {
        return TarifaGrande;
    }

    public void setTarifaGrande(BigDecimal tarifaGrande) {
        TarifaGrande = tarifaGrande;
    }
}
