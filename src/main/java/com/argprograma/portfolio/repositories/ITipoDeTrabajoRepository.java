package com.argprograma.portfolio.repositories;

import com.argprograma.portfolio.entities.TipoEmpleo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITipoDeTrabajoRepository extends JpaRepository<TipoEmpleo, Long> {
}
