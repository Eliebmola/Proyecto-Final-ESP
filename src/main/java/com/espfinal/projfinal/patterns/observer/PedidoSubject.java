package com.espfinal.projfinal.patterns.observer;

import com.espfinal.projfinal.entities.Pedido;

import java.util.ArrayList;
import java.util.List;

public class PedidoSubject {
    private List<PedidoObserver> observadores = new ArrayList<>();

    public void suscribir(PedidoObserver observer) {
        observadores.add(observer);
    }

    public void desuscribir(PedidoObserver observer) {
        observadores.remove(observer);
    }

    public void notificarCambioEstado(Pedido pedido, String estadoAnterior) {
        for (PedidoObserver observer : observadores) {
            observer.actualizar(pedido, estadoAnterior);
        }
    }
}
