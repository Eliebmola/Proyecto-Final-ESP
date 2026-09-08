package com.espfinal.projfinal.patterns.payment;

public interface MetodoPago {
    String procesarPago(Double monto);
    String getTipo();
}
