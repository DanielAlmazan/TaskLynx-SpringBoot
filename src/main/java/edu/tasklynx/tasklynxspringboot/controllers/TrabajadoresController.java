package edu.tasklynx.tasklynxspringboot.controllers;

import edu.tasklynx.tasklynxspringboot.models.entity.Trabajador;
import edu.tasklynx.tasklynxspringboot.models.entity.Trabajo;
import edu.tasklynx.tasklynxspringboot.models.services.ITrabajadorService;
import edu.tasklynx.tasklynxspringboot.models.services.ITrabajoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("NonAsciiCharacters")
@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class TrabajadoresController {
    @Autowired
    private ITrabajadorService trabajadorService;
    @Autowired
    private ITrabajoService trabajoService;

    @GetMapping("/trabajadores")
    public ResponseEntity<?> indexAll() {
        List<Trabajador> trabajadores;
        Map<String, Object> response = new HashMap<>();

        try {
            trabajadores = trabajadorService.findAll();
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(trabajadores, HttpStatus.OK);
    }

    @GetMapping("/trabajadores/{id}")
    public ResponseEntity<?> indexOne(@PathVariable String id) {
        Trabajador trabajador;
        Map<String, Object> response = new HashMap<>();

        try {
            trabajador = trabajadorService.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (trabajador == null) {
            response.put("mensaje", "El trabajador con ID: \"" + id + "\" no existe en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(trabajador, HttpStatus.OK);
    }

    @GetMapping("/trabajadores/{nombre}/{contraseña}")
    public ResponseEntity<?> indexOneByUsuarioAndContraseña(@PathVariable String nombre, @PathVariable String contraseña) {
        Trabajador trabajador;
        Map<String, Object> response = new HashMap<>();

        try {
            trabajador = trabajadorService.findByNameAndPass(nombre, contraseña);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (trabajador == null) {
            response.put("mensaje", "El trabajador con usuario: " + nombre + " y contraseña: " + contraseña + " no existe en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(trabajador, HttpStatus.OK);
    }

    @GetMapping("/trabajadores/{id}/trabajos")
    public ResponseEntity<?> indexOneTrabajos(@PathVariable String id) {
        List<Trabajo> trabajos;
        Map<String, Object> response = new HashMap<>();

        try {
            trabajos = trabajadorService.findById(id).getTrabajos().stream().toList();
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (trabajos.isEmpty()) {
            response.put("mensaje", "No existen trabajos para el trabajador con ID: " + id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(trabajos, HttpStatus.OK);
    }

    @GetMapping("/trabajadores/{id}/trabajos/pendientes")
    public ResponseEntity<?> indexOneTrabajosPendientes(@PathVariable String id) {
        List<Trabajo> trabajosCompletados;
        Map<String, Object> response = new HashMap<>();

        try {
            trabajosCompletados = trabajoService.findPendientesPorTrabajador(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (trabajosCompletados.isEmpty()) {
            response.put("mensaje", "No existen trabajos pendientes para el trabajador con ID: " + id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(trabajosCompletados, HttpStatus.OK);
    }
    
    @GetMapping("/trabajadores/{id}/trabajos/pendientes/prioridad")
    public ResponseEntity<?> indexOneTrabajosPendientesOrderByPrioridad(@PathVariable String id) {
        List<Trabajo> trabajosCompletados;
        Map<String, Object> response = new HashMap<>();

        try {
            trabajosCompletados = trabajoService.findPendientesPorTrabajadorOrderByPrioridadAsc(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (trabajosCompletados.isEmpty()) {
            response.put("mensaje", "No existen trabajos pendientes para el trabajador con ID: " + id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(trabajosCompletados, HttpStatus.OK);
    }
    
    @GetMapping("/trabajadores/{id}/trabajos/pendientes/{prioridad}")
    public ResponseEntity<?> indexOneTrabajosPendientesByPrioridad(@PathVariable String id, @PathVariable BigDecimal prioridad) {
        List<Trabajo> trabajosCompletados;
        Map<String, Object> response = new HashMap<>();

        try {
            trabajosCompletados = trabajoService.findPendientesPorTrabajadorYPrioridad(id, prioridad);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (trabajosCompletados.isEmpty()) {
            response.put("mensaje", "No existen trabajos pendientes para el trabajador con ID: " + id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(trabajosCompletados, HttpStatus.OK);
    }

    @GetMapping("/trabajadores/{id}/trabajos/completados")
    public ResponseEntity<?> indexOneTrabajosCompletadosEntreFechas(
            @PathVariable String id,
            @RequestParam
(required = false)
 @DateTimeFormat() LocalDate fechaIni,
            @RequestParam
(required = false)
 @DateTimeFormat() LocalDate fechaFin
    ) {
        List<Trabajo> trabajosCompletados;
        Map<String, Object> response = new HashMap<>();
        
        if (fechaIni != null && fechaFin == null) {
            fechaFin = LocalDate.now();
        }

        try {
            if (fechaIni == null && fechaFin != null) {
                trabajosCompletados = trabajoService.findCompletadosPorTrabajador(id);
            } else {
                trabajosCompletados = trabajoService.findCompletadosPorTrabajadorEntreFechas(id, fechaIni, fechaFin);
            }
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (trabajosCompletados.isEmpty()) {
            response.put("mensaje", "No existen trabajos completados para el trabajador con ID: " + id + " entre las fechas " + fechaIni + " y " + fechaFin);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(trabajosCompletados, HttpStatus.OK);
    }

    @PostMapping("/trabajadores")
    public ResponseEntity<?> create(@Valid @RequestBody Trabajador trabajador, BindingResult result) {
        Trabajador newTrabajador;
        Map<String, Object> response = new HashMap<>();

        if (showErrors(result, response)) return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        try {
            newTrabajador = trabajadorService.save(trabajador);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la inserción en la base de datos");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El trabajador ha sido insertado con éxito");
        response.put("trabajador", newTrabajador);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/trabajadores/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Trabajador trabajador, @PathVariable String id, BindingResult result) {
        Trabajador currentTrabajador = trabajadorService.findById(id);
        Trabajador updatedTrabajador;
        Map<String, Object> response = new HashMap<>();

        if (showErrors(result, response)) return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        if (currentTrabajador == null) {
            response.put("mensaje", "Error: no se pudo editar, el trabajador con ID: " + id + " no existe en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            currentTrabajador.setDni(trabajador.getDni());
            currentTrabajador.setNombre(trabajador.getNombre());
            currentTrabajador.setApellidos(trabajador.getApellidos());
            currentTrabajador.setEspecialidad(trabajador.getEspecialidad());
            currentTrabajador.setContraseña(trabajador.getContraseña());
            currentTrabajador.setEmail(trabajador.getEmail());

            updatedTrabajador = trabajadorService.save(currentTrabajador);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la actualización en la base de datos");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El trabajador ha sido actualizado con éxito");
        response.put("trabajador", updatedTrabajador);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/trabajadores/{id}/eliminar")
    public ResponseEntity<?> delete(@PathVariable String id) {

        Map<String, Object> response = new HashMap<>();

        try {
            trabajadorService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la eliminación en la base de datos");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El trabajador ha sido eliminado con éxito");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Método auxiliar para mostrar errores (va el mismo código en put y post)
    private boolean showErrors(BindingResult result, Map<String, Object> response) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();

            response.put("errors", errors);
            return true;
        }
        return false;
    }
}
