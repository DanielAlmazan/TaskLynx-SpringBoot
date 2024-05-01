package edu.tasklynx.tasklynxspringboot.models.services;

import edu.tasklynx.tasklynxspringboot.models.entity.Trabajador;

import java.util.List;

public interface ITrabajadorService {
    List<Trabajador> findAll();
    Trabajador findById(String id);
    Trabajador save(Trabajador trabajador);
    void delete(String id);
}