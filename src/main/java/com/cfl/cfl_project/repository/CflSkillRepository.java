package com.cfl.cfl_project.repository;

import com.cfl.cfl_project.model.CflSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CflSkillRepository extends JpaRepository<CflSkill,Long> {
    CflSkill findByEmpIdAndQuarterAndYear(Long empId,String quarter,Long Year);
    List<CflSkill> findByYear(Long year);

    List<CflSkill> findByEmpId(Long empId);
}
