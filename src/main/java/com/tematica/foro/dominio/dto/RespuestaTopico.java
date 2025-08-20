package com.tematica.foro.dominio.dto;

import java.time.Instant;

/** Respuesta estándar de un tópico. */
public record RespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        String autor,
        Instant creadoEn
) {}
