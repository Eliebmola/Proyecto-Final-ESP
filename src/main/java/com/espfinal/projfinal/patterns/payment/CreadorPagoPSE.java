package com.espfinal.projfinal.patterns.payment;

public class CreadorPagoPSE extends CreadorPago {
    @Override
    protected MetodoPago crearMetodoPago() {
        return new PagoPSE();
    }
}