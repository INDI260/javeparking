package com.asesinosdesoftware.javeparking.services;

import com.asesinosdesoftware.javeparking.entities.RegistroOp;
import com.asesinosdesoftware.javeparking.repository.RegistroOpRepository;
import com.asesinosdesoftware.javeparking.exceptions.RepositoryException;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class RegistroOpService {

    private RegistroOpRepository registroOpRepository;

    // Constructor que inicializa el repositorio
    public RegistroOpService() {
        this.registroOpRepository = new RegistroOpRepository();
    }

    /**
     * Método para registrar la entrada de un vehículo en el sistema.
     * @param placa: Placa del vehículo.
     * @param tipo: Tipo de vehículo.
     * @param tamano: Tamaño del vehículo.
     * @param horaEntrada: Hora en la que el vehículo ingresa.
     * @throws SQLException: En caso de que ocurra un error al acceder a la base de datos.
     */
    public void registrarEntrada(String placa, String tipo, String tamano, LocalDateTime horaEntrada) throws SQLException, RepositoryException {
        // Crear un objeto RegistroOp y asignarle los valores
        RegistroOp registroOp = new RegistroOp();
        registroOp.setPlaca(placa);
        registroOp.setTipo(tipo.charAt(0));
        registroOp.setTamano(tamano.charAt(0));
        registroOp.setHoraEntrada(horaEntrada);

        // Llamar al repositorio para agregar el registro de la entrada
        registroOpRepository.agregarRegistroOp(registroOp);
    }
}









