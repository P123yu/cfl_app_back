package com.cfl.cfl_project.repository;

import com.cfl.cfl_project.model.UserManual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserManualRepository extends JpaRepository<UserManual,Long> {
    Optional<UserManual> findById(Long Id);
}
