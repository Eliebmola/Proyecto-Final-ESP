package com.espfinal.projfinal.patterns.payment;

public class CreadorPagoPayPal extends CreadorPago {
    @Override
    protected MetodoPago crearMetodoPago() {
        return new PagoPayPal();
    }
}