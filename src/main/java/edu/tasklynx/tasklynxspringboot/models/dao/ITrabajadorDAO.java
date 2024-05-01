package edu.tasklynx.tasklynxspringboot.models.dao;

import edu.tasklynx.tasklynxspringboot.models.entity.Trabajador;
import org.springframework.data.repository.CrudRepository;

public interface ITrabajadorDAO extends CrudRepository<Trabajador, String> {
}
