package edu.tasklynx.tasklynxspringboot.models.services;

import edu.tasklynx.tasklynxspringboot.models.dao.ITrabajoDAO;
import edu.tasklynx.tasklynxspringboot.models.entity.Trabajo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TrabajoServices implements ITrabajoService{
    @Autowired
    ITrabajoDAO trabajoDAO;

    @Override
    @Transactional(readOnly = true)
    public List<Trabajo> findAll() {
        return (List<Trabajo>) trabajoDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Trabajo findById(String id) {
        return trabajoDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Trabajo save(Trabajo trabajo) {
        return trabajoDAO.save(trabajo);
    }

    @Override
    @Transactional
    public void delete(String id) {
        trabajoDAO.deleteById(id);
    }
}
