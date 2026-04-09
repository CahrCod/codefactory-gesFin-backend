package com.pruebareservas.controller;

import com.pruebareservas.dto.ApiResponseDTO;
import com.pruebareservas.dto.UsuarioDTO;
import com.pruebareservas.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<ApiResponseDTO<UsuarioDTO>> registrarUsuario(@RequestBody Map<String, String> request) {
        try {
            UsuarioDTO usuario = usuarioService.crearUsuario(
                    request.get("email"),
                    request.get("nombre"),
                    request.get("apellido"),
                    request.get("password"));

            ApiResponseDTO<UsuarioDTO> response = ApiResponseDTO.<UsuarioDTO>builder()
                    .statusCode(201)
                    .message("Usuario registrado exitosamente")
                    .data(usuario)
                    .success(true)
                    .build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            ApiResponseDTO<UsuarioDTO> response = ApiResponseDTO.<UsuarioDTO>builder()
                    .statusCode(400)
                    .message(e.getMessage())
                    .success(false)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/autenticar")
    public ResponseEntity<ApiResponseDTO<UsuarioDTO>> autenticar(@RequestBody Map<String, String> request) {
        try {
            UsuarioDTO usuario = usuarioService.autenticar(
                    request.get("email"),
                    request.get("password"));

            ApiResponseDTO<UsuarioDTO> response = ApiResponseDTO.<UsuarioDTO>builder()
                    .statusCode(200)
                    .message("Autenticación exitosa")
                    .data(usuario)
                    .success(true)
                    .build();

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ApiResponseDTO<UsuarioDTO> response = ApiResponseDTO.<UsuarioDTO>builder()
                    .statusCode(401)
                    .message(e.getMessage())
                    .success(false)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<UsuarioDTO>> obtenerUsuario(@PathVariable Long id) {
        try {
            UsuarioDTO usuario = usuarioService.obtenerUsuario(id);

            ApiResponseDTO<UsuarioDTO> response = ApiResponseDTO.<UsuarioDTO>builder()
                    .statusCode(200)
                    .message("Usuario obtenido exitosamente")
                    .data(usuario)
                    .success(true)
                    .build();

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ApiResponseDTO<UsuarioDTO> response = ApiResponseDTO.<UsuarioDTO>builder()
                    .statusCode(404)
                    .message(e.getMessage())
                    .success(false)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<UsuarioDTO>>> listarUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.listarTodos();

        ApiResponseDTO<List<UsuarioDTO>> response = ApiResponseDTO.<List<UsuarioDTO>>builder()
                .statusCode(200)
                .message("Usuarios obtenidos exitosamente")
                .data(usuarios)
                .success(true)
                .build();

        return ResponseEntity.ok(response);
    }
}
