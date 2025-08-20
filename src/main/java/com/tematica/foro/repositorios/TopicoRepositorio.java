package com.tematica.foro.repositorios;

import com.tematica.foro.modelos.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepositorio extends JpaRepository<Topico, Long> {
}
