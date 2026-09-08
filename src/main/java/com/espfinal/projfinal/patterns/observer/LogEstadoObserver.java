package com.espfinal.projfinal.patterns.observer;

import com.espfinal.projfinal.entities.Pedido;

public class LogEstadoObserver implements PedidoObserver{
    @Override
    public void actualizar(Pedido pedido, String estadoAnterior) {
        System.out.println("[LOG] Pedido #" + pedido.getId() + ": " +
                estadoAnterior + " -> " + pedido.getEstado() +
                " (" + java.time.LocalDateTime.now() + ")");
    }
}
