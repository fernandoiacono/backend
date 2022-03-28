package com.argprograma.portfolio.repositories;

import com.argprograma.portfolio.entities.PortfolioUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPortfolioUserRepository extends JpaRepository<PortfolioUser, Long> {

    public Optional<PortfolioUser> findByEmail(String email);

    public Optional<PortfolioUser> findByUsernameOrEmail(String username, String email);

    public Optional<PortfolioUser> findByUsername(String username);

    public Boolean existsByUsername(String username);

    public Boolean existsByEmail(String email);

}
