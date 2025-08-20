package com.tematica.foro.autenticacion;

import jakarta.validation.constraints.NotBlank;

/** Cuerpo de login (nombreUsuario y contrasena). */
public record PeticionAutenticacion(
        @NotBlank String nombreUsuario,
        @NotBlank String contrasena
) {}
