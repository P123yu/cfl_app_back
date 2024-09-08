package com.cfl.cfl_project.service.impl;

import com.cfl.cfl_project.dto.CflLogBookCflTableDTO;
import com.cfl.cfl_project.dto.CflProbationConfirmationCflTableDTO;
import com.cfl.cfl_project.model.Cfl;
import com.cfl.cfl_project.model.LogBook;
import com.cfl.cfl_project.model.ProbationConfirmation;
import com.cfl.cfl_project.repository.CflRepository;
import com.cfl.cfl_project.repository.ProbationConfirmationRepository;
import com.cfl.cfl_project.service.ProbationConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProbationConfirmationServiceImpl implements ProbationConfirmationService {

    @Autowired
    private ProbationConfirmationRepository probationConfirmationRepository;

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
    public ProbationConfirmation create(ProbationConfirmation probationConfirmation) {
        int year=LocalDate.now().getYear();
        if(getQuarter().equalsIgnoreCase("Q4")){
            year-=1;
        }
        probationConfirmation.setYear((long) year);
        probationConfirmation.setStatus("saved");
        return probationConfirmationRepository.save(probationConfirmation);
    }

    @Override
    public ProbationConfirmation getByEmployeeCode(Long empId) {
        return probationConfirmationRepository.findByEmployeeCode(empId);
    }


    @Autowired
    private CflRepository cflRepository;

    @Override
    public List<CflProbationConfirmationCflTableDTO> cflProbationConfirmationCflTableDTOs() {
        List<CflProbationConfirmationCflTableDTO>cflProbationConfirmationCflTableDTOS=new ArrayList<>();
        List<ProbationConfirmation>probationConfirmations=probationConfirmationRepository.findAll();
        for(ProbationConfirmation probationConfirmation : probationConfirmations) {
            CflProbationConfirmationCflTableDTO cflProbationConfirmationCflTableDTO = new CflProbationConfirmationCflTableDTO();
            Cfl cfl = cflRepository.findByEmpId(probationConfirmation.getEmployeeCode());
            cflProbationConfirmationCflTableDTO.setEmployeeCode(cfl.getEmpId());

            cflProbationConfirmationCflTableDTO.setCflName(cfl.getCflFirstName());
            cflProbationConfirmationCflTableDTO.setCflEmail(cfl.getCflEmail());
            cflProbationConfirmationCflTableDTO.setCflDepartment(cfl.getCflDepartment());
            cflProbationConfirmationCflTableDTO.setCflLocation(cfl.getCflLocation());
            cflProbationConfirmationCflTableDTO.setReportingManagerName(cfl.getReportingManager());
            cflProbationConfirmationCflTableDTO.setReportingManagerEmail(cfl.getReportingManagerMail());
            cflProbationConfirmationCflTableDTO.setCflDepartment(cfl.getCflDepartment());
            cflProbationConfirmationCflTableDTO.setCflSubDepartment(cfl.getCflSubDepartment());

            cflProbationConfirmationCflTableDTO.setDesignation(cfl.getCflDesignation());
            cflProbationConfirmationCflTableDTO.setLocation(cfl.getCflLocation());
            cflProbationConfirmationCflTableDTO.setDepartment(cfl.getCflDepartment());
            cflProbationConfirmationCflTableDTO.setProject(cfl.getCflProject());
            cflProbationConfirmationCflTableDTO.setDateOfJoining(cfl.getJoiningDate());
            cflProbationConfirmationCflTableDTO.setDateOfConfirmation(probationConfirmation.getDateOfConfirmation());

            cflProbationConfirmationCflTableDTO.setPerformanceStandard1(probationConfirmation.getDropdown1());
            cflProbationConfirmationCflTableDTO.setQualityOfWork1(probationConfirmation.getDropdown2());
            cflProbationConfirmationCflTableDTO.setSubjectKnowledge1(probationConfirmation.getDropdown3());
            cflProbationConfirmationCflTableDTO.setInitiativeAndWillingness1(probationConfirmation.getDropdown4());
            cflProbationConfirmationCflTableDTO.setAttendance1(probationConfirmation.getDropdown5());
            cflProbationConfirmationCflTableDTO.setTeamWork1(probationConfirmation.getDropdown6());
            cflProbationConfirmationCflTableDTO.setOrganizingAndTeamManagement1(probationConfirmation.getDropdown7());
            cflProbationConfirmationCflTableDTO.setAttitudeTowardsWork1(probationConfirmation.getDropdown8());
            cflProbationConfirmationCflTableDTO.setWellVersedWithCompanyPolicies1(probationConfirmation.getDropdown9());
            cflProbationConfirmationCflTableDTO.setCompanyCodeOfConduct1(probationConfirmation.getDropdown10());


            cflProbationConfirmationCflTableDTO.setPerformanceStandard2(probationConfirmation.getDropdown11());
            cflProbationConfirmationCflTableDTO.setQualityOfWork2(probationConfirmation.getDropdown12());
            cflProbationConfirmationCflTableDTO.setSubjectKnowledge2(probationConfirmation.getDropdown13());
            cflProbationConfirmationCflTableDTO.setInitiativeAndWillingness2(probationConfirmation.getDropdown14());
            cflProbationConfirmationCflTableDTO.setAttendance2(probationConfirmation.getDropdown15());
            cflProbationConfirmationCflTableDTO.setTeamWork2(probationConfirmation.getDropdown16());
            cflProbationConfirmationCflTableDTO.setOrganizingAndTeamManagement2(probationConfirmation.getDropdown17());
            cflProbationConfirmationCflTableDTO.setAttitudeTowardsWork2(probationConfirmation.getDropdown18());
            cflProbationConfirmationCflTableDTO.setWellVersedWithCompanyPolicies2(probationConfirmation.getDropdown19());
            cflProbationConfirmationCflTableDTO.setCompanyCodeOfConduct2(probationConfirmation.getDropdown20());

            cflProbationConfirmationCflTableDTO.setRemark3(probationConfirmation.getRemark3());
            cflProbationConfirmationCflTableDTO.setRemark6(probationConfirmation.getRemark6());
            cflProbationConfirmationCflTableDTO.setConfirmation(probationConfirmation.getConfirmation());
            cflProbationConfirmationCflTableDTO.setReportingManagerName(probationConfirmation.getReportingManagerName());
            cflProbationConfirmationCflTableDTO.setReportingManagerSignature(probationConfirmation.getReportingManagerSignature());
            cflProbationConfirmationCflTableDTO.setBuHeadName(probationConfirmation.getBuHeadName());
            cflProbationConfirmationCflTableDTO.setBuHeadSignature(probationConfirmation.getBuHeadSignature());
            cflProbationConfirmationCflTableDTO.setHrRepresentativeName(probationConfirmation.getHrRepresentativeName());
            cflProbationConfirmationCflTableDTO.setHrRepresentativeSignature(probationConfirmation.getHrRepresentativeSignature());
            cflProbationConfirmationCflTableDTO.setStatus(probationConfirmation.getStatus());
            cflProbationConfirmationCflTableDTOS.add(cflProbationConfirmationCflTableDTO);
        }
        return cflProbationConfirmationCflTableDTOS;
    }

    @Override
    public List<ProbationConfirmation> findAllProbationStatusByYear(Long year) {
        return probationConfirmationRepository.findByYear(year);
    }

    @Override
    public ProbationConfirmation editProbationConfirmation(Long empId) {
        ProbationConfirmation probationConfirmation=probationConfirmationRepository.findByEmployeeCode(empId);
        probationConfirmation.setStatus("update");
        return probationConfirmationRepository.save(probationConfirmation);
    }
}
