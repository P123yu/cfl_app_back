package com.cfl.cfl_project.repository;

import com.cfl.cfl_project.model.Exit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExitRepository extends JpaRepository<Exit,Long> {

    Optional<Exit> findByEmpId(Long empId);
    void deleteByEmpId(Long empId);
    boolean existsByEmpId(Long empId);
}
