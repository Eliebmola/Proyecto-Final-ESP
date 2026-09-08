package com.espfinal.projfinal.dto.usuario;

import java.time.LocalDateTime;

public record UsuarioResponseDTO(
        Long id,
        String nombre,
        String email,
        String rol,
        String direccion,
        LocalDateTime fechaCreacion
) {}
