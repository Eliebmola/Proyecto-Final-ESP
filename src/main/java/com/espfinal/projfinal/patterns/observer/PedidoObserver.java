package com.espfinal.projfinal.patterns.observer;

import com.espfinal.projfinal.entities.Pedido;

public interface PedidoObserver {
    void actualizar(Pedido pedido, String estadoAnterior);
}
