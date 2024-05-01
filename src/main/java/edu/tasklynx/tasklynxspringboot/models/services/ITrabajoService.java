package edu.tasklynx.tasklynxspringboot.models.services;

import edu.tasklynx.tasklynxspringboot.models.entity.Trabajo;

import java.util.List;

public interface ITrabajoService {
    public List<Trabajo> findAll();
    public Trabajo findById(String id);
    public Trabajo save(Trabajo trabajo);
    public void delete(String id);
}
