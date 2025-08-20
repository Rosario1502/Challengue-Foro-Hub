package com.tematica.foro.controladores;

import com.tematica.foro.modelos.Topico;
import com.tematica.foro.repositorios.TopicoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoControlador {

    @Autowired
    private TopicoRepositorio repositorio;

    @GetMapping
    public List<Topico> listar() {
        return repositorio.findAll();
    }

    @PostMapping
    public Topico crear(@RequestBody Topico topico) {
        return repositorio.save(topico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topico> actualizar(@PathVariable Long id, @RequestBody Topico topico) {
        return repositorio.findById(id)
                .map(t -> {
                    t.setTitulo(topico.getTitulo());
                    t.setMensaje(topico.getMensaje());
                    return ResponseEntity.ok(repositorio.save(t));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        return repositorio.findById(id)
                .map(t -> {
                    repositorio.delete(t);
                    return ResponseEntity.noContent().<Void>build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
