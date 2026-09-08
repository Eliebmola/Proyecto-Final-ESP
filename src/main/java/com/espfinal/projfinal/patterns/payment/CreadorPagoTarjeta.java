package com.espfinal.projfinal.patterns.payment;

public class CreadorPagoTarjeta extends CreadorPago {
    @Override
    protected MetodoPago crearMetodoPago() {
        return new PagoTarjeta();
    }
}