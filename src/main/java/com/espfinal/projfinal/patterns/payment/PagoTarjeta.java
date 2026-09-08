package com.espfinal.projfinal.patterns.payment;

public class PagoTarjeta implements MetodoPago {
    @Override
    public String procesarPago(Double monto){
        return "Pago de $" + monto + " procesado exitosamente con Tarjeta de Crédito";
    }

    @Override
    public String getTipo() {
        return "TARJETA";
    }

}
