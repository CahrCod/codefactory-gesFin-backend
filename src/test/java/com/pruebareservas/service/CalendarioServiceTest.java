package com.pruebareservas.service;

import com.pruebareservas.dto.CalendarioDTO;
import com.pruebareservas.dto.FranjasDTO;
import com.pruebareservas.entity.Reserva;
import com.pruebareservas.entity.Usuario;
import com.pruebareservas.entity.Espacio;
import com.pruebareservas.repository.ReservaRepository;
import com.pruebareservas.repository.EspacioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@DisplayName("CalendarioService - Pruebas Unitarias")
class CalendarioServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private EspacioRepository espacioRepository;

    @InjectMocks
    private CalendarioService calendarioService;

    private Espacio espacioMuestra;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        espacioMuestra = Espacio.builder()
                .id(1L)
                .nombre("Sala de Juntas")
                .descripcion("Sala para reuniones")
                .ubicacion("Piso 2")
                .capacidad(10)
                .estado("DISPONIBLE")
                .build();
    }

    @Test
    @DisplayName("Debe obtener calendario del día con franjas disponibles")
    void testObtenerCalendarioDiaDisponible() {
        // Arrange
        LocalDate fecha = LocalDate.now();
        when(espacioRepository.findById(1L)).thenReturn(Optional.of(espacioMuestra));
        when(reservaRepository.findReservasByEspacioAndFecha(
                anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(new ArrayList<>());

        // Act
        CalendarioDTO resultado = calendarioService.obtenerCalendarioDia(1L, fecha);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getEspacioId());
        assertEquals("Sala de Juntas", resultado.getEspacioNombre());
        assertTrue(resultado.getTieneDisponibilidad());
        assertFalse(resultado.getFranjas().isEmpty());
    }

    @Test
    @DisplayName("Debe obtener calendario del día con horarios ocupados")
    void testObtenerCalendarioDiaOcupado() {
        // Arrange
        LocalDate fecha = LocalDate.now();
        Usuario usuario = Usuario.builder().id(1L).nombre("Juan").apellido("Pérez").build();

        LocalDateTime inicio = fecha.atTime(10, 0);
        LocalDateTime fin = fecha.atTime(11, 0);

        Reserva reserva = Reserva.builder()
                .id(1L)
                .usuario(usuario)
                .espacio(espacioMuestra)
                .fechaInicio(inicio)
                .fechaFin(fin)
                .estado("CONFIRMADA")
                .build();

        List<Reserva> reservas = new ArrayList<>();
        reservas.add(reserva);

        when(espacioRepository.findById(1L)).thenReturn(Optional.of(espacioMuestra));
        when(reservaRepository.findReservasByEspacioAndFecha(
                anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(reservas);

        // Act
        CalendarioDTO resultado = calendarioService.obtenerCalendarioDia(1L, fecha);

        // Assert
        assertNotNull(resultado);
        assertFalse(resultado.getFranjas().isEmpty());

        boolean tieneOcupado = resultado.getFranjas().stream()
                .anyMatch(f -> "OCUPADO".equals(f.getEstado()));
        assertTrue(tieneOcupado, "Debe tener al menos una franja ocupada");
    }

    @Test
    @DisplayName("Debe obtener calendario de la semana")
    void testObtenerCalendarioSemana() {
        // Arrange
        LocalDate fechaInicio = LocalDate.now();
        when(espacioRepository.findById(1L)).thenReturn(Optional.of(espacioMuestra));
        when(reservaRepository.findReservasByEspacioAndFecha(
                anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(new ArrayList<>());

        // Act
        CalendarioDTO resultado = calendarioService.obtenerCalendarioSemana(1L, fechaInicio);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getEspacioId());
        assertTrue(resultado.getTieneDisponibilidad());
    }

    @Test
    @DisplayName("Debe fallar si el espacio no existe")
    void testObtenerCalendarioEspacioInexistente() {
        // Arrange
        LocalDate fecha = LocalDate.now();
        when(espacioRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> calendarioService.obtenerCalendarioDia(999L, fecha));
    }

    @Test
    @DisplayName("Debe contar correctamente franjas disponibles y ocupadas")
    void testContarFranjasDisponiblesYOcupadas() {
        // Arrange
        LocalDate fecha = LocalDate.now();
        Usuario usuario = Usuario.builder().id(1L).nombre("Juan").apellido("Pérez").build();

        LocalDateTime inicio = fecha.atTime(14, 0);
        LocalDateTime fin = fecha.atTime(15, 0);

        Reserva reserva = Reserva.builder()
                .id(1L)
                .usuario(usuario)
                .espacio(espacioMuestra)
                .fechaInicio(inicio)
                .fechaFin(fin)
                .estado("CONFIRMADA")
                .build();

        when(espacioRepository.findById(1L)).thenReturn(Optional.of(espacioMuestra));
        when(reservaRepository.findReservasByEspacioAndFecha(
                anyLong(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(reserva));

        // Act
        CalendarioDTO resultado = calendarioService.obtenerCalendarioDia(1L, fecha);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.getTotalDisponibles() >= 0);
        assertTrue(resultado.getTotalOcupados() >= 1);
    }
}
