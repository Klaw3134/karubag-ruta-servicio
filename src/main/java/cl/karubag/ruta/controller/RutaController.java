package cl.karubag.ruta.controller;

import cl.karubag.ruta.dto.RutaDTO;
import cl.karubag.ruta.model.EstadoRuta;
import cl.karubag.ruta.service.RutaService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rutas")
public class RutaController {

    private final RutaService rutaService;

    public RutaController(RutaService rutaService) {
        this.rutaService = rutaService;
    }

    @GetMapping
    public ResponseEntity<List<RutaDTO>> listarTodos() {
        return ResponseEntity.ok(rutaService.listarTodos());
    }

    @GetMapping("/operador/{operadorId}")
    public ResponseEntity<List<RutaDTO>> listarPorOperador(@PathVariable Long operadorId) {
        return ResponseEntity.ok(rutaService.listarPorOperador(operadorId));
    }

    @GetMapping("/operador/{operadorId}/hoy")
    public ResponseEntity<RutaDTO> obtenerRutaDelDia(@PathVariable Long operadorId) {
        return ResponseEntity.ok(rutaService.obtenerRutaDelDia(operadorId));
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<RutaDTO>> listarPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(rutaService.listarPorFecha(fecha));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<RutaDTO>> listarPorEstado(@PathVariable EstadoRuta estado) {
        return ResponseEntity.ok(rutaService.listarPorEstado(estado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RutaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(rutaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<RutaDTO> crear(@Valid @RequestBody RutaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rutaService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RutaDTO> actualizar(@PathVariable Long id, @Valid @RequestBody RutaDTO dto) {
        return ResponseEntity.ok(rutaService.actualizar(id, dto));
    }

    @PutMapping("/{id}/iniciar")
    public ResponseEntity<RutaDTO> iniciar(@PathVariable Long id) {
        return ResponseEntity.ok(rutaService.iniciar(id));
    }

    @PutMapping("/{id}/completar")
    public ResponseEntity<RutaDTO> completar(@PathVariable Long id) {
        return ResponseEntity.ok(rutaService.completar(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        rutaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
