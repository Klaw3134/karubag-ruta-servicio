package cl.karubag.ruta.controller;

import cl.karubag.ruta.dto.RutaDTO;
import cl.karubag.ruta.model.EstadoRuta;
import cl.karubag.ruta.service.RutaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@Tag(name = "Rutas", description = "Gestión de rutas de operadores Karübag")
@RestController
@RequestMapping("/api/rutas")
public class RutaController {

    private final RutaService rutaService;

    public RutaController(RutaService rutaService) {
        this.rutaService = rutaService;
    }

    @Operation(summary = "Listar todas las rutas", description = "Retorna la lista completa de rutas")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<RutaDTO>> listarTodos() {
        return ResponseEntity.ok(rutaService.listarTodos());
    }

    @Operation(summary = "Listar por operador", description = "Retorna rutas de un operador específico")
    @ApiResponse(responseCode = "200", description = "Lista de rutas del operador")
    @GetMapping("/operador/{operadorId}")
    public ResponseEntity<List<RutaDTO>> listarPorOperador(@PathVariable Long operadorId) {
        return ResponseEntity.ok(rutaService.listarPorOperador(operadorId));
    }

    @Operation(summary = "Obtener ruta del día", description = "Retorna la ruta programada para hoy del operador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ruta del día encontrada"),
        @ApiResponse(responseCode = "404", description = "No hay ruta programada para hoy")
    })
    @GetMapping("/operador/{operadorId}/hoy")
    public ResponseEntity<RutaDTO> obtenerRutaDelDia(@PathVariable Long operadorId) {
        return ResponseEntity.ok(rutaService.obtenerRutaDelDia(operadorId));
    }

    @Operation(summary = "Listar por fecha", description = "Retorna rutas de una fecha específica")
    @ApiResponse(responseCode = "200", description = "Lista de rutas por fecha")
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<RutaDTO>> listarPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(rutaService.listarPorFecha(fecha));
    }

    @Operation(summary = "Listar por estado", description = "Retorna rutas filtradas por estado")
    @ApiResponse(responseCode = "200", description = "Lista de rutas por estado")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<RutaDTO>> listarPorEstado(@PathVariable EstadoRuta estado) {
        return ResponseEntity.ok(rutaService.listarPorEstado(estado));
    }

    @Operation(summary = "Obtener ruta por ID", description = "Busca una ruta por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ruta encontrada"),
        @ApiResponse(responseCode = "404", description = "Ruta no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RutaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(rutaService.obtenerPorId(id));
    }

    @Operation(summary = "Crear ruta", description = "Crea una nueva ruta para un operador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ruta creada exitosamente",
            content = @Content(schema = @Schema(implementation = RutaDTO.class),
            examples = @ExampleObject(value = "{\"operadorId\": 1, \"fecha\": \"2026-06-05\", \"totalParadas\": 28, \"estado\": \"PROGRAMADA\"}")))
    })
    @PostMapping
    public ResponseEntity<RutaDTO> crear(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos de la ruta a crear",
            required = true,
            content = @Content(examples = @ExampleObject(value = "{\"operadorId\": 1, \"fecha\": \"2026-06-05\", \"totalParadas\": 28, \"estado\": \"PROGRAMADA\"}")))
        @Valid @RequestBody RutaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rutaService.crear(dto));
    }

    @Operation(summary = "Actualizar ruta", description = "Actualiza los datos de una ruta")
    @ApiResponse(responseCode = "200", description = "Ruta actualizada exitosamente")
    @PutMapping("/{id}")
    public ResponseEntity<RutaDTO> actualizar(@PathVariable Long id, @Valid @RequestBody RutaDTO dto) {
        return ResponseEntity.ok(rutaService.actualizar(id, dto));
    }

    @Operation(summary = "Iniciar ruta", description = "Cambia el estado de la ruta a EN_CURSO")
    @ApiResponse(responseCode = "200", description = "Ruta iniciada exitosamente")
    @PutMapping("/{id}/iniciar")
    public ResponseEntity<RutaDTO> iniciar(@PathVariable Long id) {
        return ResponseEntity.ok(rutaService.iniciar(id));
    }

    @Operation(summary = "Completar ruta", description = "Cambia el estado de la ruta a COMPLETADA")
    @ApiResponse(responseCode = "200", description = "Ruta completada exitosamente")
    @PutMapping("/{id}/completar")
    public ResponseEntity<RutaDTO> completar(@PathVariable Long id) {
        return ResponseEntity.ok(rutaService.completar(id));
    }

    @Operation(summary = "Eliminar ruta", description = "Elimina una ruta por su ID")
    @ApiResponse(responseCode = "204", description = "Ruta eliminada exitosamente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        rutaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
