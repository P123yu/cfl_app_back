package com.cfl.cfl_project.service;

import com.cfl.cfl_project.dto.AnnualAppraisalInfoDTO;
import com.cfl.cfl_project.model.AnnualAppraisalInfo;
import com.cfl.cfl_project.model.Cfl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnnualAppraisalInfoService {

    List<AnnualAppraisalInfo>findAllCflWhoFilledAnnualAppraisal(Long year);
}
