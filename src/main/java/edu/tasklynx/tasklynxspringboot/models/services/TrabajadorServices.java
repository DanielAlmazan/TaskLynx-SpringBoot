package edu.tasklynx.tasklynxspringboot.models.services;

import edu.tasklynx.tasklynxspringboot.models.dao.ITrabajadorDAO;
import edu.tasklynx.tasklynxspringboot.models.entity.Trabajador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrabajadorServices implements ITrabajadorService {
    @Autowired
    ITrabajadorDAO trabajadorDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Trabajador> findAll() {
        return (List<Trabajador>) trabajadorDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Trabajador findById(String id) {
        return trabajadorDAO.findById(id).orElse(null);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Trabajador findByNameAndPass(String user, String pass) {
        return trabajadorDAO.findByNameAndPass(user, pass);
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
}
