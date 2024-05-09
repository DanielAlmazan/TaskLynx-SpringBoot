package edu.tasklynx.tasklynxspringboot.models.dao;

import edu.tasklynx.tasklynxspringboot.models.entity.Trabajo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ITrabajoDAO extends CrudRepository<Trabajo, String> {
    List<Trabajo> findAllByFecFinIsNotNull();
    List<Trabajo> findAllByFecFinIsNull();
    List<Trabajo> findCompletaByTrabajador(@Param("idTrabajador") String id);
    List<Trabajo> findPendienteByTrabajador(@Param("idTrabajador") String id);
    // Trabajo findByCodTrabajoAndIdTrabajadorIsNull(String id);
    Trabajo findByIdAndUnassigned(@Param("codTrabajo") String id);
    List<Trabajo> findCompletadosPorTrabajadorEntreFechas(@Param("idTrabajador") String idTrabajador, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    List<Trabajo> findByIdTrabajadorIsNull();
    List<Trabajo> findByFecFinIsNull();
    List<Trabajo> findByFecFinIsNotNull();
    List<Trabajo> findPendingByTrabajadorOrderByPrioridad(String idTrabajador);
    List<Trabajo> findPendingByTrabajadorAndPrioridad(String idTrabajador, BigDecimal prioridad);
}