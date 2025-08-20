package com.tematica.foro.dominio.dto;

import jakarta.validation.constraints.NotBlank;

/** Cuerpo de la solicitud para crear tópicos. */
public record PeticionCrearTopico(
        @NotBlank String titulo,
        @NotBlank String mensaje
) {}
