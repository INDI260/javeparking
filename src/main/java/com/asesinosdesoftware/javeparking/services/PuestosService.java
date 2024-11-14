package com.asesinosdesoftware.javeparking.services;

import com.asesinosdesoftware.javeparking.entities.Parqueadero;
import com.asesinosdesoftware.javeparking.entities.Puesto;
import com.asesinosdesoftware.javeparking.exceptions.ServiceException;
import com.asesinosdesoftware.javeparking.repository.ParqueaderoRepository;
import com.asesinosdesoftware.javeparking.repository.PuestoRepository;

import java.sql.SQLException;

public class PuestosService {

    PuestoRepository puestoRepository = new PuestoRepository();
    ParqueaderoRepository parqueaderoRepository = new ParqueaderoRepository();

    public void agregarPuestos(int idparq, int cantP, int cantM, int cantG, Puesto puesto) throws SQLException, ServiceException {

        Parqueadero parqueadero = new Parqueadero();

        if(parqueaderoRepository.buscarParqueaderoPorId(idparq,parqueadero)==null){
            throw new ServiceException("No se encontro el parqueadero solicitado");
        }

        for (int i=0; i<cantP; i++){
            puesto.setTamano('p');
            puesto.setDisponibilidad(false);
            puesto.setParqueaderoID(idparq);
            puestoRepository.agregarPuesto(puesto);
        }

        for (int i=0; i<cantM; i++){
            puesto.setTamano('m');
            puesto.setDisponibilidad(false);
            puesto.setParqueaderoID(idparq);
            puestoRepository.agregarPuesto(puesto);
        }

        for (int i=0; i<cantG; i++){
            puesto.setTamano('g');
            puesto.setDisponibilidad(false);
            puesto.setParqueaderoID(idparq);
            puestoRepository.agregarPuesto(puesto);
        }

    }

    public void editarpuestos(String tamano, int idparq, Boolean disponibilidad,Puesto puestoseleccionado) throws SQLException, ServiceException {

        Parqueadero parqueadero = new Parqueadero();
        if(parqueaderoRepository.buscarParqueaderoPorId(idparq,parqueadero)==null ){
            throw new ServiceException("No se encontro el parqueadero solicitado");
        }

        puestoseleccionado.setTamano(tamano.charAt(0));
        puestoseleccionado.setDisponibilidad(disponibilidad);
        puestoseleccionado.setParqueaderoID(idparq);
        puestoRepository.actualizarPuesto(puestoseleccionado);


    }

    public void eliminarPuestos(Puesto puestoseleccionado) throws SQLException, ServiceException {

        puestoRepository.eliminarPuesto(puestoseleccionado);
    }

}
