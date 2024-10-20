package com.asesinosdesoftware.javeparking.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class pagoSuscripcion {

    private int id; // Este dato lo crea automáticamente el manejador de bases de datos
    private Suscripcion suscripcion; // Suscripcion asociada al pago
    private BigDecimal valor; // El valor monetario que debe recibirse en el pago
    private LocalDateTime fechaPago;//Este atributo guarda la fecha y la hora de entrada del vehiculo en la reserva.
    private String metodoPago;


}
