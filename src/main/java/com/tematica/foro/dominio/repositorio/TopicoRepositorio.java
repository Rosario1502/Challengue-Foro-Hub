package com.tematica.foro.dominio.repositorio;

import com.tematica.foro.dominio.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

/** Acceso a datos de tópicos. */
public interface TopicoRepositorio extends JpaRepository<Topico, Long> { }
