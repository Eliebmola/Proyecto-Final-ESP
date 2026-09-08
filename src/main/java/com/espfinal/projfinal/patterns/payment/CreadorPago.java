package com.espfinal.projfinal.patterns.payment;

public abstract class CreadorPago {

    protected abstract MetodoPago crearMetodoPago();

    public String procesarPago(Double monto) {
        MetodoPago metodo = crearMetodoPago();
        return metodo.procesarPago(monto);
    }

    public String getTipoPago() {
        return crearMetodoPago().getTipo();
    }
}