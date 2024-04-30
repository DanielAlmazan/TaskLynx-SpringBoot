package edu.danielalmazan.tasklynxspringboot.models.dao;

import edu.danielalmazan.tasklynxspringboot.models.entity.Trabajo;
import org.springframework.data.repository.CrudRepository;

public interface ITrabajoDAO extends CrudRepository<Trabajo, String> {
}
