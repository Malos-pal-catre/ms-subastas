package com.pesquera.subastas.dto;

import com.pesquera.subastas.model.Subasta;

public class SubastaMapper {

    public static SubastaResponseDTO toDTO(Subasta subasta) {
        SubastaResponseDTO dto = new SubastaResponseDTO();
        dto.setId(subasta.getId());
        dto.setEspecie(subasta.getEspecie());
        dto.setKilos(subasta.getKilos());
        dto.setPescadorId(subasta.getPescadorId());
        dto.setPrecioBase(subasta.getPrecioBase());
        dto.setPrecioFinal(subasta.getPrecioFinal());
        dto.setCompradorGanadorId(subasta.getCompradorGanadorId());
        dto.setEstado(subasta.getEstado().name());
        dto.setFechaInicio(subasta.getFechaInicio());
        dto.setFechaCierre(subasta.getFechaCierre());
        return dto;
    }

    public static Subasta toEntity(SubastaRequestDTO dto) {
        Subasta subasta = new Subasta();
        subasta.setEspecie(dto.getEspecie());
        subasta.setKilos(dto.getKilos());
        subasta.setPescadorId(dto.getPescadorId());
        subasta.setPrecioBase(dto.getPrecioBase());
        return subasta;
    }
}