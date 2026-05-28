package cl.karubag.ruta.repository;

import cl.karubag.ruta.model.Ruta;
import cl.karubag.ruta.model.EstadoRuta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface RutaRepository extends JpaRepository<Ruta, Long> {

    List<Ruta> findByOperadorId(Long operadorId);

    List<Ruta> findByFecha(LocalDate fecha);

    List<Ruta> findByEstado(EstadoRuta estado);

    List<Ruta> findByOperadorIdAndFecha(Long operadorId, LocalDate fecha);
}
