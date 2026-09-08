package com.espfinal.projfinal.patterns.observer;

import com.espfinal.projfinal.entities.Pedido;

public class NotificadorEmailObserver implements PedidoObserver {
    @Override
    public void actualizar(Pedido pedido, String estadoAnterior) {
        System.out.println("Enviando correo a " + pedido.getUsuario().getEmail() +
                ": tu pedido #" + pedido.getId() + " cambió de " + estadoAnterior +
                " a " + pedido.getEstado());
    }
}
