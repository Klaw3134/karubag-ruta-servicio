package cl.karubag.ruta.dto;

import cl.karubag.ruta.model.EstadoRuta;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class RutaDTO {

    private Long id;

    @NotNull(message = "El operadorId es obligatorio")
    private Long operadorId;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    private Integer totalParadas;

    private EstadoRuta estado;

    private String observacion;

    public RutaDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getOperadorId() { return operadorId; }
    public void setOperadorId(Long operadorId) { this.operadorId = operadorId; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public Integer getTotalParadas() { return totalParadas; }
    public void setTotalParadas(Integer totalParadas) { this.totalParadas = totalParadas; }
    public EstadoRuta getEstado() { return estado; }
    public void setEstado(EstadoRuta estado) { this.estado = estado; }
    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
}
