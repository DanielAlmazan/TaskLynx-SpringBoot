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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("NonAsciiCharacters")
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/api")
public class TrabajadoresController {
    @Autowired
    private ITrabajadorService trabajadorService;
    @Autowired
    private ITrabajoService trabajoService;

    // Devuelve todos los trabajadores
    @GetMapping("/trabajadores")
    public ResponseEntity<?> indexAll() {
        List<Trabajador> trabajadores;
        Map<String, Object> response = new HashMap<>();

        try {
            trabajadores = trabajadorService.findAll();
        } catch (DataAccessException e) {
            response.put("error", true);
            response.put("errorMessage", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("error", false);
        response.put("result", trabajadores);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Devuelve un trabajador por ID
    @GetMapping("/trabajadores/{id}")
    public ResponseEntity<?> indexOne(@PathVariable String id) {
        Trabajador trabajador;
        Map<String, Object> response = new HashMap<>();

        try {
            trabajador = trabajadorService.findById(id);
        } catch (DataAccessException e) {
            response.put("error", true);
            response.put("errorMessage", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (trabajador == null) {
            response.put("error", true);
            response.put("errorMessage", "El trabajador con ID: '" + id + "' no existe en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("error", false);
        response.put("result", trabajador);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Devuelve los trabajos pendientes buscando un usuario por ID y contraseña
    @GetMapping("/trabajadores/{id}/{contraseña}")
    public ResponseEntity<?> indexOneByIdAndContraseña(@PathVariable String id, @PathVariable String contraseña) {
        Trabajador trabajador;
        Map<String, Object> response = new HashMap<>();

        try {
            if (trabajadorService.findById(id) == null) {
                response.put("error", true);
                response.put("errorMessage", "El trabajador con ID: '" + id + "' no existe en la base de datos");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            
            trabajador = trabajadorService.findByIdAndPass(id, contraseña);
        } catch (DataAccessException e) {
            response.put("error", true);
            response.put("errorMessage", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (trabajador == null) {
            response.put("error", true);
            response.put("errorMessage", "Las credenciales son incorrectas");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return indexOneTrabajosPendientes(id);
    }

    // Devuelve una lista de trabajadores por especialidad
    @GetMapping("/trabajadores/especialidad/{especialidad}")
    public ResponseEntity<?> indexByEspecialidad(@PathVariable String especialidad) {
        List<Trabajador> trabajadores;
        Map<String, Object> response = new HashMap<>();

        try {
            trabajadores = trabajadorService.findBySpeciality(especialidad);
        } catch (DataAccessException e) {
            response.put("error", true);
            response.put("errorMessage", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("error", false);
        response.put("result", trabajadores);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Devuelve los trabajos de un trabajador
    @GetMapping("/trabajadores/{id}/trabajos")
    public ResponseEntity<?> indexOneTrabajos(@PathVariable String id) {
        List<Trabajo> trabajos;
        Map<String, Object> response = new HashMap<>();

        try {
            if (trabajadorService.findById(id) == null) {
                response.put("error", true);
                response.put("errorMessage", "El trabajador con ID: '" + id + "' no existe en la base de datos");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            Trabajador trabajador = trabajadorService.findById(id);
            if (trabajador == null) {
                response.put("error", true);
                response.put("errorMessage", "El trabajador con ID: '" + id + "' no existe en la base de datos");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            
            trabajos = trabajador.getTrabajos().stream().toList();
        } catch (DataAccessException e) {
            response.put("error", true);
            response.put("errorMessage", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("error", false);
        response.put("result", trabajos);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Devuelve los trabajos pendientes de un trabajador
    @GetMapping("/trabajadores/{id}/trabajos/pendientes")
    public ResponseEntity<?> indexOneTrabajosPendientes(@PathVariable String id) {
        List<Trabajo> trabajosPendientes;
        Map<String, Object> response = new HashMap<>();

        try {
            if (trabajadorService.findById(id) == null) {
                response.put("error", true);
                response.put("errorMessage", "El trabajador con ID: '" + id + "' no existe en la base de datos");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            trabajosPendientes = trabajoService.findPendientesPorTrabajador(id);
        } catch (DataAccessException e) {
            response.put("error", true);
            response.put("errorMessage", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("error", false);
        response.put("result", trabajosPendientes);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/trabajadores/{id}/trabajos/pendientes/prioridad")
    public ResponseEntity<?> indexOneTrabajosPendientesOrderByPrioridad(@PathVariable String id) {
        List<Trabajo> trabajosPendientes;
        Map<String, Object> response = new HashMap<>();

        try {
            if (trabajadorService.findById(id) == null) {
                response.put("error", true);
                response.put("errorMessage", "El trabajador con ID: '" + id + "' no existe en la base de datos");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
            
            trabajosPendientes = trabajoService.findPendientesPorTrabajadorOrderByPrioridadAsc(id);
        } catch (DataAccessException e) {
            response.put("error", true);
            response.put("errorMessage", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("error", false);
        response.put("result", trabajosPendientes);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/trabajadores/{id}/trabajos/pendientes/{prioridad}")
    public ResponseEntity<?> indexOneTrabajosPendientesByPrioridad(@PathVariable String id, @PathVariable BigDecimal prioridad) {
        List<Trabajo> trabajosPendientes;
        Map<String, Object> response = new HashMap<>();

        try {
            if (trabajadorService.findById(id) == null) {
                response.put("error", true);
                response.put("errorMessage", "El trabajador con ID: '" + id + "' no existe en la base de datos");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            trabajosPendientes = trabajoService.findPendientesPorTrabajadorYPrioridad(id, prioridad);
        } catch (DataAccessException e) {
            response.put("error", true);
            response.put("errorMessage", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("error", false);
        response.put("result", trabajosPendientes);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/trabajadores/sintrabajospendientes")
    public ResponseEntity<?> indexTrabajadoresSinTrabajosPendientes() {
        List<Trabajador> trabajadores;
        Map<String, Object> response = new HashMap<>();

        try {
            trabajadores = trabajadorService.findWithoutPendingTasks();
        } catch (DataAccessException e) {
            response.put("error", true);
            response.put("errorMessage", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("error", false);
        response.put("result", trabajadores);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Devuelve los trabajos completados de un trabajador
    @GetMapping("/trabajadores/{id}/trabajos/completados")
    public ResponseEntity<?> indexOneTrabajosCompletadosEntreFechas(
            @PathVariable
            String id,
            @RequestParam (required = false) @DateTimeFormat()
            LocalDate fechaIni,
            @RequestParam (required = false) @DateTimeFormat()
            LocalDate fechaFin
    ) {
        List<Trabajo> trabajosCompletados;
        Map<String, Object> response = new HashMap<>();

        try {
            if (trabajadorService.findById(id) == null) {
                response.put("error", true);
                response.put("errorMessage", "El trabajador con ID: '" + id + "' no existe en la base de datos");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            if (fechaIni == null && fechaFin == null) {
                trabajosCompletados = trabajoService.findCompletadosPorTrabajador(id);
            } else if (fechaIni != null & fechaFin == null) {
                trabajosCompletados = trabajoService.findCompletadosPorTrabajadorEntreFechas(id, fechaIni, LocalDate.now());
            } else if(fechaIni == null) {
                trabajosCompletados = trabajoService.findCompletadosPorTrabajadorHastaFecha(id, fechaFin);
            } else {
                trabajosCompletados = trabajoService.findCompletadosPorTrabajadorEntreFechas(id, fechaIni, fechaFin);
            }
        } catch (DataAccessException e) {
            response.put("error", true);
            response.put("errorMessage", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("error", false);
        response.put("result", trabajosCompletados);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Crea un trabajador
    @PostMapping("/trabajadores")
    public ResponseEntity<?> create(@Valid @RequestBody Trabajador trabajador, BindingResult result) {
        Trabajador newTrabajador;
        Map<String, Object> response = new HashMap<>();
        List<String> errors = new ArrayList<>();

        if (showErrors(result, response, errors)) return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        if (trabajadorService.existsById(trabajador.getIdTrabajador())) {
            response.put("error", true);
            errors.add("Ya existe un trabajador con el ID: " + trabajador.getIdTrabajador());
            response.put("errorsList", errors);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        if (trabajadorService.existsByEmail(trabajador.getEmail())) {
            response.put("error", true);
            errors.add("Ya existe un trabajador con el email: " + trabajador.getEmail());
            response.put("errorsList", errors);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        if (trabajadorService.existsByDni(trabajador.getDni())) {
            response.put("error", true);
            errors.add("Ya existe un trabajador con el DNI: " + trabajador.getDni());
            response.put("errorsList", errors);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        try {
            newTrabajador = trabajadorService.save(trabajador);
        } catch (DataAccessException e) {
            response.put("error", true);
            errors.add(e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            response.put("errorsList", errors);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

            response.put("error", false);
            response.put("result", newTrabajador);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Actualiza un trabajador
    @PutMapping("/trabajadores/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> update(@Valid @RequestBody Trabajador trabajador, @PathVariable String id, BindingResult result) {
        Trabajador currentTrabajador = trabajadorService.findById(id);
        Trabajador updatedTrabajador;
        Map<String, Object> response = new HashMap<>();
        List<String> errors = new ArrayList<>();

        if (showErrors(result, response, errors)) return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        if (currentTrabajador == null) {
            response.put("error", true);
            errors.add("No se pudo editar, el trabajador con ID '" + id + "' no existe en la base de datos");
            response.put("errorsList", errors);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        if (!currentTrabajador.getDni().equals(trabajador.getDni())) {
            if (trabajadorService.existsByDni(trabajador.getDni())) {
                response.put("error", true);
                errors.add("Ya existe un trabajador con el DNI: " + trabajador.getDni());
                response.put("errorsList", errors);
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
        }

        if (!currentTrabajador.getEmail().equals(trabajador.getEmail())) {
            if (trabajadorService.existsByEmail(trabajador.getEmail())) {
                response.put("error", true);
                errors.add("Ya existe un trabajador con el email: " + trabajador.getEmail());
                response.put("errorsList", errors);
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
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
            response.put("error", true);
            errors.add(e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            response.put("errorsList", errors);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("error", false);
        response.put("result", updatedTrabajador);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Actualiza la contraseña de un trabajador
    @PutMapping("/trabajadores/{id}/password/{password}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> updatePassword(@PathVariable String id, @PathVariable String password) {
        Trabajador currentTrabajador = trabajadorService.findById(id);
        Map<String, Object> response = new HashMap<>();

        if (currentTrabajador == null) {
            response.put("error", true);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            currentTrabajador.setContraseña(password);
            trabajadorService.save(currentTrabajador);
        } catch (DataAccessException e) {
            response.put("error", true);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("error", false);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Elimina un trabajador
    @DeleteMapping("/trabajadores/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable String id) {

        Map<String, Object> response = new HashMap<>();

        Trabajador currentTrabajador = trabajadorService.findById(id);

        if(currentTrabajador == null) {
            response.put("error", true);
            response.put("errorMessage", "El trabajador con id '" + id + "' no existe en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            trabajadorService.delete(id);
        } catch (DataAccessException e) {
            response.put("error", true);
            response.put("errorMessage", e.getMessage() + ": " + e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("error", false);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Método auxiliar para mostrar errores
    private boolean showErrors(BindingResult result, Map<String, Object> response, List<String> errors) {
        if (result.hasErrors()) {
            errors = result.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();

            response.put("error", true);
            response.put("errorsList", errors);
            return true;
        }
        return false;
    }
}
