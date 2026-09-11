package com.espfinal.projfinal.patterns.builder;

import com.espfinal.projfinal.entities.DetallePedido;
import com.espfinal.projfinal.entities.Pedido;
import com.espfinal.projfinal.entities.Producto;
import com.espfinal.projfinal.entities.Usuario;
import com.espfinal.projfinal.patterns.singleton.ConfiguracionTienda;

public class PedidoBuilder {
    private Pedido pedido;

    public PedidoBuilder() {
        this.pedido = new Pedido();
    }

    public PedidoBuilder conUsuario(Usuario usuario) {
        pedido.setUsuario(usuario);
        return this;
    }

    public PedidoBuilder conTipoEnvio(String tipoEnvio) {
        pedido.setTipoEnvio(tipoEnvio);

        ConfiguracionTienda config =
                ConfiguracionTienda.getInstancia();

        double costo;

        if (tipoEnvio.equalsIgnoreCase("EXPRESS")) {
            costo = config.getCostoEnvioExpress();
        } else {
            costo = config.getCostoEnvioNacional();
        }

        pedido.setCostoEnvio(costo);

        return this;
    }

    public PedidoBuilder conMetodoPago(String metodoPago) {
        pedido.setMetodoPago(metodoPago);
        return this;
    }

    public PedidoBuilder agregarProducto(Producto producto, Integer cantidad) {
        DetallePedido detalle = new DetallePedido(pedido, producto, cantidad, producto.getPrecio());
        pedido.agregarDetalle(detalle);
        return this;
    }

    public Pedido construir() {
        if (pedido.getUsuario() == null) {
            throw new IllegalStateException("El pedido debe tener un usuario asignado");
        }
        if (pedido.getDetalles().isEmpty()) {
            throw new IllegalStateException("El pedido debe tener al menos un producto");
        }
        if (pedido.getTipoEnvio() == null) {
            throw new IllegalStateException("El pedido debe tener un tipo de envío");
        }

        double subtotalProductos = pedido.getDetalles().stream()
                .mapToDouble(DetallePedido::getSubtotal)
                .sum();

        pedido.setTotal(subtotalProductos + pedido.getCostoEnvio());

        return pedido;
    }
}
