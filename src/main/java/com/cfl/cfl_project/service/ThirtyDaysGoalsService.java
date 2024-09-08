package com.cfl.cfl_project.service;

import com.cfl.cfl_project.dto.CflGoalSettingCflTableDTO;
import com.cfl.cfl_project.model.SixtyDaysGoals;
import com.cfl.cfl_project.model.ThirtyDaysGoals;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ThirtyDaysGoalsService {

    // create
    List<ThirtyDaysGoals>createThirtyDaysGoals(Long empId,String quarter,List<ThirtyDaysGoals>thirtyDaysGoals);

    // get
    List<ThirtyDaysGoals>getThirtyDaysGoalsByEmpIdAndQuarter(Long empId,String quarter,Long year);

    // update
    List<ThirtyDaysGoals> updateThirtyDaysGoals(List<ThirtyDaysGoals>thirtyDaysGoals);


    // goal setting report
    List<CflGoalSettingCflTableDTO>getAllGoalSetting();

    Integer countTotalQuarter(Long empId,Long year);

    List<String> showListOfCflNameWhoCompletedGoalSetting(String quarter);
}
