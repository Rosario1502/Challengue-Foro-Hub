package com.tematica.foro.dominio.dto;

import jakarta.validation.constraints.NotBlank;

/** Cuerpo de la solicitud para actualizar tópicos. */
public record PeticionActualizarTopico(
        @NotBlank String titulo,
        @NotBlank String mensaje
) {}
