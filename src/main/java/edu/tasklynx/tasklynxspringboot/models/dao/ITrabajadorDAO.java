package edu.tasklynx.tasklynxspringboot.models.dao;

import edu.tasklynx.tasklynxspringboot.models.entity.Trabajador;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ITrabajadorDAO extends CrudRepository<Trabajador, String> {
    Trabajador findByNameAndPass(@Param("nombre") String name, @Param("contraseña") String pass);
    List<Trabajador> findBySpeciality(@Param("especialidad") String category);
}
