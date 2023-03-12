package com.ingeniemos.controlclientes.dao;

import com.ingeniemos.controlclientes.dominio.Persona;
import org.springframework.data.repository.CrudRepository;

public interface IPersonaDao extends CrudRepository<Persona, Long> {
}
