package com.asesinosdesoftware.javeparking.entities;

import java.util.List;

public class Parqueadero {

    private int id; //Este dato lo crea automáticamte el manejador de bases de datos y por tanto no se debe asignar manualmente
    private List<Puesto> puestos; //Lista de puestos del parqueadero
    private float TarifaEstandar;

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

    public float getTarifaEstandar() {
        return TarifaEstandar;
    }

    public void setTarifaEstandar(float tarifaEstandar) {
        this.TarifaEstandar = tarifaEstandar;
    }
}
