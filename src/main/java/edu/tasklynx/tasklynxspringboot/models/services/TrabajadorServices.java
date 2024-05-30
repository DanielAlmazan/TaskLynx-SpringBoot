package edu.tasklynx.tasklynxspringboot.models.services;

import edu.tasklynx.tasklynxspringboot.models.dao.ITrabajadorDAO;
import edu.tasklynx.tasklynxspringboot.models.entity.Trabajador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class TrabajadorServices implements ITrabajadorService {
    @Autowired
    ITrabajadorDAO trabajadorDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Trabajador> findAll() {
        return ((List<Trabajador>) trabajadorDAO.findAll()).stream()
                .sorted(Comparator.comparing(Trabajador::getIdTrabajador)).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Trabajador findById(String id) {
        return trabajadorDAO.findById(id).orElse(null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Trabajador findByIdAndPass(String id, String pass) {
        return trabajadorDAO.findByIdTrabajadorAndContraseña(id, pass);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Trabajador> findBySpeciality(String speciality) {
        return trabajadorDAO.findBySpeciality(speciality);
    }
    
    @Override
    @Transactional
    public Trabajador save(Trabajador trabajador) {
        return trabajadorDAO.save(trabajador);
    }

    @Override
    @Transactional
    public void delete(String id) {
        trabajadorDAO.deleteById(id);
    }

    public List<Trabajador> findWithoutPendingTasks() {
        return trabajadorDAO.findWithoutPendingTasks();
    }

    @Override
    public boolean existsById(String id) {
        return trabajadorDAO.existsById(id);
    }

    public boolean existsByEmail(String email) {
        return trabajadorDAO.existsByEmail(email);
    }

    public boolean existsByDni(String dni) {
        return trabajadorDAO.existsByDni(dni);
    }
}
