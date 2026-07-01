package com.pesquera.subastas.controller;

import com.pesquera.subastas.dto.SubastaMapper;
import com.pesquera.subastas.dto.SubastaRequestDTO;
import com.pesquera.subastas.dto.SubastaResponseDTO;
import com.pesquera.subastas.model.EstadoSubasta;
import com.pesquera.subastas.model.Subasta;
import com.pesquera.subastas.service.SubastaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/subastas")
@RequiredArgsConstructor
@Tag(name = "Subastas", description = "Gestión de subastas - Caleta Pesquera")
public class SubastaController {

    private final SubastaService subastaService;

    @PostMapping
    @Operation(
            summary = "Crear una nueva subasta",
            description = "Abre una nueva subasta para una captura de un pescador, con un precio base inicial."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Subasta creada correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "SubastaCreada",
                                    value = "{\"id\":10,\"especie\":\"Loco\",\"kilos\":120.5,\"pescadorId\":2,\"precioBase\":600000.0,\"precioFinal\":null,\"compradorGanadorId\":null,\"estado\":\"ABIERTA\",\"fechaInicio\":\"2026-06-30T08:00:00\",\"fechaCierre\":null}"
                            ))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "ErrorValidacion",
                                    value = "{\"precioBase\":\"El precio base debe ser mayor a 0\"}"
                            )))
    })
    public ResponseEntity<SubastaResponseDTO> crearSubasta(
            @org.springframework.web.bind.annotation.RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la subasta a crear",
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SubastaRequestDTO.class),
                            examples = @ExampleObject(
                                    name = "NuevaSubasta",
                                    value = "{\"especie\":\"Loco\",\"kilos\":120.5,\"pescadorId\":2,\"precioBase\":600000.0}"
                            )))
            @Valid SubastaRequestDTO dto) {
        Subasta subasta = SubastaMapper.toEntity(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(SubastaMapper.toDTO(subastaService.crearSubasta(subasta)));
    }

    @GetMapping
    @Operation(
            summary = "Listar todas las subastas",
            description = "Retorna el listado completo de subastas registradas, en cualquier estado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "ListadoSubastas",
                                    value = "[{\"id\":10,\"especie\":\"Loco\",\"kilos\":120.5,\"pescadorId\":2,\"precioBase\":600000.0,\"precioFinal\":null,\"compradorGanadorId\":null,\"estado\":\"ABIERTA\",\"fechaInicio\":\"2026-06-30T08:00:00\",\"fechaCierre\":null}]"
                            )))
    })
    public ResponseEntity<List<SubastaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(subastaService.obtenerTodas().stream().map(SubastaMapper::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar subasta por ID",
            description = "Retorna los datos de una subasta específica según su identificador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subasta encontrada",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "SubastaEncontrada",
                                    value = "{\"id\":10,\"especie\":\"Loco\",\"kilos\":120.5,\"pescadorId\":2,\"precioBase\":600000.0,\"precioFinal\":null,\"compradorGanadorId\":null,\"estado\":\"ABIERTA\",\"fechaInicio\":\"2026-06-30T08:00:00\",\"fechaCierre\":null}"
                            ))),
            @ApiResponse(responseCode = "404", description = "Subasta no encontrada")
    })
    public ResponseEntity<SubastaResponseDTO> obtenerPorId(
            @Parameter(description = "ID de la subasta", example = "10")
            @PathVariable Long id) {
        return ResponseEntity.ok(SubastaMapper.toDTO(subastaService.obtenerPorId(id)));
    }

    @GetMapping("/estado/{estado}")
    @Operation(
            summary = "Listar subastas por estado",
            description = "Retorna las subastas filtradas por estado: ABIERTA, CERRADA o DESIERTA."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de subastas por estado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "SubastasPorEstado",
                                    value = "[{\"id\":10,\"especie\":\"Loco\",\"kilos\":120.5,\"pescadorId\":2,\"precioBase\":600000.0,\"precioFinal\":null,\"compradorGanadorId\":null,\"estado\":\"ABIERTA\",\"fechaInicio\":\"2026-06-30T08:00:00\",\"fechaCierre\":null}]"
                            )))
    })
    public ResponseEntity<List<SubastaResponseDTO>> obtenerPorEstado(
            @Parameter(description = "Estado de la subasta", example = "ABIERTA")
            @PathVariable EstadoSubasta estado) {
        return ResponseEntity.ok(subastaService.obtenerPorEstado(estado).stream().map(SubastaMapper::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/pescador/{pescadorId}")
    @Operation(
            summary = "Listar subastas por pescador",
            description = "Retorna todas las subastas asociadas a un pescador específico."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de subastas del pescador",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "SubastasPorPescador",
                                    value = "[{\"id\":10,\"especie\":\"Loco\",\"kilos\":120.5,\"pescadorId\":2,\"precioBase\":600000.0,\"precioFinal\":null,\"compradorGanadorId\":null,\"estado\":\"ABIERTA\",\"fechaInicio\":\"2026-06-30T08:00:00\",\"fechaCierre\":null}]"
                            )))
    })
    public ResponseEntity<List<SubastaResponseDTO>> obtenerPorPescador(
            @Parameter(description = "ID del pescador", example = "2")
            @PathVariable Long pescadorId) {
        return ResponseEntity.ok(subastaService.obtenerPorPescador(pescadorId).stream().map(SubastaMapper::toDTO).collect(Collectors.toList()));
    }

    @PutMapping("/{id}/cerrar")
    @Operation(
            summary = "Cerrar una subasta",
            description = "Cierra la subasta indicada, registrando el precio final pactado y el comprador ganador."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subasta cerrada correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "SubastaCerrada",
                                    value = "{\"id\":10,\"especie\":\"Loco\",\"kilos\":120.5,\"pescadorId\":2,\"precioBase\":600000.0,\"precioFinal\":850000.0,\"compradorGanadorId\":3,\"estado\":\"CERRADA\",\"fechaInicio\":\"2026-06-30T08:00:00\",\"fechaCierre\":\"2026-06-30T10:00:00\"}"
                            ))),
            @ApiResponse(responseCode = "404", description = "Subasta no encontrada")
    })
    public ResponseEntity<SubastaResponseDTO> cerrarSubasta(
            @Parameter(description = "ID de la subasta a cerrar", example = "10")
            @PathVariable Long id,
            @Parameter(description = "Precio final pactado en la subasta", example = "850000.0")
            @RequestParam Double precioFinal,
            @Parameter(description = "ID del comprador ganador de la subasta", example = "3")
            @RequestParam Long compradorGanadorId) {
        return ResponseEntity.ok(SubastaMapper.toDTO(subastaService.cerrarSubasta(id, precioFinal, compradorGanadorId)));
    }

    @PutMapping("/{id}/desierta")
    @Operation(
            summary = "Marcar subasta como desierta",
            description = "Marca la subasta indicada como desierta cuando no hubo compradores interesados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subasta marcada como desierta correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "SubastaDesierta",
                                    value = "{\"id\":10,\"especie\":\"Loco\",\"kilos\":120.5,\"pescadorId\":2,\"precioBase\":600000.0,\"precioFinal\":null,\"compradorGanadorId\":null,\"estado\":\"DESIERTA\",\"fechaInicio\":\"2026-06-30T08:00:00\",\"fechaCierre\":\"2026-06-30T10:00:00\"}"
                            ))),
            @ApiResponse(responseCode = "404", description = "Subasta no encontrada")
    })
    public ResponseEntity<SubastaResponseDTO> marcarDesierta(
            @Parameter(description = "ID de la subasta a marcar como desierta", example = "10")
            @PathVariable Long id) {
        return ResponseEntity.ok(SubastaMapper.toDTO(subastaService.marcarDesierta(id)));
    }
}