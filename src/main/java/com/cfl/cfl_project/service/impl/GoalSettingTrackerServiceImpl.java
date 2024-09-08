package com.cfl.cfl_project.service.impl;

import com.cfl.cfl_project.dto.CflGoalSettingTrackerCflTableDTO;
import com.cfl.cfl_project.dto.CflSkillCflTableDTO;
import com.cfl.cfl_project.model.Cfl;
import com.cfl.cfl_project.model.CflSkill;
import com.cfl.cfl_project.model.GoalSettingTracker;
import com.cfl.cfl_project.repository.CflRepository;
import com.cfl.cfl_project.service.GoalSettingTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import com.cfl.cfl_project.repository.GoalSettingTrackerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoalSettingTrackerServiceImpl implements GoalSettingTrackerService {

    @Autowired
    private GoalSettingTrackerRepository goalSettingTrackerRepository;

    @Override
    public GoalSettingTracker getTrackingInfoByCflIdAndQuarter(Long cflId, String quarter) {
        return goalSettingTrackerRepository.findByCflIdAndQuarter(cflId, quarter);
    }

    @Autowired
    private CflRepository cflRepository;

    @Override
    public List<CflGoalSettingTrackerCflTableDTO> getAllGoalSettingTracker() {

        List<CflGoalSettingTrackerCflTableDTO> cflGoalSettingTrackerCflTableDTOS = new ArrayList<>();
        List<GoalSettingTracker> goalSettingTrackers = goalSettingTrackerRepository.findAll();
        for (GoalSettingTracker goalSettingTracker : goalSettingTrackers) {
            CflGoalSettingTrackerCflTableDTO cflGoalSettingTrackerCflTableDTO = new CflGoalSettingTrackerCflTableDTO();
            Cfl cfl = cflRepository.findByEmpId(goalSettingTracker.getCflId());
            cflGoalSettingTrackerCflTableDTO.setEmpId(goalSettingTracker.getCflId());

            cflGoalSettingTrackerCflTableDTO.setCflName(cfl.getCflFirstName());
            cflGoalSettingTrackerCflTableDTO.setCflEmail(cfl.getCflEmail());
            cflGoalSettingTrackerCflTableDTO.setCflDepartment(cfl.getCflDepartment());
            cflGoalSettingTrackerCflTableDTO.setCflLocation(cfl.getCflLocation());
            cflGoalSettingTrackerCflTableDTO.setReportingManagerName(cfl.getReportingManager());
            cflGoalSettingTrackerCflTableDTO.setReportingManagerEmail(cfl.getReportingManagerMail());
            cflGoalSettingTrackerCflTableDTO.setCflDepartment(cfl.getCflDepartment());
            cflGoalSettingTrackerCflTableDTO.setCflSubDepartment(cfl.getCflSubDepartment());

            cflGoalSettingTrackerCflTableDTO.setQuarter(goalSettingTracker.getQuarter());
            cflGoalSettingTrackerCflTableDTO.setGoalInitiatedFromHrToManager(goalSettingTracker.getGoalInitiatedFromHrToManager());
            cflGoalSettingTrackerCflTableDTO.setResponseSendByManagerToCfl(goalSettingTracker.getResponseSendByManagerToCfl());
            cflGoalSettingTrackerCflTableDTO.setResponseSendByManagerToHr(goalSettingTracker.getResponseSendByManagerToHr());
            cflGoalSettingTrackerCflTableDTOS.add(cflGoalSettingTrackerCflTableDTO);
        }
        return cflGoalSettingTrackerCflTableDTOS;
    }

    @Override
    public List<GoalSettingTracker> getAllGoalSettingTrackerByQuarterAndYear(String Quarter, Long year) {
        return goalSettingTrackerRepository.findByQuarterAndYear(Quarter,year);
    }
}
