package com.espfinal.projfinal.dto.pedido;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DetalleRequestDTO(
        @NotNull(message = "El producto es obligatorio")
        Long productoId,

        @NotNull @Positive(message = "La cantidad debe ser mayor a 0")
        Integer cantidad
) {}
