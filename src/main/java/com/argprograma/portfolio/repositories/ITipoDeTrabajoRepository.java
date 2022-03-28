package com.argprograma.portfolio.repositories;

import com.argprograma.portfolio.entities.TipoTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITipoDeTrabajoRepository extends JpaRepository<TipoTrabajo, Long> {
}
