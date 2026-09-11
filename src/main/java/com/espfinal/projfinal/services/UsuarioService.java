package com.espfinal.projfinal.services;

import com.espfinal.projfinal.dto.usuario.UsuarioRequestDTO;
import com.espfinal.projfinal.dto.usuario.UsuarioResponseDTO;
import com.espfinal.projfinal.entities.Usuario;
import com.espfinal.projfinal.exceptions.RecursoNoEncontradoException;
import com.espfinal.projfinal.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioResponseDTO crear(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        }

        Usuario usuario = new Usuario(dto.nombre(), dto.email(), dto.password(), dto.rol());
        usuario.setDireccion(dto.direccion());

        Usuario guardado = usuarioRepository.save(usuario);
        return toResponseDTO(guardado);
    }

    public UsuarioResponseDTO obtenerPorId(Long id) {
        Usuario usuario = buscarOFallar(id);
        return toResponseDTO(usuario);
    }

    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO actualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = buscarOFallar(id);
        usuario.setNombre(dto.nombre());
        usuario.setEmail(dto.email());
        usuario.setDireccion(dto.direccion());
        // La contraseña normalmente se actualiza en un endpoint aparte, no aquí

        Usuario actualizado = usuarioRepository.save(usuario);
        return toResponseDTO(actualizado);
    }

    public void eliminar(Long id) {
        Usuario usuario = buscarOFallar(id);
        usuarioRepository.delete(usuario);
    }

    private Usuario buscarOFallar(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + id));
    }

    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRol(),
                usuario.getDireccion(),
                usuario.getFechaCreacion()
        );
    }
}