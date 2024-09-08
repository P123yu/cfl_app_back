package com.cfl.cfl_project.repository;

import com.cfl.cfl_project.model.HR;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HRRepository extends JpaRepository<HR,Long> {
    Boolean existsByHrEmail(String hrEmail);
    HR findByHrEmail(String hrEmail);
    HR findByHrId(Long hrId);
//    Mentor findByEmpId(Long empId);
}
