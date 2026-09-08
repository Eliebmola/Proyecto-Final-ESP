package com.espfinal.projfinal.dto.pedido;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoRequestDTO(
        @NotNull(message = "El usuario es obligatorio")
        Long usuarioId,

        @NotBlank(message = "El tipo de envío es obligatorio")
        String tipoEnvio, // "NACIONAL" o "EXPRESS"

        @NotBlank(message = "El método de pago es obligatorio")
        String metodoPago, // "TARJETA", "PSE", "PAYPAL"

        @NotEmpty(message = "El pedido debe tener al menos un producto")
        @Valid
        List<DetalleRequestDTO> detalles
) {}