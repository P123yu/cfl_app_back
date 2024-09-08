package com.cfl.cfl_project.service;

import com.cfl.cfl_project.dto.ManagerRatingCflTableDTO;
import com.cfl.cfl_project.model.ManagerRating;
import com.cfl.cfl_project.model.PerformanceDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ManagerRatingService {
    ManagerRating createManagerRating(ManagerRating managerRating);
    ManagerRating getManagerRatingByEmpId(Long id);

    List<ManagerRating> getManagerRatingByEmpIdAndYear(Long id,Long year);

    List<ManagerRating> getAll();
    Boolean managerRatingEmail(Long empId,String potentialLevel,String performanceLevel,String talentLevel,String managerEmail);
    List<ManagerRatingCflTableDTO>getAllRating();
    String getTalentLevel(Long empId);

    List<PerformanceDTO> getListOfSpecificTalentLevelByYear(String talentLevel,Long year);





}
