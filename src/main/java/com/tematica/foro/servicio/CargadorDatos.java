package com.tematica.foro.servicio;

import com.tematica.foro.dominio.Rol;
import com.tematica.foro.dominio.Topico;
import com.tematica.foro.dominio.Usuario;
import com.tematica.foro.dominio.repositorio.TopicoRepositorio;
import com.tematica.foro.dominio.repositorio.UsuarioRepositorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;

/** Carga datos iniciales (usuario admin y tópicos de ejemplo). */
@Configuration
public class CargadorDatos {
    @Bean
    CommandLineRunner init(UsuarioRepositorio usuarios, TopicoRepositorio topicos, PasswordEncoder encoder) {
        return args -> {
            if (usuarios.findByNombreUsuario("admin").isEmpty()) {
                usuarios.save(Usuario.builder()
                        .nombreUsuario("admin")
                        .contrasena(encoder.encode("123456"))
                        .rol(Rol.ADMIN)
                        .build());
            }
            if (topicos.count() == 0) {
                topicos.save(Topico.builder().titulo("Bienvenida").mensaje("¡Hola foro!")
                        .autor("admin").creadoEn(Instant.now()).build());
                topicos.save(Topico.builder().titulo("Reglas").mensaje("Sé respetuoso y comparte conocimiento.")
                        .autor("admin").creadoEn(Instant.now()).build());
            }
        };
    }
}
