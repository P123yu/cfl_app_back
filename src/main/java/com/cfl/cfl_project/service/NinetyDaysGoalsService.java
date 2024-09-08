package com.cfl.cfl_project.service;

import com.cfl.cfl_project.model.NinetyDaysGoals;
import com.cfl.cfl_project.model.SixtyDaysGoals;
import com.cfl.cfl_project.model.ThirtyDaysGoals;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NinetyDaysGoalsService {
    // create
    List<NinetyDaysGoals> createNinetyDaysGoals(Long empId,String quarter, List<NinetyDaysGoals>ninetyDaysGoals);

    // get
    List<NinetyDaysGoals>getNinetyDaysGoalsByEmpIdAndQuarter(Long empId,String quarter);

    // update
    List<NinetyDaysGoals> updateNinetyDaysGoals(List<NinetyDaysGoals>ninetyDaysGoals);
}
