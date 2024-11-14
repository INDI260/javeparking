package com.asesinosdesoftware.javeparking.services;

import com.asesinosdesoftware.javeparking.entities.Parqueadero;
import com.asesinosdesoftware.javeparking.repository.ParqueaderoRepository;

import java.math.BigDecimal;
import java.sql.SQLException;

public class ParqueaderoService {

    ParqueaderoRepository parqueaderoRepository = new ParqueaderoRepository();

    public void crearparq(String parqPequeno, String parqMediano, String parqGrande, String suPequena, String suMediano, String suGrande, Double idjave,Parqueadero parqueadero) throws SQLException {

        BigDecimal bdParqPequeno = new BigDecimal(parqPequeno);
        BigDecimal bdParqMediano = new BigDecimal(parqMediano);
        BigDecimal bdParqGrande = new BigDecimal(parqGrande);
        BigDecimal bdSuPequena = new BigDecimal(suPequena);
        BigDecimal bdSuMediano = new BigDecimal(suMediano);
        BigDecimal bdSuGrande = new BigDecimal(suGrande);
        BigDecimal bdJaveriana = new BigDecimal(idjave);

        parqueadero.setTarifaPequeno(bdParqPequeno);
        parqueadero.setTarifaMediano(bdParqMediano);
        parqueadero.setTarifaGrande(bdParqGrande);
        parqueadero.setSuscripcionPequeno(bdSuPequena);
        parqueadero.setSuscripcionMediano(bdSuMediano);
        parqueadero.setSuscripcionGrande(bdSuGrande);
        parqueadero.setDescuentoJaveriano(bdJaveriana);

        parqueaderoRepository.agregarParqueadero(parqueadero);

    }

}
