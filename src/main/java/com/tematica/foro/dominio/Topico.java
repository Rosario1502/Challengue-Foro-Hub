package com.tematica.foro.dominio;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

/** Entidad Tópico para el foro. */
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "topicos")
public class Topico {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, length = 4000)
    private String mensaje;

    @Column(nullable = false)
    private String autor;

    @Column(nullable = false)
    private Instant creadoEn;
}
