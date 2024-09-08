package com.cfl.cfl_project.service;

import com.cfl.cfl_project.dto.CflGoalSettingTrackerCflTableDTO;
import com.cfl.cfl_project.model.GoalSettingTracker;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GoalSettingTrackerService {

    // track goal settings
    GoalSettingTracker getTrackingInfoByCflIdAndQuarter(Long cflId,String quarter);

    // get all goal setting
    List<CflGoalSettingTrackerCflTableDTO>getAllGoalSettingTracker();


    List<GoalSettingTracker>getAllGoalSettingTrackerByQuarterAndYear(String Quarter,Long year);


}
