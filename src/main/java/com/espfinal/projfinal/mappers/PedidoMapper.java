package com.espfinal.projfinal.mappers;

import com.espfinal.projfinal.dto.pedido.DetalleResponseDTO;
import com.espfinal.projfinal.dto.pedido.PedidoResponseDTO;
import com.espfinal.projfinal.entities.DetallePedido;
import com.espfinal.projfinal.entities.Pedido;

import java.util.List;
import java.util.stream.Collectors;

public class PedidoMapper {

    public static PedidoResponseDTO toResponseDTO(Pedido pedido) {
        List<DetalleResponseDTO> detalles = pedido.getDetalles().stream()
                .map(PedidoMapper::toDetalleResponseDTO)
                .collect(Collectors.toList());

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getUsuario().getNombre(),
                pedido.getCostoEnvio(),
                pedido.getTotal(),
                pedido.getEstado(),
                pedido.getMetodoPago(),
                pedido.getTipoEnvio(),
                pedido.getFechaPedido(),
                detalles
        );
    }

    private static DetalleResponseDTO toDetalleResponseDTO(DetallePedido detalle) {
        return new DetalleResponseDTO(
                detalle.getProducto().getNombre(),
                detalle.getCantidad(),
                detalle.getPrecioUnitario(),
                detalle.getSubtotal()
        );
    }
}
