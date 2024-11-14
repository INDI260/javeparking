package com.asesinosdesoftware.javeparking.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class Pago {

    private int id; // Este dato lo crea automáticamente el manejador de bases de datos
    private BigDecimal valor; // El valor monetario que debe recibirse en el pago
    private LocalDateTime fecha;//Este atributo guarda la fecha y la hora de entrada del vehículo en la reserva.
    private String metodoPago;
}
