package com.espfinal.projfinal.dto.pedido;

public record DetalleResponseDTO(
        String productoNombre,
        Integer cantidad,
        Double precioUnitario,
        Double subtotal
) {}