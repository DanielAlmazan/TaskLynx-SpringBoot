package edu.tasklynx.tasklynxspringboot.models.services;

import edu.tasklynx.tasklynxspringboot.models.entity.Trabajador;

import java.util.List;

public interface ITrabajadorService {
    List<Trabajador> findAll();
    Trabajador findById(String id);
    Trabajador findByNameAndPass(String user, String pass);
    List<Trabajador> findBySpeciality(String speciality);
    Trabajador save(Trabajador trabajador);
    void delete(String id);
}