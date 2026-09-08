package com.espfinal.projfinal.patterns.payment;

public class PagoPayPal implements MetodoPago {
    @Override
    public String procesarPago(Double monto){
        return "Pago de $" + monto +" procesando exitosamente via PayPal";
    }

    @Override
    public String getTipo() {
        return "PAYPAL";
    }
}
