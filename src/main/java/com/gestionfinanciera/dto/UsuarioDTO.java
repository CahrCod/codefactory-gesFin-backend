package com.gestionfinanciera.dto;

import lombok.*;

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
}
