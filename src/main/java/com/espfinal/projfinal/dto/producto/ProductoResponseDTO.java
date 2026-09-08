package com.espfinal.projfinal.dto.producto;

public record ProductoResponseDTO(
        Long id,
        String nombre,
        String descripcion,
        Double precio,
        Integer stock
) {}
