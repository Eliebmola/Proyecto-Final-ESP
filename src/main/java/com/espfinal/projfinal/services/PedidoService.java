package com.espfinal.projfinal.services;

import com.espfinal.projfinal.dto.pedido.DetalleRequestDTO;
import com.espfinal.projfinal.dto.pedido.PedidoRequestDTO;
import com.espfinal.projfinal.dto.pedido.PedidoResponseDTO;
import com.espfinal.projfinal.entities.Pedido;
import com.espfinal.projfinal.entities.Producto;
import com.espfinal.projfinal.entities.Usuario;
import com.espfinal.projfinal.exceptions.RecursoNoEncontradoException;
import com.espfinal.projfinal.exceptions.StockInsuficienteException;
import com.espfinal.projfinal.mappers.PedidoMapper;
import com.espfinal.projfinal.patterns.builder.PedidoBuilder;
import com.espfinal.projfinal.patterns.observer.LogEstadoObserver;
import com.espfinal.projfinal.patterns.observer.NotificadorEmailObserver;
import com.espfinal.projfinal.patterns.observer.PedidoSubject;
import com.espfinal.projfinal.patterns.payment.CreadorPago;
import com.espfinal.projfinal.patterns.payment.FabricadorPagos;
import com.espfinal.projfinal.repositories.PedidoRepository;
import com.espfinal.projfinal.repositories.ProductoRepository;
import com.espfinal.projfinal.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    private final FabricadorPagos fabricadorPagos;
    private final PedidoSubject pedidoSubject;

    public PedidoService(PedidoRepository pedidoRepository,
                         ProductoRepository productoRepository,
                         UsuarioRepository usuarioRepository) {
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;


        this.fabricadorPagos = new FabricadorPagos();


        this.pedidoSubject = new PedidoSubject();
        this.pedidoSubject.suscribir(new NotificadorEmailObserver());
        this.pedidoSubject.suscribir(new LogEstadoObserver());
    }

    @Transactional
    public PedidoResponseDTO crearPedido(PedidoRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Usuario no encontrado con id: " + dto.usuarioId()));


        PedidoBuilder builder = new PedidoBuilder()
                .conUsuario(usuario)
                .conTipoEnvio(dto.tipoEnvio())
                .conMetodoPago(dto.metodoPago());

        for (DetalleRequestDTO detalleDTO : dto.detalles()) {
            Producto producto = productoRepository.findById(detalleDTO.productoId())
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                            "Producto no encontrado con id: " + detalleDTO.productoId()));

            if (!producto.reducirStock(detalleDTO.cantidad())) {
                throw new StockInsuficienteException(
                        "Stock insuficiente para el producto: " + producto.getNombre());
            }
            productoRepository.save(producto); // persiste el nuevo stock

            builder.agregarProducto(producto, detalleDTO.cantidad());
        }

        Pedido pedido = builder.construir();
        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        return PedidoMapper.toResponseDTO(pedidoGuardado);
    }

    @Transactional
    public String procesarPago(Long pedidoId) {
        Pedido pedido = buscarOFallar(pedidoId);

        if (!pedido.getEstado().equals("PENDIENTE")) {
            throw new IllegalArgumentException(
                    "Solo se pueden procesar pagos de pedidos PENDIENTES. Estado actual: " + pedido.getEstado());
        }


        CreadorPago creador = fabricadorPagos.obtenerCreador(pedido.getMetodoPago());
        String resultadoPago = creador.procesarPago(pedido.getTotal());

        cambiarEstado(pedido, "PAGADO");

        return resultadoPago;
    }

    @Transactional
    public void cancelarPedido(Long pedidoId) {
        Pedido pedido = buscarOFallar(pedidoId);

        if (pedido.getEstado().equals("PAGADO")) {
            throw new IllegalArgumentException("No se puede cancelar un pedido ya pagado");
        }


        pedido.getDetalles().forEach(detalle -> {
            Producto producto = detalle.getProducto();
            producto.setStock(producto.getStock() + detalle.getCantidad());
            productoRepository.save(producto);
        });

        cambiarEstado(pedido, "CANCELADO");
    }

    public PedidoResponseDTO obtenerPorId(Long id) {
        return PedidoMapper.toResponseDTO(buscarOFallar(id));
    }

    public List<PedidoResponseDTO> listarPorUsuario(Long usuarioId) {
        return pedidoRepository.findByUsuarioId(usuarioId).stream()
                .map(PedidoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<PedidoResponseDTO> listarTodos() {
        return pedidoRepository.findAll().stream()
                .map(PedidoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }


    private void cambiarEstado(Pedido pedido, String nuevoEstado) {
        String estadoAnterior = pedido.getEstado();
        pedido.setEstado(nuevoEstado);
        pedidoRepository.save(pedido);


        pedidoSubject.notificarCambioEstado(pedido, estadoAnterior);
    }

    private Pedido buscarOFallar(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado con id: " + id));
    }
}