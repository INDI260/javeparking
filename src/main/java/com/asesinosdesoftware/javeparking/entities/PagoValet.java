package com.asesinosdesoftware.javeparking.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PagoValet {
    private int id; // Este dato lo crea automáticamente el manejador de bases de datos
    //private Valet valet; // Valet asociado al pago (Todavia no esta implementado)
    private BigDecimal valor; // El valor monetario que debe recibirse en el pago
    private LocalDateTime fechaPago;//Este atributo guarda la fecha y la hora de entrada del vehiculo en la reserva.
    private String metodoPago;
}