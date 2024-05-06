package edu.tasklynx.tasklynxspringboot.controllers;

import edu.tasklynx.tasklynxspringboot.models.entity.Trabajador;
import edu.tasklynx.tasklynxspringboot.models.services.ITrabajadorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin (origins = {"*"})
@RestController
@RequestMapping("/api")
public class TrabajadoresController {

    @Autowired
    private ITrabajadorService trabajadorService;

    @GetMapping("/trabajadores")
    public ResponseEntity<?> indexAll() {
        List<Trabajador> trabajadores;
        Map<String, Object> response = new HashMap<>();

        try {
            trabajadores = trabajadorService.findAll();
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (trabajadores.isEmpty()) {
            response.put("mensaje", "No existen trabajadores en la base de datos");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
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
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (trabajador == null) {
            response.put("mensaje", "El trabajador con ID: ".concat(id.concat(" no existe en la base de datos")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(trabajador, HttpStatus.OK);
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
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
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
            response.put("mensaje", "Error: no se pudo editar, el trabajador con ID: ".concat(id.concat(" no existe en la base de datos")));
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
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
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
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
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