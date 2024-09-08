package com.cfl.cfl_project.repository;

import com.cfl.cfl_project.model.LogBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogBookRepository extends JpaRepository<LogBook,Long> {
    List<LogBook> findByEmpId(Long empId);
}
