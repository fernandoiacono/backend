package com.argprograma.portfolio.security.repositories;

import com.argprograma.portfolio.security.entities.PortfolioUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPortfolioUserRepository extends JpaRepository<PortfolioUser, Long> {

    Optional<PortfolioUser> findByEmail(String email);

    Optional<PortfolioUser> findByUsernameOrEmail(String username, String email);

    Optional<PortfolioUser> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}