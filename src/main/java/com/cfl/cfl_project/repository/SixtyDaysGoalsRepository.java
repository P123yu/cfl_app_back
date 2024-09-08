package com.cfl.cfl_project.repository;

import com.cfl.cfl_project.model.SixtyDaysGoals;
import com.cfl.cfl_project.model.ThirtyDaysGoals;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SixtyDaysGoalsRepository extends JpaRepository<SixtyDaysGoals,Long> {
    List<SixtyDaysGoals> findByEmpIdAndQuarter(Long empId,String Quarter);
    List<SixtyDaysGoals> findByEmpId(Long empId);
}
