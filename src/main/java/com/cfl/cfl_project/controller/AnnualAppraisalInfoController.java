package com.cfl.cfl_project.controller;

import com.cfl.cfl_project.dto.AnnualAppraisalInfoDTO;
import com.cfl.cfl_project.model.AnnualAppraisalInfo;
import com.cfl.cfl_project.service.AnnualAppraisalInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/annualAppraisal")
@CrossOrigin

public class AnnualAppraisalInfoController {


        @Autowired
        private AnnualAppraisalInfoService annualAppraisalInfoService;


//        @GetMapping("/getAllCflWhoFilledAnnualAppraisal/{year}")
//        public ResponseEntity<List<AnnualAppraisalInfoDTO>> findAllCflWhoFilledAnnualAppraisal(@PathVariable Long year) {
//            List<AnnualAppraisalInfoDTO> annualAppraisalInfoDTOList=annualAppraisalInfoService.findAllCflWhoFilledAnnualAppraisal(year);
//            return ! annualAppraisalInfoDTOList.isEmpty() ? ResponseEntity.ok(annualAppraisalInfoDTOList) : ResponseEntity.notFound().build();
//        }


    @GetMapping("/getAllCflWhoFilledAnnualAppraisal/{year}")
    public ResponseEntity<List<AnnualAppraisalInfo>> findAllCflWhoFilledAnnualAppraisal(@PathVariable Long year) {
        List<AnnualAppraisalInfo> annualAppraisalInfoList=annualAppraisalInfoService.findAllCflWhoFilledAnnualAppraisal(year);
        return ! annualAppraisalInfoList.isEmpty() ? ResponseEntity.ok(annualAppraisalInfoList) : ResponseEntity.notFound().build();
    }
    }



