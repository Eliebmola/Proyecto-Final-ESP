package com.espfinal.projfinal.patterns.payment;

public class PagoPSE implements MetodoPago {
    @Override
    public String procesarPago(Double monto){
        return "Pago de $" + monto +" procesando exitosamente via PSE";
    }

    @Override
    public String getTipo() {
        return "PSE";
    }
}
