package com.pruebareservas.config;

import com.pruebareservas.entity.Usuario;
import com.pruebareservas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            Usuario usuario1 = Usuario.builder()
                    .email("juan.perez@example.com")
                    .nombre("Juan")
                    .apellido("Pérez")
                    .password("password123")
                    .autenticado(false)
                    .build();

            Usuario usuario2 = Usuario.builder()
                    .email("maria.garcia@example.com")
                    .nombre("María")
                    .apellido("García")
                    .password("password456")
                    .autenticado(false)
                    .build();

            Usuario usuario3 = Usuario.builder()
                    .email("carlos.lopez@example.com")
                    .nombre("Carlos")
                    .apellido("López")
                    .password("password789")
                    .autenticado(false)
                    .build();

            usuarioRepository.save(usuario1);
            usuarioRepository.save(usuario2);
            usuarioRepository.save(usuario3);
            System.out.println("✓ Usuarios de muestra creados");
        }
        System.out.println("✓ Base de datos inicializada");
    }
}
