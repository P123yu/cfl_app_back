package com.cfl.cfl_project.service.impl;

import com.cfl.cfl_project.dto.CflSkillCflTableDTO;
import com.cfl.cfl_project.model.Cfl;
import com.cfl.cfl_project.model.CflSkill;
import com.cfl.cfl_project.repository.CflRepository;
import com.cfl.cfl_project.repository.CflSkillRepository;
import com.cfl.cfl_project.service.CflSkillService;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CflSkillServiceImpl implements CflSkillService {

    @Autowired
    private CflSkillRepository cflSkillRepository;

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




    @Autowired
    private CflRepository cflRepository;

    @Override
    @Transactional
    public CflSkill createCflSkill(String quarter,CflSkill skill) {
//        Long year= (long) LocalDate.now().getYear();
//        if(getQuarter().equals("Q4")){
//            year=year-1;
//        }

        Cfl cfl=cflRepository.findByEmpId(skill.getEmpId());




        CflSkill cflSkill = new CflSkill();
        cflSkill.setEmpId(skill.getEmpId());
        cflSkill.setQuarter(quarter);
        cflSkill.setPrimarySkills(skill.getPrimarySkills());
        cflSkill.setSecondarySkills(skill.getSecondarySkills());
        cflSkill.setOtherSkills(skill.getOtherSkills());
        cflSkill.setYear(Long.valueOf(cfl.getYear()));
        return cflSkillRepository.save(cflSkill);
    }

    @Override
   // @Cacheable(value = "cfl", key="'byId_' + #id + '_quarter_' + #quarter")
    public CflSkill getCflSkillByEmpIdAndQuarterAndYear(Long id, String quarter,Long year) {
        return cflSkillRepository.findByEmpIdAndQuarterAndYear(id, quarter,year);
    }


    @Override
    //@Cacheable(value = "cfl", key="'allCflSkill'")
    public List<CflSkill> getAll() {
        return cflSkillRepository.findAll();
    }



    @Override
    public List<CflSkill> getAllByYear(Long empId) {
        return cflSkillRepository.findByYear(empId);
    }


    // add cfl name in cfl skill  table from cfl table

    @Override
    public List<CflSkillCflTableDTO> getAllReports() {
        List<CflSkillCflTableDTO>cflSkillCflTableDTOS=new ArrayList<>();
        List<CflSkill>cflSkills=cflSkillRepository.findAll();
        for(CflSkill cflSkill : cflSkills){
            CflSkillCflTableDTO cflSkillCflTableDTO=new CflSkillCflTableDTO();
            Cfl cfl=cflRepository.findByEmpId(cflSkill.getEmpId());
            cflSkillCflTableDTO.setEmpId(cflSkill.getEmpId());

            cflSkillCflTableDTO.setCflName(cfl.getCflFirstName());
            cflSkillCflTableDTO.setCflEmail(cfl.getCflEmail());
            cflSkillCflTableDTO.setCflDepartment(cfl.getCflDepartment());
            cflSkillCflTableDTO.setCflLocation(cfl.getCflLocation());
            cflSkillCflTableDTO.setReportingManagerName(cfl.getReportingManager());
            cflSkillCflTableDTO.setReportingManagerEmail(cfl.getReportingManagerMail());
            cflSkillCflTableDTO.setCflDepartment(cfl.getCflDepartment());
            cflSkillCflTableDTO.setCflSubDepartment(cfl.getCflSubDepartment());

            cflSkillCflTableDTO.setQuarter(cflSkill.getQuarter());
            cflSkillCflTableDTO.setPrimarySkills(cflSkill.getPrimarySkills());
            cflSkillCflTableDTO.setSecondarySkills(cflSkill.getSecondarySkills());
            cflSkillCflTableDTO.setOtherSkills(cflSkill.getOtherSkills());
            cflSkillCflTableDTOS.add(cflSkillCflTableDTO);
        }
       return cflSkillCflTableDTOS;
    }

    @Override
    public List<CflSkill> getCflSkillByEmpId(Long empId) {
        List<CflSkill> res = cflSkillRepository.findByEmpId(empId);
        return res;
    }


//    @Override
//    public CflSkill getCflSkillByEmpId(Long empId) {
//        return cflSkillRepository.findByEmpId(empId);
//    }



}
