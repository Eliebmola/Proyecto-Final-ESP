package com.espfinal.projfinal.patterns.singleton;

public class ConfiguracionTienda {
    private static ConfiguracionTienda instancia;

    private double costoEnvioNacional;
    private double costoEnvioExpress;
    private double impuestoIva;

    private ConfiguracionTienda() {
        this.costoEnvioNacional = 5000.0;
        this.costoEnvioExpress = 12000.0;
        this.impuestoIva = 0.19;
    }

    public static synchronized ConfiguracionTienda getInstancia() {
        if (instancia == null) {
            instancia = new ConfiguracionTienda();
        }
        return instancia;
    }

    public double getCostoEnvioNacional() {
        return costoEnvioNacional;
    }

    public double getCostoEnvioExpress() {
        return costoEnvioExpress;
    }

    public double getImpuestoIva() {
        return impuestoIva;
    }

    public void actualizarCostoExpress(double nuevoCosto) {
        this.costoEnvioExpress = nuevoCosto;
    }
}
