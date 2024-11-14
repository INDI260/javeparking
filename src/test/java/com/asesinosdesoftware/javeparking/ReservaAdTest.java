package com.asesinosdesoftware.javeparking;

import com.asesinosdesoftware.javeparking.entities.Puesto;
import com.asesinosdesoftware.javeparking.entities.Reserva;
import com.asesinosdesoftware.javeparking.entities.Vehiculo;
import com.asesinosdesoftware.javeparking.exceptions.RepositoryException;
import com.asesinosdesoftware.javeparking.exceptions.ServiceException;
import com.asesinosdesoftware.javeparking.persistencia.H2DBConnectionManager;
import com.asesinosdesoftware.javeparking.persistencia.IDBConnectionManager;
import com.asesinosdesoftware.javeparking.repository.PuestoRepository;
import com.asesinosdesoftware.javeparking.repository.ReservaRepository;
import com.asesinosdesoftware.javeparking.repository.VehiculoRepository;
import com.asesinosdesoftware.javeparking.services.ReservaAdService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservaAdTest {

        IDBConnectionManager dbConnectionManager = new H2DBConnectionManager();
        private ReservaAdService reservaAdService;
        private VehiculoRepository vehiculoRepository;
        private PuestoRepository puestoRepository;
        private ReservaRepository reservaRepository;

        @BeforeEach
        void setUp() {
            dbConnectionManager = new H2DBConnectionManager();
            vehiculoRepository = new VehiculoRepository();
            puestoRepository = new PuestoRepository();
            reservaRepository = new ReservaRepository();
            reservaAdService = new ReservaAdService();
        }

        @Test
        void testCrearReservaConHorasIncorrectas() {
            Reserva reserva = new Reserva();
            Assertions.assertThrows(ServiceException.class, () -> {
                reservaAdService.crearReserva("10:00", "08:00", "ABC123", "S", reserva);
            });
        }

        @Test
        void testCrearReservaConTamanioIncorrecto() {
            Reserva reserva = new Reserva();
            Assertions.assertThrows(ServiceException.class, () -> {
                reservaAdService.crearReserva("08:00", "10:00", "ABC123", "M", reserva);
            });
        }

        @Test
        void testEditarReserva() throws SQLException, ServiceException, RepositoryException {
            Reserva reservaSeleccionada = new Reserva();
            vehiculoRepository.agregarVehiculo(new Vehiculo("ABC123", 'm', 'm', 1));
            vehiculoRepository.agregarVehiculo(new Vehiculo("ABC456", 'm', 'm', 1));
            reservaSeleccionada.setHoraEntrada(LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0)));
            reservaSeleccionada.setHoraSalida(LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)));
            reservaSeleccionada.setPuesto(puestoRepository.buscarPuesto("m",false,new Puesto()));
            reservaSeleccionada.setVehiculo(vehiculoRepository.buscarVehiculo("ABC123", new Vehiculo()));
            reservaRepository.agregarReserva(reservaSeleccionada);

            reservaAdService.editarReserva("09:00", "11:00", "ABC456", "m", reservaSeleccionada);

            Assertions.assertEquals(LocalTime.of(9, 0), reservaSeleccionada.getHoraEntrada().toLocalTime());
            Assertions.assertEquals(LocalTime.of(11, 0), reservaSeleccionada.getHoraSalida().toLocalTime());
            Assertions.assertEquals("ABC456", reservaSeleccionada.getVehiculo().getPlaca());
            Assertions.assertEquals('m', reservaSeleccionada.getPuesto().getTamano());
        }

        @Test
        void testEditarReservaConHorasIncorrectas() {
            Reserva reservaSeleccionada = new Reserva();
            reservaSeleccionada.setId(548);
            reservaSeleccionada.setHoraEntrada(LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0)));
            reservaSeleccionada.setHoraSalida(LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)));
            reservaSeleccionada.setPuesto(new Puesto(548, 'm', true, 451));

            Assertions.assertThrows(ServiceException.class, () -> {
                reservaAdService.editarReserva("11:00", "09:00", "ABC123", "M", reservaSeleccionada);
            });
        }

        @Test
        void testEditarReservaConTamanioIncorrecto() {
            Reserva reservaSeleccionada = new Reserva();
            reservaSeleccionada.setId(548);
            reservaSeleccionada.setHoraEntrada(LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0)));
            reservaSeleccionada.setHoraSalida(LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)));
            reservaSeleccionada.setPuesto(new Puesto(548, 'm', true, 451));

            Assertions.assertThrows(ServiceException.class, () -> {
                reservaAdService.editarReserva("09:00", "11:00", "ABC123", "L", reservaSeleccionada);
            });
        }

        @Test
        void testEliminarReserva() throws SQLException {
            Reserva reservaSeleccionada = new Reserva();
            reservaSeleccionada.setId(548);
            reservaSeleccionada.setPuesto(new Puesto(548, 'm', true, 451));

            reservaAdService.eliminarReserva(reservaSeleccionada);

            // Verifica que la reserva se haya eliminado correctamente
            Assertions.assertNull(reservaRepository.buscarReservaPorId(548, reservaSeleccionada));
            // Verifica que el puesto se haya marcado como no disponible
            Puesto puesto = new Puesto();
            puestoRepository.buscarPuesto(reservaSeleccionada.getPuesto().getId(), puesto);
            Assertions.assertFalse(puesto.isDisponibilidad());
        }
    
}
