package com.asesinosdesoftware.javeparking.controller;

import com.asesinosdesoftware.javeparking.entities.Pago;
import com.asesinosdesoftware.javeparking.repository.PagoRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class PagoController {

    private Pago pago; // Objeto Pago asociado a esta instancia

    // Constructor vacío
    public PagoController() {
        this.pago = new Pago(0, null, null, Pago.TipoPago.RESERVA); // Puedes inicializar con un tipo predeterminado
    }

    // Método para establecer un pago
    public void setPago(Pago pago) {
        this.pago = pago; // Establece el pago actual
    }

    // Método para registrar un pago
    public void registrarPago(Connection connection, Pago pago) {
        try {
            if (pago.getTipoPago() == Pago.TipoPago.RESERVA && pago.getReserva() != null) {
                PagoRepository.agregarPagoReserva(connection, pago);
                System.out.println("Pago de reserva registrado exitosamente.");
            } else if (pago.getTipoPago() == Pago.TipoPago.SUSCRIPCION && pago.getSuscripcion() != null) {
                PagoRepository.agregarPagoSuscripcion(connection, pago);
                System.out.println("Pago de suscripción registrado exitosamente.");
            } else {
                throw new IllegalArgumentException("El pago debe estar asociado a una reserva o una suscripción.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("Error al registrar el pago.");
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }

    // Método para mostrar información sobre el pago
    public void mostrarInfoPago() {
        if (pago != null) {
            System.out.println("Detalles del pago:");
            System.out.println("ID: " + pago.getId());
            System.out.println("Valor: " + pago.getValor());
            System.out.println("Fecha de pago: " + pago.getFechaPago());
            if (pago.getReserva() != null) {
                System.out.println("Reserva asociada: " + pago.getReserva().getId());
            } else if (pago.getSuscripcion() != null) {
                System.out.println("Suscripción asociada: " + pago.getSuscripcion().getId());
            }
        } else {
            System.out.println("No hay información de pago disponible.");
        }
    }

    // Método para mostrar errores (puedes personalizar este método)
    private void showError(String message) {
        System.err.println(message);
    }
}

