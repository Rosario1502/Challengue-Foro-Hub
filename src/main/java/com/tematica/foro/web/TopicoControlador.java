package com.tematica.foro.web;

import com.tematica.foro.dominio.Topico;
import com.tematica.foro.dominio.dto.*;
import com.tematica.foro.dominio.repositorio.TopicoRepositorio;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Instant;
import java.util.List;

/** Endpoints CRUD de tópicos. */
@RestController
@RequestMapping("/topicos")
public class TopicoControlador {
    private final TopicoRepositorio repositorio;

    public TopicoControlador(TopicoRepositorio repositorio) { this.repositorio = repositorio; }

    // LISTAR (público)
    @GetMapping
    public List<RespuestaTopico> listar() {
        return repositorio.findAll().stream().map(this::aRespuesta).toList();
    }

    // OBTENER POR ID (público)
    @GetMapping("/{id}")
    public ResponseEntity<RespuestaTopico> obtener(@PathVariable Long id) {
        return repositorio.findById(id)
                .map(t -> ResponseEntity.ok(aRespuesta(t)))
                .orElse(ResponseEntity.notFound().build());
    }

    // REGISTRAR (requiere JWT)
    @PostMapping
    public ResponseEntity<RespuestaTopico> crear(@RequestBody @Validated PeticionCrearTopico req,
                                                 @AuthenticationPrincipal(expression = "username") String nombreUsuario) {
        Topico t = Topico.builder()
                .titulo(req.titulo())
                .mensaje(req.mensaje())
                .autor(nombreUsuario)
                .creadoEn(Instant.now())
                .build();
        Topico guardado = repositorio.save(t);
        return ResponseEntity.created(URI.create("/topicos/" + guardado.getId())).body(aRespuesta(guardado));
    }

    // ACTUALIZAR (requiere JWT)
    @PutMapping("/{id}")
    public ResponseEntity<RespuestaTopico> actualizar(@PathVariable Long id,
                                                      @RequestBody @Validated PeticionActualizarTopico req,
                                                      @AuthenticationPrincipal(expression = "username") String nombreUsuario) {
        return repositorio.findById(id).map(t -> {
            t.setTitulo(req.titulo());
            t.setMensaje(req.mensaje());
            Topico guardado = repositorio.save(t);
            return ResponseEntity.ok(aRespuesta(guardado));
        }).orElse(ResponseEntity.notFound().build());
    }

    // ELIMINAR (requiere JWT)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!repositorio.existsById(id)) return ResponseEntity.notFound().build();
        repositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private RespuestaTopico aRespuesta(Topico t) {
        return new RespuestaTopico(t.getId(), t.getTitulo(), t.getMensaje(), t.getAutor(), t.getCreadoEn());
    }
}
