package com.pruebareservas.service;

import com.pruebareservas.dto.ReservaDTO;
import com.pruebareservas.entity.Reserva;
import com.pruebareservas.entity.Usuario;
import com.pruebareservas.entity.Espacio;
import com.pruebareservas.repository.ReservaRepository;
import com.pruebareservas.repository.UsuarioRepository;
import com.pruebareservas.repository.EspacioRepository;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@DisplayName("ReservaService - Pruebas Unitarias")
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EspacioRepository espacioRepository;

    @InjectMocks
    private ReservaService reservaService;

    private Usuario usuarioMuestra;
    private Espacio espacioMuestra;
    private Reserva reservaMuestra;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        usuarioMuestra = Usuario.builder()
                .id(1L)
                .email("test@example.com")
                .nombre("Juan")
                .apellido("Pérez")
                .autenticado(true)
                .build();

        espacioMuestra = Espacio.builder()
                .id(1L)
                .nombre("Sala de Juntas")
                .descripcion("Sala para reuniones")
                .ubicacion("Piso 2")
                .capacidad(10)
                .estado("DISPONIBLE")
                .build();

        LocalDateTime ahora = LocalDateTime.now();
        reservaMuestra = Reserva.builder()
                .id(1L)
                .usuario(usuarioMuestra)
                .espacio(espacioMuestra)
                .fechaInicio(ahora)
                .fechaFin(ahora.plusHours(1))
                .estado("CONFIRMADA")
                .build();
    }

    @Test
    @DisplayName("Debe crear una reserva exitosamente")
    void testCrearReserva() {
        // Arrange
        LocalDateTime inicio = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0);
        LocalDateTime fin = inicio.plusHours(1);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioMuestra));
        when(espacioRepository.findById(1L)).thenReturn(Optional.of(espacioMuestra));
        when(reservaRepository.existsConflicto(1L, inicio, fin)).thenReturn(false);
        when(reservaRepository.save(any(Reserva.class))).thenReturn(Reserva.builder()
                .id(1L)
                .usuario(usuarioMuestra)
                .espacio(espacioMuestra)
                .fechaInicio(inicio)
                .fechaFin(fin)
                .estado("CONFIRMADA")
                .build());

        // Act
        ReservaDTO resultado = reservaService.crearReserva(1L, 1L, inicio, fin);

        // Assert
        assertNotNull(resultado);
        assertEquals("CONFIRMADA", resultado.getEstado());
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }

    @Test
    @DisplayName("Debe fallar si el usuario no está autenticado")
    void testCrearReservaUsuarioNoAutenticado() {
        // Arrange
        usuarioMuestra.setAutenticado(false);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioMuestra));

        LocalDateTime inicio = LocalDateTime.now().withHour(10).withMinute(0);
        LocalDateTime fin = inicio.plusHours(1);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> reservaService.crearReserva(1L, 1L, inicio, fin));
    }

    @Test
    @DisplayName("Debe fallar si hay conflicto de horarios")
    void testCrearReservaConFlicto() {
        // Arrange
        LocalDateTime inicio = LocalDateTime.now().withHour(10).withMinute(0);
        LocalDateTime fin = inicio.plusHours(1);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioMuestra));
        when(espacioRepository.findById(1L)).thenReturn(Optional.of(espacioMuestra));
        when(reservaRepository.existsConflicto(1L, inicio, fin)).thenReturn(true);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> reservaService.crearReserva(1L, 1L, inicio, fin));
    }

    @Test
    @DisplayName("Debe obtener una reserva por ID")
    void testObtenerReserva() {
        // Arrange
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reservaMuestra));

        // Act
        ReservaDTO resultado = reservaService.obtenerReserva(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals("CONFIRMADA", resultado.getEstado());
    }

    @Test
    @DisplayName("Debe cancelar una reserva")
    void testCancelarReserva() {
        // Arrange
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reservaMuestra));
        when(reservaRepository.save(any(Reserva.class))).thenReturn(Reserva.builder()
                .id(1L)
                .usuario(usuarioMuestra)
                .espacio(espacioMuestra)
                .fechaInicio(reservaMuestra.getFechaInicio())
                .fechaFin(reservaMuestra.getFechaFin())
                .estado("CANCELADA")
                .build());

        // Act
        ReservaDTO resultado = reservaService.cancelarReserva(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals("CANCELADA", resultado.getEstado());
    }
}
