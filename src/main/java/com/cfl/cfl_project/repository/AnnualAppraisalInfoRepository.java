package com.cfl.cfl_project.repository;

import com.cfl.cfl_project.model.AnnualAppraisalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnualAppraisalInfoRepository extends JpaRepository<AnnualAppraisalInfo,Long> {
    boolean existsByEmpId(Long empId);
    List<AnnualAppraisalInfo>findByYear(Long year);
}
