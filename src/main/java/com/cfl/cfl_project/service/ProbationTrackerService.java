package com.cfl.cfl_project.service;

import com.cfl.cfl_project.dto.CflGoalSettingTrackerCflTableDTO;
import com.cfl.cfl_project.dto.ProbationTrackerCflTableDTO;
import com.cfl.cfl_project.model.GoalSettingTracker;
import com.cfl.cfl_project.model.ProbationTracker;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProbationTrackerService {
    // track probation settings
    ProbationTracker getTrackingInfoByCflId(Long cflId);


    // get all goal setting
    List<ProbationTrackerCflTableDTO> getProbationTracker();
}
