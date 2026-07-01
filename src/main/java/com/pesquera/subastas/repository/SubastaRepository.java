package com.pesquera.subastas.repository;

import com.pesquera.subastas.model.Subasta;
import com.pesquera.subastas.model.EstadoSubasta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubastaRepository extends JpaRepository<Subasta, Long> {

    List<Subasta> findByEstado(EstadoSubasta estado);
    List<Subasta> findByPescadorId(Long pescadorId);
    List<Subasta> findByEspecie(String especie);

    @Query("SELECT s FROM Subasta s WHERE s.estado = 'ABIERTA' AND s.especie = :especie")
    List<Subasta> findSubastasAbiertasByEspecie(@Param("especie") String especie);

    @Query("SELECT s FROM Subasta s WHERE s.pescadorId = :pescadorId AND s.estado = 'CERRADA' ORDER BY s.fechaCierre DESC")
    List<Subasta> findSubastasCerradasByPescador(@Param("pescadorId") Long pescadorId);

    @Query(value = "SELECT * FROM subastas WHERE estado = 'ABIERTA' ORDER BY fecha_inicio ASC", nativeQuery = true)
    List<Subasta> findTodasSubastasAbiertas();
}