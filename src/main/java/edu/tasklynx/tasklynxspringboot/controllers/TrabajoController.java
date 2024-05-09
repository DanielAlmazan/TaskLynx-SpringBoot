package edu.tasklynx.tasklynxspringboot.controllers;

import edu.tasklynx.tasklynxspringboot.models.entity.Trabajo;
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
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class TrabajoController {
    @Autowired
    private ITrabajoService trabajoService;

    @GetMapping("/trabajos")
    public ResponseEntity<?> showAll() {
        List<Trabajo> trabajos;
        Map<String, Object> response = new HashMap<>();

        try {
            trabajos = trabajoService.findAll();
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(trabajos, HttpStatus.OK);
    }
    
    
    
    @GetMapping("/trabajos/pendientes")
    public ResponseEntity<?> showPendientes() {
        List<Trabajo> trabajos;
        Map<String, Object> response = new HashMap<>();

        try {
            trabajos = trabajoService.findPendientes();
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(trabajos, HttpStatus.OK);
    }
    
    @GetMapping("/trabajos/completados")
    public ResponseEntity<?> showCompletados() {
        List<Trabajo> trabajos;
        Map<String, Object> response = new HashMap<>();

        try {
            trabajos = trabajoService.findCompletados();
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(trabajos, HttpStatus.OK);
    }

    @GetMapping("/trabajos/{id}")
    public ResponseEntity<?> showById(@PathVariable String id) {
        Trabajo trabajo;
        Map<String, Object> response = new HashMap<>();

        try {
            trabajo = trabajoService.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (trabajo == null) {
            response.put("mensaje", "El trabajo con ID '" + id + "' no existe en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(trabajo, HttpStatus.OK);
    }

    @PostMapping("/trabajos")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@Valid @RequestBody Trabajo trabajo, BindingResult result) {
        Trabajo trabajoNew;
        Map<String, Object> response = new HashMap<>();

        if (checkErrors(result, response)) return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        try {
            trabajoNew = trabajoService.save(trabajo);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "EL trabajo ha sido insertado con éxito");
        response.put("trabajo", trabajoNew);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/trabajos/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> update(@Valid @RequestBody Trabajo trabajo, @PathVariable String id, BindingResult result) {
        Trabajo currentTrabajo = trabajoService.findById(id);
        Trabajo updatedTrabajo;
        Map<String, Object> response = new HashMap<>();

        if (checkErrors(result, response)) return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        if (currentTrabajo == null) {
            response.put("mensaje", "Error: no se pudo editar, el trabajador con ID '" + id
                    + "' no existe en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            // Update values
            currentTrabajo.setCategoria(trabajo.getCategoria());
            currentTrabajo.setDescripcion(trabajo.getDescripcion());
            currentTrabajo.setFecIni(trabajo.getFecIni());
            currentTrabajo.setFecFin(trabajo.getFecFin());
            currentTrabajo.setTiempo(trabajo.getTiempo());
            currentTrabajo.setIdTrabajador(trabajo.getIdTrabajador());
            currentTrabajo.setPrioridad(trabajo.getPrioridad());

            updatedTrabajo = trabajoService.save(currentTrabajo);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la actualización en la base de datos");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "EL trabajo ha sido actualizado con éxito");
        response.put("trabajo", updatedTrabajo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/trabajos/finalizar/{id}")
    public ResponseEntity<?> finalizarTrabajo(
            @PathVariable String id,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fec_fin,
            @RequestParam(required = false) BigDecimal tiempo
    ) {
        Trabajo trabajo;
        Map<String, Object> response = new HashMap<>();

        try {
            trabajo = trabajoService.finalizarTrabajo(id, fec_fin, tiempo);
        } catch (ResponseStatusException e) {
            response.put("mensaje", "Error al finalizar el trabajo");
            response.put("error", e.getReason());
            return new ResponseEntity<>(response, e.getStatusCode());
        }

        response.put("mensaje", "El trabajo ha sido finalizado con éxito");
        response.put("trabajo", trabajo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/trabajos/asignar/{codTrabajo}/{idTrabajador}")
    public ResponseEntity<?> asignarTrabajo(@PathVariable String codTrabajo, @PathVariable String idTrabajador) {
        Trabajo trabajo;
        Map<String, Object> response = new HashMap<>();

        try {
            trabajo = trabajoService.asignarTrabajo(codTrabajo, idTrabajador);
        } catch (ResponseStatusException e) {
            response.put("mensaje", "Error al asignar el trabajo al trabajador");
            response.put("error", e.getReason());
            return new ResponseEntity<>(response, e.getStatusCode());
        }

        response.put("mensaje", "El trabajo ha sido asignado con éxito");
        response.put("trabajo", trabajo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/trabajos/editarFechaFin/{id}")
    public ResponseEntity<?> editarFechaFin(
            @PathVariable String id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fec_fin
    ) {
        Trabajo trabajo;
        Map<String, Object> response = new HashMap<>();

        try {
            trabajo = trabajoService.editarFechaFin(id, fec_fin);
        } catch (ResponseStatusException e) {
            response.put("mensaje", "Error al editar la fecha de finalización del trabajo");
            response.put("error", e.getReason());
            return new ResponseEntity<>(response, e.getStatusCode());
        }

        response.put("mensaje", "La fecha de finalización del trabajo ha sido editada con éxito");
        response.put("trabajo", trabajo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/trabajos/editarTiempo/{id}")
    public ResponseEntity<?> editarTiempo(@PathVariable String id, @RequestParam BigDecimal tiempo) {
        Trabajo trabajo;
        Map<String, Object> response = new HashMap<>();

        try {
            trabajo = trabajoService.editarTiempo(id, tiempo);
        } catch (ResponseStatusException e) {
            response.put("mensaje", "Error al editar el tiempo del trabajo");
            response.put("error", e.getReason());
            return new ResponseEntity<>(response, e.getStatusCode());
        }

        response.put("mensaje", "El tiempo del trabajo ha sido editado con éxito");
        response.put("trabajo", trabajo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/trabajos/crearConTrabajador/{idTrabajador}")
    public ResponseEntity<?> crearTrabajoConTrabajador(
            @Valid @RequestBody Trabajo trabajo,
            @PathVariable String idTrabajador
            //BindingResult result
    ) {
        Trabajo trabajoNew;
        Map<String, Object> response = new HashMap<>();

        //if (checkErrors(result, response)) return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        try {
            trabajoNew = trabajoService.crearTrabajoConTrabajador(trabajo, idTrabajador);
        } catch (ResponseStatusException e) {
            response.put("mensaje", "Error al crear el trabajo con el trabajador");
            response.put("error", e.getReason());
            return new ResponseEntity<>(response, e.getStatusCode());
        }

        response.put("mensaje", "El trabajo ha sido creado con éxito");
        response.put("trabajo", trabajoNew);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/trabajos/{id}/eliminar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();

        try {
            trabajoService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar el trabajo en la base de datos");
            response.put("error", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "EL trabajo ha sido eliminado con éxito");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Método auxiliar para comprobar errores de validación
    private boolean checkErrors(BindingResult result, Map<String, Object> response) {
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
