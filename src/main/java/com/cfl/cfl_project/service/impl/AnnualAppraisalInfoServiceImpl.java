package com.cfl.cfl_project.service.impl;

import com.cfl.cfl_project.dto.AnnualAppraisalInfoDTO;
import com.cfl.cfl_project.model.AnnualAppraisalInfo;
import com.cfl.cfl_project.model.Cfl;
import com.cfl.cfl_project.repository.AnnualAppraisalInfoRepository;
import com.cfl.cfl_project.repository.CflRepository;
import com.cfl.cfl_project.service.AnnualAppraisalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnnualAppraisalInfoServiceImpl implements AnnualAppraisalInfoService {

    @Autowired
    private AnnualAppraisalInfoRepository annualAppraisalInfoRepository;

    @Autowired
    private CflRepository cflRepository;

//    @Override
//    public List<AnnualAppraisalInfoDTO> findAllCflWhoFilledAnnualAppraisal(Long year) {
//        List<AnnualAppraisalInfoDTO> annualAppraisalInfoDTOS=new ArrayList<>();
//        List<AnnualAppraisalInfo> annualAppraisalInfoList=annualAppraisalInfoRepository.findAll();
//
//        for(AnnualAppraisalInfo annualAppraisalInfo:annualAppraisalInfoList){
//            AnnualAppraisalInfoDTO annualAppraisalInfoDTO=new AnnualAppraisalInfoDTO();
//            annualAppraisalInfoDTO.setEmpId(annualAppraisalInfo.getEmpId());
//            annualAppraisalInfoDTO.setEmpName(cflRepository.findByEmpId(annualAppraisalInfo.getEmpId()).getCflFirstName());
//            annualAppraisalInfoDTOS.add(annualAppraisalInfoDTO);
//        }
//        return annualAppraisalInfoDTOS;
//    }



    @Override
    public List<AnnualAppraisalInfo> findAllCflWhoFilledAnnualAppraisal(Long year) {
       return annualAppraisalInfoRepository.findByYear(year);
    }
}
