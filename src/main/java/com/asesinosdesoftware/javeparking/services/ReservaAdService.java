package com.asesinosdesoftware.javeparking.services;

import com.asesinosdesoftware.javeparking.entities.Puesto;
import com.asesinosdesoftware.javeparking.entities.Reserva;
import com.asesinosdesoftware.javeparking.entities.Vehiculo;
import com.asesinosdesoftware.javeparking.exceptions.ReservasException;
import com.asesinosdesoftware.javeparking.persistencia.DBConnectionManager;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;
import com.asesinosdesoftware.javeparking.repository.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


public class ReservaAdService {


    VehiculoRepository vehiculoRepository= new VehiculoRepository();
    PuestoRepository puestoRepository = new PuestoRepository();
    ReservaRepository reservaRepository = new ReservaRepository();
    IDBConnectionManager dbConnectionManager = new DBConnectionManager();


    public void crearReserva(String IDHoraEntrada, String IDHoraSalida,String IDplaca, String IdTamano,Reserva reserva) throws SQLException,ReservasException {


        LocalDate fechaActual = LocalDate.now();
        LocalTime horaEntrada = LocalTime.parse(IDHoraEntrada);
        LocalDateTime horaEntradaCompleta = LocalDateTime.of(fechaActual, horaEntrada);
        LocalTime horaSalida = LocalTime.parse(IDHoraSalida);
        LocalDateTime horaSalidaCompleta = LocalDateTime.of(fechaActual, horaSalida);

        if (horaSalidaCompleta.isBefore(horaEntradaCompleta) || horaSalidaCompleta.isEqual(horaEntradaCompleta)) {
            throw new ReservasException("Hora de entrada y salida mal definida");
        }

        Vehiculo vehiculo = new Vehiculo();
        vehiculoRepository.buscarVehiculo(dbConnectionManager.getConnection(), IDplaca, vehiculo);

        Puesto puesto = new Puesto();
        puestoRepository.buscarPuesto(IdTamano,false,dbConnectionManager.getConnection(),puesto);

        if(vehiculo.getTamano()!=IdTamano.charAt(0)){
            throw new ReservasException("Tamaño de reserva y de auto no coinciden");

        }

        reserva.setHoraEntrada(horaEntradaCompleta);
        reserva.setHoraSalida(horaSalidaCompleta);
        reserva.setVehiculo(vehiculo);
        reserva.setPuesto(puesto);


        reservaRepository.agregarReserva(dbConnectionManager.getConnection(), reserva);
        puesto.setDisponibilidad(true);
        puestoRepository.actualizarPuesto(dbConnectionManager.getConnection(), puesto);

    }

    public void editarReserva(String IDHoraEntrada, String IDHoraSalida,String IDplaca, String IdTamano,Reserva reservaSeleccionada) throws SQLException,ReservasException {
             // Actualizar datos de reserva aquí
                Puesto puesto = new Puesto();
                ReservaRepository reservaRepository = new ReservaRepository();

                LocalDate fechaActual = LocalDate.now();
                LocalTime horaEntrada = LocalTime.parse(IDHoraEntrada);
                LocalDateTime horaEntradaCompleta = LocalDateTime.of(fechaActual, horaEntrada);
                LocalTime horaSalida = LocalTime.parse(IDHoraSalida);
                LocalDateTime horaSalidaCompleta = LocalDateTime.of(fechaActual, horaSalida);

                if (horaSalidaCompleta.isBefore(horaEntradaCompleta) || horaSalidaCompleta.isEqual(horaEntradaCompleta)) {
                    throw new ReservasException("Hora de entrada y salida mal definida");

                }

                Vehiculo vehiculo = new Vehiculo();
                VehiculoRepository vehiculoRepository = new VehiculoRepository();
                vehiculoRepository.buscarVehiculo(dbConnectionManager.getConnection(), IDplaca, vehiculo);

                if(reservaSeleccionada.getPuesto().getTamano()!=IdTamano.charAt(0)){
                    Puesto puestoviejo = new Puesto();
                    puestoRepository.buscarPuesto(reservaSeleccionada.getPuesto().getId(), dbConnectionManager.getConnection(),puestoviejo);
                    puestoviejo.setDisponibilidad(false);
                    puestoRepository.actualizarPuesto(dbConnectionManager.getConnection(), puestoviejo);
                    puestoRepository.buscarPuesto(IdTamano,false,dbConnectionManager.getConnection(),puesto);
                    reservaSeleccionada.setPuesto(puesto);
                }


                if(vehiculo.getTamano()!=IdTamano.charAt(0)){
                    throw new ReservasException("Tamaño de reserva y de auto no coinciden");

                }

                reservaSeleccionada.setHoraEntrada(horaEntradaCompleta);
                reservaSeleccionada.setHoraSalida(horaSalidaCompleta);
                reservaSeleccionada.setVehiculo(vehiculo);
                puesto.setDisponibilidad(true);
                puestoRepository.actualizarPuesto(dbConnectionManager.getConnection(),puesto);
                reservaRepository.actualizarReserva(dbConnectionManager.getConnection(), reservaSeleccionada);
    }

    public void eliminarReserva(Reserva reservaSeleccionada) throws SQLException {

                ReservaRepository reservaRepository = new ReservaRepository();
                reservaRepository.eliminarReserva(dbConnectionManager.getConnection(), reservaSeleccionada);
                Puesto puesto = new Puesto();
                puestoRepository.buscarPuesto(reservaSeleccionada.getPuesto().getId(), dbConnectionManager.getConnection(),puesto);
                puesto.setDisponibilidad(false);
                puestoRepository.actualizarPuesto(dbConnectionManager.getConnection(), puesto);


    }

    public void generarReporteReservas(List<Reserva> reservasObservableList) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Reporte de Reservas");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo CSV", "*.csv"));
        File archivo = fileChooser.showSaveDialog(null);

        if (archivo != null) {
            try (FileWriter writer = new FileWriter(archivo)) {
                writer.append("ID Reserva, Placa, Hora Entrada, Hora Salida\n");
                for (Reserva reserva : reservasObservableList) {
                    writer.append(String.valueOf(reserva.getId())).append(", ")
                            .append(reserva.getVehiculo().getPlaca()).append(", ")
                            .append(reserva.getHoraEntrada().toString()).append(", ")
                            .append(reserva.getHoraSalida().toString()).append("\n");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
