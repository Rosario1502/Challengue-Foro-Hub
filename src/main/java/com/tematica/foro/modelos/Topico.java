package com.tematica.foro.modelos;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String mensaje;
}
