package com.cfl.cfl_project.repository;

import com.cfl.cfl_project.model.Cfl;
import com.cfl.cfl_project.model.MailHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MailHistoryRepository extends JpaRepository<MailHistory,Long> {
    List<MailHistory>findByEmpId(Long empId);

}
