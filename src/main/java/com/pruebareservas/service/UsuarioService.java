package com.pruebareservas.service;

import com.pruebareservas.entity.Usuario;
import com.pruebareservas.dto.UsuarioDTO;
import com.pruebareservas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioDTO crearUsuario(String email, String nombre, String apellido, String password) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new RuntimeException("El usuario con email " + email + " ya existe");
        }

        Usuario usuario = Usuario.builder()
                .email(email)
                .nombre(nombre)
                .apellido(apellido)
                .password(password)
                .autenticado(false)
                .build();

        Usuario guardado = usuarioRepository.save(usuario);
        return convertToDTO(guardado);
    }

    public UsuarioDTO autenticar(String email, String password) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + email));

        if (!usuario.getPassword().equals(password)) {
            throw new RuntimeException("Contraseña inválida");
        }

        usuario.setAutenticado(true);
        usuarioRepository.save(usuario);
        return convertToDTO(usuario);
    }

    public UsuarioDTO obtenerUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + id));
        return convertToDTO(usuario);
    }

    public UsuarioDTO obtenerPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + email));
        return convertToDTO(usuario);
    }

    public List<UsuarioDTO> listarTodos() {
        return usuarioRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    private UsuarioDTO convertToDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .email(usuario.getEmail())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .autenticado(usuario.getAutenticado())
                .fechaRegistro(usuario.getFechaRegistro())
                .build();
    }
}
