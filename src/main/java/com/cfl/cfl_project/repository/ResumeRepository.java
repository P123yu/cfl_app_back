package com.cfl.cfl_project.repository;

import com.cfl.cfl_project.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume,Long> {
    List<Resume> findByEmpId(Long empId);
}
