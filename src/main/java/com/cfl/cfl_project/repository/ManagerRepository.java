package com.cfl.cfl_project.repository;

import com.cfl.cfl_project.model.Manager;
import com.cfl.cfl_project.model.Mentor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManagerRepository extends JpaRepository<Manager,Long> {
    Manager findByManagerEmail(String managerEmail);
    Boolean existsByManagerEmail(String managerEmail);
    Manager findByManagerId(Long managerId);
}
