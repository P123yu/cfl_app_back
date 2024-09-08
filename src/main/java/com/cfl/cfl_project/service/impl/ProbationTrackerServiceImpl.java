package com.cfl.cfl_project.service.impl;

import com.cfl.cfl_project.dto.CflGoalSettingTrackerCflTableDTO;
import com.cfl.cfl_project.dto.ProbationTrackerCflTableDTO;
import com.cfl.cfl_project.model.Cfl;
import com.cfl.cfl_project.model.GoalSettingTracker;
import com.cfl.cfl_project.model.ProbationTracker;
import com.cfl.cfl_project.repository.CflRepository;
import com.cfl.cfl_project.repository.ProbationTrackerRepository;
import com.cfl.cfl_project.service.ProbationTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProbationTrackerServiceImpl implements ProbationTrackerService {

    @Autowired
    private ProbationTrackerRepository probationTrackerRepository;

    @Override
    public ProbationTracker getTrackingInfoByCflId(Long cflId) {
        return probationTrackerRepository.findByCflId(cflId);
    }


    @Autowired
    private CflRepository cflRepository;

    @Override
    public List<ProbationTrackerCflTableDTO> getProbationTracker() {
        List<ProbationTrackerCflTableDTO> probationTrackerCflTableDTOS = new ArrayList<>();
        List<ProbationTracker> probationTrackers = probationTrackerRepository.findAll();
        for (ProbationTracker probationTracker : probationTrackers) {
            ProbationTrackerCflTableDTO probationTrackerCflTableDTO = new ProbationTrackerCflTableDTO();
            Cfl cfl = cflRepository.findByEmpId(probationTracker.getCflId());
            probationTrackerCflTableDTO.setEmpId(probationTracker.getCflId());

            probationTrackerCflTableDTO.setCflName(cfl.getCflFirstName());
            probationTrackerCflTableDTO.setCflEmail(cfl.getCflEmail());
            probationTrackerCflTableDTO.setCflDepartment(cfl.getCflDepartment());
            probationTrackerCflTableDTO.setCflLocation(cfl.getCflLocation());
            probationTrackerCflTableDTO.setReportingManagerName(cfl.getReportingManager());
            probationTrackerCflTableDTO.setReportingManagerEmail(cfl.getReportingManagerMail());
            probationTrackerCflTableDTO.setCflDepartment(cfl.getCflDepartment());
            probationTrackerCflTableDTO.setCflSubDepartment(cfl.getCflSubDepartment());

            probationTrackerCflTableDTO.setProbationInitiatedFromHrToManager(probationTracker.getProbationInitiatedFromHrToManager());
            probationTrackerCflTableDTO.setResponseSendByManagerToCfl(probationTracker.getResponseSendByManagerToCfl());
            probationTrackerCflTableDTO.setResponseSendByManagerToHr(probationTracker.getResponseSendByManagerToHr());

            probationTrackerCflTableDTOS.add(probationTrackerCflTableDTO);
        }
        return probationTrackerCflTableDTOS;
    }

}
