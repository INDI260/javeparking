package com.asesinosdesoftware.javeparking.services;

import com.asesinosdesoftware.javeparking.entities.Puesto;
import com.asesinosdesoftware.javeparking.entities.Reserva;
import com.asesinosdesoftware.javeparking.entities.Vehiculo;
import com.asesinosdesoftware.javeparking.exceptions.ServiceException;
import com.asesinosdesoftware.javeparking.repository.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class ReservaAdService {


    VehiculoRepository vehiculoRepository= new VehiculoRepository();
    PuestoRepository puestoRepository = new PuestoRepository();
    ReservaRepository reservaRepository = new ReservaRepository();


    /**
     * Metodo para que el administrador pueda crear una reserva individual
     * @param IDHoraEntrada
     * @param IDHoraSalida
     * @param IDplaca
     * @param IdTamano
     * @param reserva
     * @throws SQLException
     * @throws ServiceException
     */
    public void crearReserva(String IDHoraEntrada, String IDHoraSalida,String IDplaca, String IdTamano,Reserva reserva) throws SQLException, ServiceException {

        LocalDate fechaActual = LocalDate.now();
        LocalTime horaEntrada = LocalTime.parse(IDHoraEntrada);
        LocalDateTime horaEntradaCompleta = LocalDateTime.of(fechaActual, horaEntrada);
        LocalTime horaSalida = LocalTime.parse(IDHoraSalida);
        LocalDateTime horaSalidaCompleta = LocalDateTime.of(fechaActual, horaSalida);

        if (horaSalidaCompleta.isBefore(horaEntradaCompleta) || horaSalidaCompleta.isEqual(horaEntradaCompleta)) {
            throw new ServiceException("Hora de entrada y salida mal definida");
        }

        Vehiculo vehiculo = new Vehiculo();
        vehiculoRepository.buscarVehiculo(IDplaca, vehiculo);

        Puesto puesto = new Puesto();
        puestoRepository.buscarPuesto(IdTamano,false,puesto);

        if(vehiculo.getTamano()!=IdTamano.charAt(0)){
            throw new ServiceException("Tamaño de reserva y de auto no coinciden");

        }
        reserva = new Reserva();
        reserva.setHoraEntrada(horaEntradaCompleta);
        reserva.setHoraSalida(horaSalidaCompleta);
        reserva.setVehiculo(vehiculo);
        reserva.setPuesto(puesto);


        reservaRepository.agregarReserva(reserva);
        puesto.setDisponibilidad(true);
        puestoRepository.actualizarPuesto(puesto);

    }

    /**
     * Metodo por el cual el administrador puede editar los datos de una reserva
     * @param IDHoraEntrada
     * @param IDHoraSalida
     * @param IDplaca
     * @param IdTamano
     * @param reservaSeleccionada
     * @throws SQLException
     * @throws ServiceException
     */
    public void editarReserva(String IDHoraEntrada, String IDHoraSalida,String IDplaca, String IdTamano,Reserva reservaSeleccionada) throws SQLException, ServiceException {
             // Actualizar datos de reserva aquí
                Puesto puesto = new Puesto();
                ReservaRepository reservaRepository = new ReservaRepository();

                LocalDate fechaActual = LocalDate.now();
                LocalTime horaEntrada = LocalTime.parse(IDHoraEntrada);
                LocalDateTime horaEntradaCompleta = LocalDateTime.of(fechaActual, horaEntrada);
                LocalTime horaSalida = LocalTime.parse(IDHoraSalida);
                LocalDateTime horaSalidaCompleta = LocalDateTime.of(fechaActual, horaSalida);

                if (horaSalidaCompleta.isBefore(horaEntradaCompleta) || horaSalidaCompleta.isEqual(horaEntradaCompleta)) {
                    throw new ServiceException("Hora de entrada y salida mal definida");

                }

                Vehiculo vehiculo = new Vehiculo();
                VehiculoRepository vehiculoRepository = new VehiculoRepository();
                vehiculoRepository.buscarVehiculo(IDplaca, vehiculo);

                if(reservaSeleccionada.getPuesto().getTamano()!=IdTamano.charAt(0)){
                    Puesto puestoviejo = new Puesto();
                    puestoRepository.buscarPuesto(reservaSeleccionada.getPuesto().getId(),puestoviejo);
                    puestoviejo.setDisponibilidad(false);
                    puestoRepository.actualizarPuesto(puestoviejo);
                    puestoRepository.buscarPuesto(IdTamano,false,puesto);
                    reservaSeleccionada.setPuesto(puesto);
                }


                if(vehiculo.getTamano()!=IdTamano.charAt(0)){
                    throw new ServiceException("Tamaño de reserva y de auto no coinciden");

                }

                reservaSeleccionada.setHoraEntrada(horaEntradaCompleta);
                reservaSeleccionada.setHoraSalida(horaSalidaCompleta);
                reservaSeleccionada.setVehiculo(vehiculo);
                puesto.setDisponibilidad(true);
                puestoRepository.actualizarPuesto(puesto);
                reservaRepository.actualizarReserva(reservaSeleccionada);
    }

    /**
     * Metodo por el cual el administrador puede eliminar por completo una reserva individual
     * @param reservaSeleccionada
     * @throws SQLException
     */
    public void eliminarReserva(Reserva reservaSeleccionada) throws SQLException {

                ReservaRepository reservaRepository = new ReservaRepository();
                reservaRepository.eliminarReserva(reservaSeleccionada);
                Puesto puesto = new Puesto();
                puestoRepository.buscarPuesto(reservaSeleccionada.getPuesto().getId(),puesto);
                puesto.setDisponibilidad(false);
                puestoRepository.actualizarPuesto(puesto);


    }

    /**
     * Metodo por el cual el administrador puede crear un reporte sobre las reservas de su parqueadero
     * @param reservasObservableList
     */
    public void generarReporteReservas(List<Reserva> reservasObservableList) {
        // Define la carpeta de destino dentro de com.asesinosdesoftware.javeparking
        String carpetaReportes = "src/main/java/com/asesinosdesoftware/javeparking/reportes";
        Path pathCarpeta = Paths.get(carpetaReportes);

        // Crea la carpeta "reportes" si no existe
        try {
            if (!Files.exists(pathCarpeta)) {
                Files.createDirectories(pathCarpeta);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return; // Salir del método si falla la creación de la carpeta
        }

        // Genera una marca de tiempo para el nombre del archivo usando LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String timestamp = LocalDateTime.now().format(formatter);

        // Define el archivo con la ruta completa y el nombre con fecha y hora
        String nombreArchivo = "reporte_reservas_" + timestamp + ".csv";
        File archivo = new File(pathCarpeta.toFile(), nombreArchivo);

        // Escribe el archivo CSV
        try (FileWriter writer = new FileWriter(archivo)) {
            writer.append("ID Reserva, Placa, Hora Entrada, Hora Salida\n");
            for (Reserva reserva : reservasObservableList) {
                writer.append(String.valueOf(reserva.getId())).append(", ")
                        .append(reserva.getVehiculo().getPlaca()).append(", ")
                        .append(reserva.getHoraEntrada() != null ? reserva.getHoraEntrada().format(formatter) : "N/A").append(", ")
                        .append(reserva.getHoraSalida() != null ? reserva.getHoraSalida().format(formatter) : "N/A").append("\n");
            }
            System.out.println("Reporte generado en: " + archivo.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
