package com.asesinosdesoftware.javeparking.entities;

import java.math.BigDecimal;
import java.util.List;

public class Parqueadero {

    private int id; //Este dato lo crea automáticamte el manejador de bases de datos y por tanto no se debe asignar manualmente
    private List<Puesto> puestos; //Lista de puestos del parqueadero
    private BigDecimal TarifaPequeno; //Tarifa cobrada por hora para un puesto pequeño
    private BigDecimal TarifaMediano; //Tarifa cobrada por hora para un puesto mediano
    private BigDecimal TarifaGrande; //Tarifa cobrada por hora para un puesto grande
    private BigDecimal SuscripcionPequeno; //Valor de la suscripción para un puesto pequeño
    private BigDecimal SuscripcionMediano;//Valor de la suscripción para un puesto mediano
    private BigDecimal SuscripcionGrande; //Valor de la suscripción para un puesto grande
    private BigDecimal DescuentoJaveriano; //Porcenaje de descuento por ser miembro de la comunidad

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

    public BigDecimal getDescuentoJaveriano() {
        return DescuentoJaveriano;
    }

    public void setDescuentoJaveriano(BigDecimal descuentoJaveriano) {
        DescuentoJaveriano = descuentoJaveriano;
    }

    public BigDecimal getSuscripcionGrande() {
        return SuscripcionGrande;
    }

    public void setSuscripcionGrande(BigDecimal suscripcionGrande) {
        SuscripcionGrande = suscripcionGrande;
    }

    public BigDecimal getSuscripcionMediano() {
        return SuscripcionMediano;
    }

    public void setSuscripcionMediano(BigDecimal suscripcionMediano) {
        SuscripcionMediano = suscripcionMediano;
    }

    public BigDecimal getSuscripcionPequeno() {
        return SuscripcionPequeno;
    }

    public void setSuscripcionPequeno(BigDecimal suscripcionPequeno) {
        SuscripcionPequeno = suscripcionPequeno;
    }
}
