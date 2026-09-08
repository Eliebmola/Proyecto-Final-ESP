package com.espfinal.projfinal.services;

import com.espfinal.projfinal.dto.producto.ProductoRequestDTO;
import com.espfinal.projfinal.dto.producto.ProductoResponseDTO;
import com.espfinal.projfinal.entities.Producto;
import com.espfinal.projfinal.exceptions.RecursoNoEncontradoException;
import com.espfinal.projfinal.mappers.ProductoMapper;
import com.espfinal.projfinal.repositories.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public ProductoResponseDTO crear(ProductoRequestDTO dto) {
        Producto producto = new Producto(dto.nombre(), dto.descripcion(), dto.precio(), dto.stock());
        Producto guardado = productoRepository.save(producto);
        return ProductoMapper.toResponseDTO(guardado);
    }

    public ProductoResponseDTO obtenerPorId(Long id) {
        return ProductoMapper.toResponseDTO(buscarOFallar(id));
    }

    public List<ProductoResponseDTO> listarTodos() {
        return productoRepository.findAll().stream()
                .map(ProductoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<ProductoResponseDTO> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(ProductoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ProductoResponseDTO actualizar(Long id, ProductoRequestDTO dto) {
        Producto producto = buscarOFallar(id);
        producto.setNombre(dto.nombre());
        producto.setDescripcion(dto.descripcion());
        producto.setPrecio(dto.precio());
        producto.setStock(dto.stock());

        Producto actualizado = productoRepository.save(producto);
        return ProductoMapper.toResponseDTO(actualizado);
    }

    public void eliminar(Long id) {
        Producto producto = buscarOFallar(id);
        productoRepository.delete(producto);
    }

    private Producto buscarOFallar(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con id: " + id));
    }
}
