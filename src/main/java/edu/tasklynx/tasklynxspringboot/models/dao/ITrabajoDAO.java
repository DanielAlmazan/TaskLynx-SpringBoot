package edu.tasklynx.tasklynxspringboot.models.dao;

import edu.tasklynx.tasklynxspringboot.models.entity.Trabajador;
import edu.tasklynx.tasklynxspringboot.models.entity.Trabajo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ITrabajoDAO extends CrudRepository<Trabajo, String> {
    List<Trabajo> findAllByFecFinIsNotNull();
    List<Trabajo> findAllByFecFinIsNull();
    List<Trabajo> findCompletaByTrabajador(@Param("idTrabajador") String id);
    List<Trabajo> findPendienteByTrabajador(@Param("idTrabajador") String id);
    Trabajador findTrabajadorByCodTrabajo(@Param("codTrabajo") String codTrabajo);
    List<Trabajo> findCompletadosPorTrabajadorEntreFechas(@Param("idTrabajador") String idTrabajador, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    List<Trabajo> findByIdTrabajadorIdTrabajadorAndFecFinIsLessThanEqual(@Param("idTrabajador") String id, @Param("fec_ini") LocalDate fecFin);
    List<Trabajo> findByIdTrabajadorIsNull();
    List<Trabajo> findByFecFinIsNull();
    List<Trabajo> findByFecFinIsNotNull();
    List<Trabajo> findPendingByTrabajadorOrderByPrioridad(String idTrabajador);
    List<Trabajo> findPendingByTrabajadorAndPrioridad(String idTrabajador, BigDecimal prioridad);
}