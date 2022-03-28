package com.argprograma.portfolio.repositories;

import com.argprograma.portfolio.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    public Optional<Role> findByName(String name);
}
