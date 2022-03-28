package com.argprograma.portfolio.repositories;

import com.argprograma.portfolio.entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPersonaRepository extends JpaRepository<Persona, Long> { }