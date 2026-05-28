package cl.karubag.ruta.service;

import cl.karubag.ruta.dto.RutaDTO;
import cl.karubag.ruta.model.EstadoRuta;
import cl.karubag.ruta.model.Ruta;
import cl.karubag.ruta.repository.RutaRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RutaService {

    private final RutaRepository rutaRepository;

    public RutaService(RutaRepository rutaRepository) {
        this.rutaRepository = rutaRepository;
    }

    public List<RutaDTO> listarTodos() {
        return rutaRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<RutaDTO> listarPorOperador(Long operadorId) {
        return rutaRepository.findByOperadorId(operadorId)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<RutaDTO> listarPorFecha(LocalDate fecha) {
        return rutaRepository.findByFecha(fecha)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<RutaDTO> listarPorEstado(EstadoRuta estado) {
        return rutaRepository.findByEstado(estado)
                .stream().map(this::toDTO).collect(Collectors.toList());
    }

    public RutaDTO obtenerRutaDelDia(Long operadorId) {
        return rutaRepository.findByOperadorIdAndFecha(operadorId, LocalDate.now())
                .stream().map(this::toDTO).findFirst()
                .orElseThrow(() -> new RuntimeException("No hay ruta programada para hoy"));
    }

    public RutaDTO obtenerPorId(Long id) {
        Ruta ruta = rutaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada con id: " + id));
        return toDTO(ruta);
    }

    public RutaDTO crear(RutaDTO dto) {
        return toDTO(rutaRepository.save(toEntity(dto)));
    }

    public RutaDTO actualizar(Long id, RutaDTO dto) {
        Ruta ruta = rutaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada con id: " + id));
        ruta.setOperadorId(dto.getOperadorId());
        ruta.setFecha(dto.getFecha());
        ruta.setTotalParadas(dto.getTotalParadas());
        ruta.setEstado(dto.getEstado());
        ruta.setObservacion(dto.getObservacion());
        return toDTO(rutaRepository.save(ruta));
    }

    public RutaDTO iniciar(Long id) {
        Ruta ruta = rutaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada con id: " + id));
        ruta.setEstado(EstadoRuta.EN_CURSO);
        return toDTO(rutaRepository.save(ruta));
    }

    public RutaDTO completar(Long id) {
        Ruta ruta = rutaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada con id: " + id));
        ruta.setEstado(EstadoRuta.COMPLETADA);
        return toDTO(rutaRepository.save(ruta));
    }

    public void eliminar(Long id) {
        rutaRepository.deleteById(id);
    }

    private RutaDTO toDTO(Ruta r) {
        RutaDTO dto = new RutaDTO();
        dto.setId(r.getId());
        dto.setOperadorId(r.getOperadorId());
        dto.setFecha(r.getFecha());
        dto.setTotalParadas(r.getTotalParadas());
        dto.setEstado(r.getEstado());
        dto.setObservacion(r.getObservacion());
        return dto;
    }

    private Ruta toEntity(RutaDTO dto) {
        Ruta r = new Ruta();
        r.setOperadorId(dto.getOperadorId());
        r.setFecha(dto.getFecha());
        r.setTotalParadas(dto.getTotalParadas() != null ? dto.getTotalParadas() : 0);
        r.setEstado(dto.getEstado() != null ? dto.getEstado() : EstadoRuta.PROGRAMADA);
        r.setObservacion(dto.getObservacion());
        return r;
    }
}
