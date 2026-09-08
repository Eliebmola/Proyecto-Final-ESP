package com.espfinal.projfinal.dto.pedido;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO(
        Long id,
        String usuarioNombre,
        Double costoEnvio,
        Double total,
        String estado,
        String metodoPago,
        String tipoEnvio,
        LocalDateTime fechaPedido,
        List<DetalleResponseDTO> detalles
) {}
