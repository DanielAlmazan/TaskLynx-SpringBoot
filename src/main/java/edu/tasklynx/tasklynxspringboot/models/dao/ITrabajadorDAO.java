package edu.tasklynx.tasklynxspringboot.models.dao;

import edu.tasklynx.tasklynxspringboot.models.entity.Trabajador;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ITrabajadorDAO extends CrudRepository<Trabajador, String> {
    Trabajador findByIdTrabajadorAndContraseña(@Param("id") String id, @Param("contraseña") String pass);
    List<Trabajador> findBySpeciality(@Param("especialidad") String especialidad);
    boolean existsByEmail(@Param("email") String email);
    boolean existsByDni(@Param("dni") String dni);
    boolean existsById(@Param("id") String id);
}
