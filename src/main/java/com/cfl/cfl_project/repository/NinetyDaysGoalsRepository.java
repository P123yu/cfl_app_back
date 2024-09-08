package com.cfl.cfl_project.repository;

import com.cfl.cfl_project.model.NinetyDaysGoals;
import com.cfl.cfl_project.model.SixtyDaysGoals;
import com.cfl.cfl_project.model.ThirtyDaysGoals;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NinetyDaysGoalsRepository extends JpaRepository<NinetyDaysGoals,Long> {
    List<NinetyDaysGoals> findByEmpIdAndQuarter(Long empId, String Quarter);
    List<NinetyDaysGoals> findByEmpId(Long empId);
}
