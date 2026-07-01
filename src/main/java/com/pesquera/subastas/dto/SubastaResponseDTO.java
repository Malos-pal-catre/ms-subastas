package com.pesquera.subastas.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SubastaResponseDTO {
    private Long id;
    private String especie;
    private Double kilos;
    private Long pescadorId;
    private Double precioBase;
    private Double precioFinal;
    private Long compradorGanadorId;
    private String estado;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaCierre;
}