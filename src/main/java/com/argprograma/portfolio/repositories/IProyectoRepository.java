package com.argprograma.portfolio.repositories;

import com.argprograma.portfolio.entities.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProyectoRepository extends JpaRepository<Proyecto, Long> { }