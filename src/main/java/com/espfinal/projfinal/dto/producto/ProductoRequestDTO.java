package com.espfinal.projfinal.dto.producto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductoRequestDTO(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        String descripcion,

        @NotNull @Positive(message = "El precio debe ser mayor a 0")
        Double precio,

        @NotNull @PositiveOrZero(message = "El stock no puede ser negativo")
        Integer stock
) {}
