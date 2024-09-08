package com.cfl.cfl_project.repository;

import com.cfl.cfl_project.model.Register;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeneralRepository extends JpaRepository<Register,Long> {
    List<Register>findByUserNameContaining(String userName);
}
