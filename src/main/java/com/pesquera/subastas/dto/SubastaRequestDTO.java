package com.pesquera.subastas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class SubastaRequestDTO {

    @NotBlank(message = "La especie es obligatoria")
    private String especie;

    @NotNull(message = "Los kilos son obligatorios")
    @Positive(message = "Los kilos deben ser mayor a 0")
    private Double kilos;

    @NotNull(message = "El id del pescador es obligatorio")
    private Long pescadorId;

    @NotNull(message = "El precio base es obligatorio")
    @Positive(message = "El precio base debe ser mayor a 0")
    private Double precioBase;
}