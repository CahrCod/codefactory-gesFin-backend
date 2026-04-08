package com.pruebareservas.service;

import com.pruebareservas.dto.UsuarioDTO;
import com.pruebareservas.entity.Usuario;
import com.pruebareservas.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("UsuarioService - Pruebas Unitarias")
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuarioMuestra;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuarioMuestra = Usuario.builder()
                .id(1L)
                .email("test@example.com")
                .nombre("Juan")
                .apellido("Pérez")
                .password("password123")
                .autenticado(false)
                .fechaRegistro(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Debe crear un usuario exitosamente")
    void testCrearUsuario() {
        // Arrange
        when(usuarioRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioMuestra);

        // Act
        UsuarioDTO resultado = usuarioService.crearUsuario("test@example.com", "Juan", "Pérez", "password123");

        // Assert
        assertNotNull(resultado);
        assertEquals("test@example.com", resultado.getEmail());
        assertEquals("Juan", resultado.getNombre());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Debe fallar al crear usuario con email duplicado")
    void testCrearUsuarioDuplicado() {
        // Arrange
        when(usuarioRepository.existsByEmail("test@example.com")).thenReturn(true);

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> usuarioService.crearUsuario("test@example.com", "Juan", "Pérez", "password123"));
    }

    @Test
    @DisplayName("Debe autenticar usuario con credenciales válidas")
    void testAutenticarUsuarioValido() {
        // Arrange
        usuarioMuestra.setAutenticado(true);
        when(usuarioRepository.findByEmail("test@example.com")).thenReturn(Optional.of(usuarioMuestra));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioMuestra);

        // Act
        UsuarioDTO resultado = usuarioService.autenticar("test@example.com", "password123");

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.getAutenticado());
    }

    @Test
    @DisplayName("Debe fallar al autenticar con contraseña inválida")
    void testAutenticarContraseñaInvalida() {
        // Arrange
        when(usuarioRepository.findByEmail("test@example.com")).thenReturn(Optional.of(usuarioMuestra));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> usuarioService.autenticar("test@example.com", "contraseñaInvalida"));
    }

    @Test
    @DisplayName("Debe obtener usuario por ID")
    void testObtenerUsuario() {
        // Arrange
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioMuestra));

        // Act
        UsuarioDTO resultado = usuarioService.obtenerUsuario(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals("test@example.com", resultado.getEmail());
    }

    @Test
    @DisplayName("Debe fallar al obtener usuario inexistente")
    void testObtenerUsuarioInexistente() {
        // Arrange
        when(usuarioRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> usuarioService.obtenerUsuario(999L));
    }
}
