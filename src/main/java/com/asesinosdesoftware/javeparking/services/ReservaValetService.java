package com.asesinosdesoftware.javeparking.services;

import com.asesinosdesoftware.javeparking.entities.Puesto;
import com.asesinosdesoftware.javeparking.entities.Reserva;
import com.asesinosdesoftware.javeparking.entities.Vehiculo;
import com.asesinosdesoftware.javeparking.exceptions.ServiceException;
import com.asesinosdesoftware.javeparking.repository.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservaValetService {

    VehiculoRepository vehiculoRepository = new VehiculoRepository();
    PuestoRepository puestoRepository = new PuestoRepository();
    ReservaRepository reservaRepository = new ReservaRepository();

    /**
     * Metodo para que el cliente pueda crear una reserva para valet parking.
     * @param IDHoraEntrada
     * @param IDHoraSalida
     * @param IDplaca
     * @param reserva
     * @throws SQLException
     * @throws ServiceException
     */
    public void crearReservaValet(String IDHoraEntrada, String IDHoraSalida, String IDplaca, Reserva reserva) throws SQLException, ServiceException {
        // Fecha y hora de la reserva
        LocalDate fechaActual = LocalDate.now();
        LocalTime horaEntrada = LocalTime.parse(IDHoraEntrada);
        LocalDateTime horaEntradaCompleta = LocalDateTime.of(fechaActual, horaEntrada);
        LocalTime horaSalida = LocalTime.parse(IDHoraSalida);
        LocalDateTime horaSalidaCompleta = LocalDateTime.of(fechaActual, horaSalida);

        // Validación de hora de entrada y salida
        if (horaSalidaCompleta.isBefore(horaEntradaCompleta) || horaSalidaCompleta.isEqual(horaEntradaCompleta)) {
            throw new ServiceException("La hora de salida debe ser posterior a la hora de entrada.");
        }

        // Verificar que el vehículo existe
        Vehiculo vehiculo = new Vehiculo();
        vehiculoRepository.buscarVehiculo(IDplaca, vehiculo);

        // Buscar un puesto disponible para valet parking (sin tener en cuenta tamaño)
        Puesto puesto = new Puesto();
        puestoRepository.buscarPuesto("", false, puesto); // Aquí se pasa una cadena vacía para buscar cualquier puesto disponible

        if (puesto == null) {
            throw new ServiceException("No hay puestos disponibles para valet parking.");
        }

        // Configurar la reserva
        reserva.setHoraEntrada(horaEntradaCompleta);
        reserva.setHoraSalida(horaSalidaCompleta);
        reserva.setVehiculo(vehiculo);
        reserva.setPuesto(puesto);

        // Guardar la reserva
        reservaRepository.agregarReserva(reserva);

        // Marcar el puesto como ocupado
        puesto.setDisponibilidad(false);
        puestoRepository.actualizarPuesto(puesto);
    }

    /**
     * Metodo por el cual el cliente puede editar su reserva de valet parking.
     * @param IDHoraEntrada
     * @param IDHoraSalida
     * @param IDplaca
     * @param reservaSeleccionada
     * @throws SQLException
     * @throws ServiceException
     */
    public void editarReservaValet(String IDHoraEntrada, String IDHoraSalida, String IDplaca, Reserva reservaSeleccionada) throws SQLException, ServiceException {
        // Actualizar datos de la reserva
        LocalDate fechaActual = LocalDate.now();
        LocalTime horaEntrada = LocalTime.parse(IDHoraEntrada);
        LocalDateTime horaEntradaCompleta = LocalDateTime.of(fechaActual, horaEntrada);
        LocalTime horaSalida = LocalTime.parse(IDHoraSalida);
        LocalDateTime horaSalidaCompleta = LocalDateTime.of(fechaActual, horaSalida);

        if (horaSalidaCompleta.isBefore(horaEntradaCompleta) || horaSalidaCompleta.isEqual(horaEntradaCompleta)) {
            throw new ServiceException("La hora de salida debe ser posterior a la hora de entrada.");
        }

        // Buscar el vehículo
        Vehiculo vehiculo = new Vehiculo();
        vehiculoRepository.buscarVehiculo(IDplaca, vehiculo);

        // Verificar si el puesto necesita cambio
        if (reservaSeleccionada.getPuesto() != null) {
            // Liberar el puesto anterior
            Puesto puestoAnterior = reservaSeleccionada.getPuesto();
            puestoAnterior.setDisponibilidad(true);
            puestoRepository.actualizarPuesto(puestoAnterior);
        }

        // Buscar un nuevo puesto disponible para valet parking
        Puesto nuevoPuesto = new Puesto();
        puestoRepository.buscarPuesto("", false, nuevoPuesto); // Pasamos una cadena vacía para buscar cualquier puesto disponible

        if (nuevoPuesto == null) {
            throw new ServiceException("No hay puestos disponibles para valet parking.");
        }

        // Actualizar la reserva
        reservaSeleccionada.setHoraEntrada(horaEntradaCompleta);
        reservaSeleccionada.setHoraSalida(horaSalidaCompleta);
        reservaSeleccionada.setVehiculo(vehiculo);
        reservaSeleccionada.setPuesto(nuevoPuesto);

        // Guardar los cambios en la reserva
        reservaRepository.actualizarReserva(reservaSeleccionada);

        // Marcar el nuevo puesto como ocupado
        nuevoPuesto.setDisponibilidad(false);
        puestoRepository.actualizarPuesto(nuevoPuesto);
    }

    /**
     * Metodo para que el cliente pueda eliminar su reserva de valet parking.
     * @param reservaSeleccionada
     * @throws SQLException
     */
    public void eliminarReservaValet(Reserva reservaSeleccionada) throws SQLException {
        // Eliminar la reserva
        reservaRepository.eliminarReserva(reservaSeleccionada);

        // Liberar el puesto de valet parking
        Puesto puesto = reservaSeleccionada.getPuesto();
        puesto.setDisponibilidad(true);
        puestoRepository.actualizarPuesto(puesto);
    }
}