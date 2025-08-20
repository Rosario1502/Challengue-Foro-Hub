package com.tematica.foro.configuracion;

import com.tematica.foro.dominio.Usuario;
import com.tematica.foro.dominio.repositorio.UsuarioRepositorio;
import com.tematica.foro.servicio.JwtServicio;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/** Filtro que autentica vía JWT si hay token en el encabezado Authorization. */
@Component
public class FiltroJwtAutenticacion extends OncePerRequestFilter {
    private final JwtServicio jwtServicio;
    private final UsuarioRepositorio usuarioRepositorio;

    public FiltroJwtAutenticacion(JwtServicio jwtServicio, UsuarioRepositorio usuarioRepositorio) {
        this.jwtServicio = jwtServicio;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                String nombreUsuario = jwtServicio.extraerNombreUsuario(token);
                if (nombreUsuario != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    Usuario usuario = usuarioRepositorio.findByNombreUsuario(nombreUsuario).orElse(null);
                    if (usuario != null) {
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            } catch (Exception ignored) { }
        }
        chain.doFilter(request, response);
    }
}
