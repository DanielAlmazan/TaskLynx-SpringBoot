package edu.tasklynx.tasklynxspringboot.models.services;

import edu.tasklynx.tasklynxspringboot.models.dao.ITrabajadorDAO;
import edu.tasklynx.tasklynxspringboot.models.dao.ITrabajoDAO;
import edu.tasklynx.tasklynxspringboot.models.entity.Trabajador;
import edu.tasklynx.tasklynxspringboot.models.entity.Trabajo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class TrabajoServices implements ITrabajoService {
    @Autowired
    ITrabajoDAO trabajoDAO;
    @Autowired
    ITrabajadorDAO trabajadorDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Trabajo> findAll() {
        return ((List<Trabajo>) trabajoDAO.findAll()).stream()
                .sorted(Comparator.comparing(Trabajo::getCodTrabajo)).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Trabajo findById(String id) {
        return trabajoDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Trabajo> findPendientes() {
        return trabajoDAO.findByFecFinIsNull();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Trabajo> findTrabajosSinTrabajador() {
        return trabajoDAO.findByIdTrabajadorIsNull();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Trabajo> findCompletados() {
        return trabajoDAO.findByFecFinIsNotNull();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Trabajo> findCompletadosPorTrabajador(String idTrabajador) {
        return trabajoDAO.findCompletaByTrabajador(idTrabajador);
    }

    @Override
    public List<Trabajo> findCompletadosPorTrabajadorEntreFechas(String idTrabajador, LocalDate startDate, LocalDate endDate) {
        return trabajoDAO.findCompletadosPorTrabajadorEntreFechas(idTrabajador, startDate, endDate);
    }

    @Override
    public List<Trabajo> findCompletadosPorTrabajadorHastaFecha(String idTrabajador, LocalDate endDate) {
        return trabajoDAO.findByIdTrabajadorIdTrabajadorAndFecFinIsLessThanEqual(idTrabajador, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Trabajo> findPendientesPorTrabajador(String idTrabajador) {
        return trabajoDAO.findPendienteByTrabajador(idTrabajador);
    }

    @Override
    public List<Trabajo> findPendientesPorTrabajadorOrderByPrioridadAsc(String trabajador) {
        return trabajoDAO.findPendingByTrabajadorOrderByPrioridad(trabajador);
    }

    @Override
    public List<Trabajo> findPendientesPorTrabajadorYPrioridad(String trabajador, BigDecimal prioridad) {
        return trabajoDAO.findPendingByTrabajadorAndPrioridad(trabajador, prioridad);
    }

    @Override
    public Trabajador findTrabajadorByCodTrabajo(String codTrabajo) {
        return trabajoDAO.findTrabajadorByCodTrabajo(codTrabajo);
    }

    @Override
    @Transactional
    public Trabajo save(Trabajo trabajo) {
        return trabajoDAO.save(trabajo);
    }

    @Override
    @Transactional
    public Trabajo finalizarTrabajo(String id, LocalDate fec_fin, BigDecimal tiempo) {
        Trabajo trabajo = trabajoDAO.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trabajo no encontrado")
        );

        if (trabajo.getIdTrabajador() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El trabajo no tiene un trabajador asignado");
        }

        fec_fin = validateDate(trabajo, fec_fin);

        final BigDecimal tiempoMaximo = BigDecimal.valueOf(trabajo.getFecIni().until(fec_fin).getDays() * 8L);

        if (trabajo.getFecIni().isEqual(fec_fin)) {
            tiempo = BigDecimal.valueOf(8L);
        } else if (BigDecimal.ZERO.compareTo(tiempo) == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El tiempo no puede ser 0");
        } else {
            tiempo = validateTime(tiempo, tiempoMaximo);
        }

        trabajo.setFecFin(fec_fin);
        trabajo.setTiempo(tiempo);
        return trabajoDAO.save(trabajo);
    }

    @Override
    @Transactional
    public Trabajo asignarTrabajo(String codTrabajo, String idTrabajador) {
        Trabajo trabajo = trabajoDAO.findById(codTrabajo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trabajo no encontrado"));
        
        if (trabajo.getIdTrabajador() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El trabajo ya tiene un trabajador asignado");
        }

        Trabajador trabajador = trabajadorDAO.findById(idTrabajador)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trabajador no encontrado"));

        if (!trabajo.getCategoria().equals(trabajador.getEspecialidad())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La categoría del trabajo y la especialidad del trabajador no coinciden");
        }

        trabajo.setIdTrabajador(trabajador);
        return trabajoDAO.save(trabajo);
    }

    @Override
    @Transactional
    public Trabajo editarFechaFin(String id, LocalDate fec_fin) {
        Trabajo trabajo = trabajoDAO.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trabajo no encontrado"));

        fec_fin = validateDate(trabajo, fec_fin);

        trabajo.setFecFin(fec_fin);
        return trabajoDAO.save(trabajo);
    }

    /**
     * Auxiliar method to validate the date
     *
     * @param trabajo Trabajo object which contains the start date
     * @param fec_fin End date to validate
     * @return The validated end date
     */
    private LocalDate validateDate(Trabajo trabajo, LocalDate fec_fin) {
        if (fec_fin == null) {
            fec_fin = LocalDate.now();
        }

        if (fec_fin.isBefore(trabajo.getFecIni())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de finalización no puede ser menor a la fecha de inicio");
        }

        if (fec_fin.isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de finalización no puede ser mayor a la fecha actual");
        }

        return fec_fin;
    }

    @Override
    @Transactional
    public Trabajo editarTiempo(String id, BigDecimal tiempo) {
        Trabajo trabajo = trabajoDAO.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trabajo no encontrado"));

        final BigDecimal tiempoMaximo = BigDecimal.valueOf(trabajo.getFecIni().until(trabajo.getFecFin()).getDays() * 8L);
        tiempo = validateTime(tiempo, tiempoMaximo);

        trabajo.setTiempo(tiempo);
        return trabajoDAO.save(trabajo);

    }

    /**
     * Auxiliar method to validate the time
     *
     * @param tiempo       Time to validate
     * @param tiempoMaximo Maximum time allowed
     * @return The validated time
     */
    private BigDecimal validateTime(BigDecimal tiempo, BigDecimal tiempoMaximo) {
        if (tiempo == null) {
            tiempo = tiempoMaximo;
        }

        if (tiempo.compareTo(BigDecimal.ZERO) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El tiempo no puede ser menor a 0");
        }

        if (tiempo.compareTo(tiempoMaximo) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El tiempo no puede ser mayor a 8 horas por día trabajado");
        }
        return tiempo;
    }

    @Override
    public Trabajo crearTrabajoConTrabajador(Trabajo trabajo, String idTrabajador) {
        Trabajador trabajador = trabajadorDAO.findById(idTrabajador).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trabajador no encontrado"));

        trabajo.setIdTrabajador(trabajador);

        return trabajoDAO.save(trabajo);
    }

    @Override
    @Transactional
    public void delete(String id) {
        trabajoDAO.deleteById(id);
    }
}
