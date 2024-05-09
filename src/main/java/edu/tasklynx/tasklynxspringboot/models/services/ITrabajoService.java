package edu.tasklynx.tasklynxspringboot.models.services;

import edu.tasklynx.tasklynxspringboot.models.entity.Trabajo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ITrabajoService {
    public List<Trabajo> findAll();
    public Trabajo findById(String id);
    List<Trabajo> findTrabajosSinTrabajador();
    List<Trabajo> findPendientes();
    List<Trabajo> findCompletados();
    List<Trabajo> findCompletadosPorTrabajador(String idTrabajador);
    List<Trabajo> findCompletadosPorTrabajadorEntreFechas(String idTrabajador, LocalDate startDate, LocalDate endDate);
    List<Trabajo> findPendientesPorTrabajador(String idTrabajador);
    List<Trabajo> findPendientesPorTrabajadorOrderByPrioridadAsc(String trabajador);
    List<Trabajo> findPendientesPorTrabajadorYPrioridad(String trabajador, BigDecimal prioridad);
    public Trabajo save(Trabajo trabajo);
    Trabajo asignarTrabajo(String codTrabajo, String idTrabajador);
    Trabajo finalizarTrabajo(String id, LocalDate fec_fin, BigDecimal tiempo);
    Trabajo editarFechaFin(String id, LocalDate fec_fin);
    Trabajo editarTiempo(String id, BigDecimal tiempo);
    Trabajo crearTrabajoConTrabajador(Trabajo trabajo, String idTrabajador);
    public void delete(String id);
}
