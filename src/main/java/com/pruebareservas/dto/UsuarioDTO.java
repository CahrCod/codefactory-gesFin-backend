package com.pruebareservas.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {
    private Long id;
    private String email;
    private String nombre;
    private String apellido;
    private Boolean autenticado;
    private LocalDateTime fechaRegistro;
}
