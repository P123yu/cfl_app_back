package com.cfl.cfl_project.repository;

import com.cfl.cfl_project.model.CflRoles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CflRolesRepository extends JpaRepository<CflRoles,Long> {
    CflRoles findByYear(Long year);
}
