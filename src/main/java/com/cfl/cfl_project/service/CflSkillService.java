package com.cfl.cfl_project.service;

import com.cfl.cfl_project.dto.CflSkillCflTableDTO;
import com.cfl.cfl_project.model.CflSkill;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CflSkillService {

    CflSkill createCflSkill(String quarter,CflSkill skill);
    CflSkill getCflSkillByEmpIdAndQuarterAndYear(Long id,String quarter,Long Year);
    List<CflSkill> getAll();

    List<CflSkill> getAllByYear(Long year);
    List<CflSkillCflTableDTO> getAllReports();

//    List<CflSkill> getCflSkillByEmpId(Long empId);

    List<CflSkill> getCflSkillByEmpId(Long empId);
}
