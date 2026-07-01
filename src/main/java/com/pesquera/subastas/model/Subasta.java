package com.pesquera.subastas.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "subastas")
public class Subasta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String especie;

    @Column(nullable = false)
    private Double kilos;

    @Column(nullable = false)
    private Long pescadorId;

    @Column(nullable = false)
    private Double precioBase;

    private Double precioFinal;

    private Long compradorGanadorId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoSubasta estado;

    @Column(nullable = false)
    private LocalDateTime fechaInicio;

    private LocalDateTime fechaCierre;
}