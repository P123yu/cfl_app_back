package com.cfl.cfl_project.repository;

import com.cfl.cfl_project.model.ThirtyDaysGoals;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThirtyDaysGoalsRepository extends JpaRepository<ThirtyDaysGoals,Long> {
    List<ThirtyDaysGoals> findByEmpIdAndQuarterAndYear(Long empId,String Quarter,Long year);
    List<ThirtyDaysGoals> findByEmpId(Long empId);

    List<ThirtyDaysGoals> findByEmpIdAndQuarter(Long empId,String quarter);

    List<ThirtyDaysGoals> findByEmpIdAndYear(Long empId,Long year);

    List<ThirtyDaysGoals> findByQuarter(String quarter);


}
