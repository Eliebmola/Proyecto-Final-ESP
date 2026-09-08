package com.espfinal.projfinal.mappers;

import com.espfinal.projfinal.dto.producto.ProductoResponseDTO;
import com.espfinal.projfinal.entities.Producto;

public class ProductoMapper {

    public static ProductoResponseDTO toResponseDTO(Producto producto) {
        return new ProductoResponseDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock()
        );
    }
}