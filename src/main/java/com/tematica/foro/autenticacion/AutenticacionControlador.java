package com.tematica.foro.autenticacion;

import com.tematica.foro.servicio.JwtServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/** Endpoints de autenticación. */
@RestController
@RequestMapping("/auth")
public class AutenticacionControlador {
    private final AuthenticationManager authenticationManager;
    private final JwtServicio jwtServicio;

    public AutenticacionControlador(AuthenticationManager authenticationManager, JwtServicio jwtServicio) {
        this.authenticationManager = authenticationManager;
        this.jwtServicio = jwtServicio;
    }

    @PostMapping("/login")
    public ResponseEntity<RespuestaAutenticacion> login(@RequestBody @Validated PeticionAutenticacion req) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.nombreUsuario(), req.contrasena()));
        String token = jwtServicio.generarToken(auth.getName());
        return ResponseEntity.ok(new RespuestaAutenticacion(token));
    }
}
