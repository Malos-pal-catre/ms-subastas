package com.pesquera.subastas.service;

import com.pesquera.subastas.exception.RecursoNoEncontradoException;
import com.pesquera.subastas.model.Subasta;
import com.pesquera.subastas.model.EstadoSubasta;
import com.pesquera.subastas.repository.SubastaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubastaService {

    private final SubastaRepository subastaRepository;

    public Subasta crearSubasta(Subasta subasta) {
        subasta.setEstado(EstadoSubasta.ABIERTA);
        subasta.setFechaInicio(LocalDateTime.now());
        return subastaRepository.save(subasta);
    }

    public List<Subasta> obtenerTodas() {
        return subastaRepository.findAll();
    }

    public Subasta obtenerPorId(Long id) {
        return subastaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Subasta no encontrada con id: " + id));
    }

    public List<Subasta> obtenerPorEstado(EstadoSubasta estado) {
        return subastaRepository.findByEstado(estado);
    }

    public List<Subasta> obtenerPorPescador(Long pescadorId) {
        return subastaRepository.findByPescadorId(pescadorId);
    }

    public Subasta cerrarSubasta(Long id, Double precioFinal, Long compradorGanadorId) {
        Subasta subasta = obtenerPorId(id);
        subasta.setPrecioFinal(precioFinal);
        subasta.setCompradorGanadorId(compradorGanadorId);
        subasta.setEstado(EstadoSubasta.CERRADA);
        subasta.setFechaCierre(LocalDateTime.now());
        return subastaRepository.save(subasta);
    }

    public Subasta marcarDesierta(Long id) {
        Subasta subasta = obtenerPorId(id);
        subasta.setEstado(EstadoSubasta.DESIERTA);
        subasta.setFechaCierre(LocalDateTime.now());
        return subastaRepository.save(subasta);
    }
}