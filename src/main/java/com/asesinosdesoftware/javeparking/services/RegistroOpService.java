package com.asesinosdesoftware.javeparking.services;

import com.asesinosdesoftware.javeparking.entities.Vehiculo;
import com.asesinosdesoftware.javeparking.repository.VehiculoRepository;
import com.asesinosdesoftware.javeparking.exceptions.RepositoryException;
import java.sql.SQLException;

public class RegistroOpService {

    private VehiculoRepository vehiculoRepository;

    // Constructor que inicializa el repositorio
    public RegistroOpService() {
        this.vehiculoRepository = new VehiculoRepository();
    }

    public void registrarVehiculo(Vehiculo vehiculo) throws SQLException, RepositoryException {
        // Validación de los datos antes de proceder
        if (vehiculo.getPlaca() == null || vehiculo.getPlaca().isEmpty()) {
            throw new IllegalArgumentException("La placa es obligatoria.");
        }
        // Llamar al repositorio para registrar el vehículo en la base de datos
        vehiculoRepository.agregarVehiculo(vehiculo);
    }
}


