package edu.tasklynx.tasklynxspringboot.models.dao;

import edu.tasklynx.tasklynxspringboot.models.entity.Trabajo;
import org.springframework.data.repository.CrudRepository;

public interface ITrabajoDAO extends CrudRepository<Trabajo, String> {
}
