package com.espfinal.projfinal.repositories;

import com.espfinal.projfinal.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    List<Producto> findByStockGreaterThan(Integer stock);
}