package com.cfl.cfl_project.repository;

import com.cfl.cfl_project.model.Refresh;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshRepository extends JpaRepository<Refresh,Long> {
    Optional<Refresh> findByRefreshToken(String refreshToken);

}
