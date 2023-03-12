package com.ingeniemos.controlclientes.servicio;

import com.ingeniemos.controlclientes.dao.IPersonaDao;
import com.ingeniemos.controlclientes.dominio.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class PersonaServiceImpl implements  PersonaService{

    @Autowired
    private IPersonaDao personaDao;
    @Override
    @Transactional(readOnly = true) //solo se lee información de la BD
    public List<Persona> listarPersonas() {
        return (List<Persona>) personaDao.findAll();
    }

    @Override
    @Transactional
    public void guardar(Persona persona) {
        personaDao.save(persona);
    }

    @Override
    @Transactional
    public void eliminar(Persona persona) {
        personaDao.delete(persona);
    }

    @Override
    @Transactional(readOnly = true)
    public Persona encontrarPersona(Persona persona) {
        return personaDao.findById(persona.getId()).orElse(null);
    }
}
