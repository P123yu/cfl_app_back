package com.cfl.cfl_project.service.impl;

import com.cfl.cfl_project.dto.CflGoalSettingCflTableDTO;
import com.cfl.cfl_project.dto.CflSkillCflTableDTO;
import com.cfl.cfl_project.model.*;
import com.cfl.cfl_project.repository.CflRepository;
import com.cfl.cfl_project.repository.NinetyDaysGoalsRepository;
import com.cfl.cfl_project.repository.SixtyDaysGoalsRepository;
import com.cfl.cfl_project.service.ThirtyDaysGoalsService;
import org.springframework.beans.factory.annotation.Autowired;
import com.cfl.cfl_project.repository.ThirtyDaysGoalsRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThirtyDaysGoalsServiceImpl implements ThirtyDaysGoalsService {

    @Autowired
    private ThirtyDaysGoalsRepository thirtyDaysGoalsRepository;



    //
    public static String getQuarter() {
        LocalDate date=LocalDate.now();
        int month = date.getMonthValue();

        if (month >= 1 && month <= 3) {
            return "Q4";
        } else if (month >= 4 && month <= 6) {
            return "Q1";
        } else if (month >= 7 && month <= 9) {
            return "Q2";
        } else if (month >= 10 && month <= 12) {
            return "Q3";
        } else {
            throw new IllegalArgumentException("Invalid month: " + month);
        }
    }


    @Override
    @Transactional
    public List<ThirtyDaysGoals> createThirtyDaysGoals(Long empId,String quarter,List<ThirtyDaysGoals> thirtyDaysGoals) {

        Cfl cfl=cflRepository.findByEmpId(empId);


        List<ThirtyDaysGoals>thirtyDaysGoalsLists=new ArrayList<>();
        for(ThirtyDaysGoals i: thirtyDaysGoals){
            ThirtyDaysGoals thirtyDaysGoals1=new ThirtyDaysGoals();
            thirtyDaysGoals1.setEmpId(empId);
            thirtyDaysGoals1.setStatus(i.getStatus());
            thirtyDaysGoals1.setQuarter(quarter);
            thirtyDaysGoals1.setGoal(i.getGoal());
            thirtyDaysGoals1.setDeliverable(i.getDeliverable());
            thirtyDaysGoals1.setWeightage(i.getWeightage());
            thirtyDaysGoals1.setYear(Long.valueOf(cfl.getYear()));
            thirtyDaysGoalsLists.add(thirtyDaysGoals1);
        }
        System.out.println(thirtyDaysGoalsLists+"thirtyDaysGoalsLists");
        return thirtyDaysGoalsRepository.saveAll(thirtyDaysGoalsLists);
    }

    @Override
    public List<ThirtyDaysGoals> updateThirtyDaysGoals(List<ThirtyDaysGoals> thirtyDaysGoals) {
        return thirtyDaysGoalsRepository.saveAll(thirtyDaysGoals.stream()
                .peek(i-> i.setStatus("approved")).collect(Collectors.toList()));
    }


    @Override
    public List<ThirtyDaysGoals> getThirtyDaysGoalsByEmpIdAndQuarter(Long empId, String quarter, Long year) {
        return thirtyDaysGoalsRepository.findByEmpIdAndQuarterAndYear(empId, quarter, year);
    }


    @Autowired
    private CflRepository cflRepository;

    @Autowired
    private SixtyDaysGoalsRepository sixtyDaysGoalsRepository;

    @Autowired
    private NinetyDaysGoalsRepository ninetyDaysGoalsRepository;
//
//
//    @Override
//    public List<CflGoalSettingCflTableDTO> getAllGoalSetting() {
//        List<CflGoalSettingCflTableDTO> cflGoalSettingCflTableDTOS = new ArrayList<>();
//
//        List<ThirtyDaysGoals> thirtyDaysGoals = thirtyDaysGoalsRepository.findAll();
//        List<SixtyDaysGoals> sixtyDaysGoals = sixtyDaysGoalsRepository.findAll();
//        List<NinetyDaysGoals> ninetyDaysGoals = ninetyDaysGoalsRepository.findAll();
//
//        // 30 Days Goals
//        for (ThirtyDaysGoals thirtyDaysGoalsObj : thirtyDaysGoals) {
//            CflGoalSettingCflTableDTO cflThirtyDaysGoalSettingCflTableDTO = new CflGoalSettingCflTableDTO();
//            Cfl cfl = cflRepository.findByEmpId(thirtyDaysGoalsObj.getEmpId());
//
//            if (cfl != null) {
//                cflThirtyDaysGoalSettingCflTableDTO.setEmpId(cfl.getEmpId());
//                cflThirtyDaysGoalSettingCflTableDTO.setCflName(cfl.getCflFirstName());
//                cflThirtyDaysGoalSettingCflTableDTO.setCflEmail(cfl.getCflEmail());
//                cflThirtyDaysGoalSettingCflTableDTO.setCflDepartment(cfl.getCflDepartment());
//                cflThirtyDaysGoalSettingCflTableDTO.setCflLocation(cfl.getCflLocation());
//                cflThirtyDaysGoalSettingCflTableDTO.setReportingManagerName(cfl.getReportingManager());
//                cflThirtyDaysGoalSettingCflTableDTO.setReportingManagerEmail(cfl.getReportingManagerMail());
//                cflThirtyDaysGoalSettingCflTableDTO.setCflSubDepartment(cfl.getCflSubDepartment());
//            }
//
//            cflThirtyDaysGoalSettingCflTableDTO.setThirtyDaysGoal(thirtyDaysGoalsObj.getGoal());
//            cflThirtyDaysGoalSettingCflTableDTO.setThirtyDaysDeliverable(thirtyDaysGoalsObj.getDeliverable());
//            cflThirtyDaysGoalSettingCflTableDTO.setThirtyDaysStatus(thirtyDaysGoalsObj.getStatus());
//            cflThirtyDaysGoalSettingCflTableDTO.setThirtyDaysWeightage(thirtyDaysGoalsObj.getWeightage());
//            cflThirtyDaysGoalSettingCflTableDTO.setThirtyDaysQuarter(thirtyDaysGoalsObj.getQuarter());
//
//            cflGoalSettingCflTableDTOS.add(cflThirtyDaysGoalSettingCflTableDTO);
//        }
//
//        // 60 Days Goals
//        for (SixtyDaysGoals sixtyDaysGoalsObj : sixtyDaysGoals) {
//            CflGoalSettingCflTableDTO cflSixtyDaysGoalSettingCflTableDTO = new CflGoalSettingCflTableDTO();
//
//            cflSixtyDaysGoalSettingCflTableDTO.setSixtyDaysGoal(sixtyDaysGoalsObj.getGoal());
//            cflSixtyDaysGoalSettingCflTableDTO.setSixtyDaysDeliverable(sixtyDaysGoalsObj.getDeliverable());
//            cflSixtyDaysGoalSettingCflTableDTO.setSixtyDaysStatus(sixtyDaysGoalsObj.getStatus());
//            cflSixtyDaysGoalSettingCflTableDTO.setSixtyDaysWeightage(sixtyDaysGoalsObj.getWeightage());
//            cflSixtyDaysGoalSettingCflTableDTO.setSixtyDaysQuarter(sixtyDaysGoalsObj.getQuarter());
//
//            cflGoalSettingCflTableDTOS.add(cflSixtyDaysGoalSettingCflTableDTO);
//        }
//
//        // 90 Days Goals
//        for (NinetyDaysGoals ninetyDaysGoalsObj : ninetyDaysGoals) {
//            CflGoalSettingCflTableDTO cflNinetyDaysGoalSettingCflTableDTO = new CflGoalSettingCflTableDTO();
//
//            cflNinetyDaysGoalSettingCflTableDTO.setNinetyDaysGoal(ninetyDaysGoalsObj.getGoal());
//            cflNinetyDaysGoalSettingCflTableDTO.setNinetyDaysDeliverable(ninetyDaysGoalsObj.getDeliverable());
//            cflNinetyDaysGoalSettingCflTableDTO.setNinetyDaysStatus(ninetyDaysGoalsObj.getStatus());
//            cflNinetyDaysGoalSettingCflTableDTO.setNinetyDaysWeightage(ninetyDaysGoalsObj.getWeightage());
//            cflNinetyDaysGoalSettingCflTableDTO.setNinetyDaysQuarter(ninetyDaysGoalsObj.getQuarter());
//
//            cflGoalSettingCflTableDTOS.add(cflNinetyDaysGoalSettingCflTableDTO);
//        }
//
//        return cflGoalSettingCflTableDTOS;
//    }
//


    @Override
    public List<CflGoalSettingCflTableDTO> getAllGoalSetting() {
        Map<Long, CflGoalSettingCflTableDTO> goalSettingMap = new HashMap<>();

        List<ThirtyDaysGoals> thirtyDaysGoals = thirtyDaysGoalsRepository.findAll();
        List<SixtyDaysGoals> sixtyDaysGoals = sixtyDaysGoalsRepository.findAll();
        List<NinetyDaysGoals> ninetyDaysGoals = ninetyDaysGoalsRepository.findAll();

        // 30 Days Goals
        for (ThirtyDaysGoals thirtyDaysGoalsObj : thirtyDaysGoals) {
            Long empId = thirtyDaysGoalsObj.getEmpId();
            CflGoalSettingCflTableDTO dto = goalSettingMap.getOrDefault(empId, new CflGoalSettingCflTableDTO());
            Cfl cfl = cflRepository.findByEmpId(empId);

            if (cfl != null) {
                dto.setEmpId(cfl.getEmpId());
                dto.setCflName(cfl.getCflFirstName());
                dto.setCflEmail(cfl.getCflEmail());
                dto.setCflDepartment(cfl.getCflDepartment());
                dto.setCflLocation(cfl.getCflLocation());
                dto.setReportingManagerName(cfl.getReportingManager());
                dto.setReportingManagerEmail(cfl.getReportingManagerMail());
                dto.setCflSubDepartment(cfl.getCflSubDepartment());
            }

            dto.setThirtyDaysGoal(thirtyDaysGoalsObj.getGoal());
            dto.setThirtyDaysDeliverable(thirtyDaysGoalsObj.getDeliverable());
            dto.setThirtyDaysStatus(thirtyDaysGoalsObj.getStatus());
            dto.setThirtyDaysWeightage(thirtyDaysGoalsObj.getWeightage());
            dto.setThirtyDaysQuarter(thirtyDaysGoalsObj.getQuarter());

            goalSettingMap.put(empId, dto);
        }

        // 60 Days Goals
        for (SixtyDaysGoals sixtyDaysGoalsObj : sixtyDaysGoals) {
            Long empId = sixtyDaysGoalsObj.getEmpId();
            CflGoalSettingCflTableDTO dto = goalSettingMap.getOrDefault(empId, new CflGoalSettingCflTableDTO());

            dto.setSixtyDaysGoal(sixtyDaysGoalsObj.getGoal());
            dto.setSixtyDaysDeliverable(sixtyDaysGoalsObj.getDeliverable());
            dto.setSixtyDaysStatus(sixtyDaysGoalsObj.getStatus());
            dto.setSixtyDaysWeightage(sixtyDaysGoalsObj.getWeightage());
            dto.setSixtyDaysQuarter(sixtyDaysGoalsObj.getQuarter());

            goalSettingMap.put(empId, dto);
        }

        // 90 Days Goals
        for (NinetyDaysGoals ninetyDaysGoalsObj : ninetyDaysGoals) {
            Long empId = ninetyDaysGoalsObj.getEmpId();
            CflGoalSettingCflTableDTO dto = goalSettingMap.getOrDefault(empId, new CflGoalSettingCflTableDTO());

            dto.setNinetyDaysGoal(ninetyDaysGoalsObj.getGoal());
            dto.setNinetyDaysDeliverable(ninetyDaysGoalsObj.getDeliverable());
            dto.setNinetyDaysStatus(ninetyDaysGoalsObj.getStatus());
            dto.setNinetyDaysWeightage(ninetyDaysGoalsObj.getWeightage());
            dto.setNinetyDaysQuarter(ninetyDaysGoalsObj.getQuarter());

            goalSettingMap.put(empId, dto);
        }

        return new ArrayList<>(goalSettingMap.values());
    }

    @Override
    public Integer countTotalQuarter(Long empId, Long year) {
        List<ThirtyDaysGoals>thirtyDaysGoals=thirtyDaysGoalsRepository.findByEmpIdAndYear(empId,year);
        return thirtyDaysGoals.size();
    }

    @Override
    public List<String> showListOfCflNameWhoCompletedGoalSetting(String quarter) {
        List<ThirtyDaysGoals>thirtyDaysGoalsList= thirtyDaysGoalsRepository.findByQuarter(quarter);
        return thirtyDaysGoalsList.stream().map(i->cflRepository.findByEmpId(i.getEmpId()).getCflFirstName()).toList();
    }


}
