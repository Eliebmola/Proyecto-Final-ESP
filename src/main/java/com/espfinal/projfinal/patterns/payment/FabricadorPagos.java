package com.espfinal.projfinal.patterns.payment;

import java.util.HashMap;
import java.util.Map;

public class FabricadorPagos {

    private final Map<String, CreadorPago> creadores;

    public FabricadorPagos() {
        this.creadores = new HashMap<>();
        registrarCreador("TARJETA", new CreadorPagoTarjeta());
        registrarCreador("PSE", new CreadorPagoPSE());
        registrarCreador("PAYPAL", new CreadorPagoPayPal());
    }

    public void registrarCreador(String tipo, CreadorPago creador) {
        creadores.put(tipo.toUpperCase(), creador);
    }

    public CreadorPago obtenerCreador(String tipo) {
        CreadorPago creador = creadores.get(tipo.toUpperCase());
        if (creador == null) {
            throw new IllegalArgumentException("Método de pago no soportado: " + tipo);
        }
        return creador;
    }
}