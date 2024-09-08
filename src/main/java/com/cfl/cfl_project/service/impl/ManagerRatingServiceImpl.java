package com.cfl.cfl_project.service.impl;

import com.cfl.cfl_project.dto.CflSkillCflTableDTO;
import com.cfl.cfl_project.dto.ManagerRatingCflTableDTO;
import com.cfl.cfl_project.email.CflToMentorMail;
import com.cfl.cfl_project.model.Cfl;
import com.cfl.cfl_project.model.CflSkill;
import com.cfl.cfl_project.model.ManagerRating;
import com.cfl.cfl_project.model.PerformanceDTO;
import com.cfl.cfl_project.repository.CflRepository;
import com.cfl.cfl_project.repository.ManagerRatingRepository;
import com.cfl.cfl_project.repository.ManagerRepository;
import com.cfl.cfl_project.service.ManagerRatingService;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerRatingServiceImpl implements ManagerRatingService {

    @Autowired
    private ManagerRatingRepository managerRatingRepository;

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



//    @Override
//    public ManagerRating createManagerRating(ManagerRating managerRating) {
//        ManagerRating newManagerRating = new ManagerRating();
//        newManagerRating.setEmpId(managerRating.getEmpId());
//        newManagerRating.setTalentLevel(managerRating.getTalentLevel());
//        newManagerRating.setPotentialLevel(managerRating.getPotentialLevel());
//        newManagerRating.setPerformanceLevel(managerRating.getPerformanceLevel());
//        newManagerRating.setQuarter(getQuarter());
//        Long year= Long.valueOf(LocalDate.now().getYear());
//        if(getQuarter().equals("Q4")){
//            year-=1;
//        }
//        newManagerRating.setYear(year);
//        return managerRatingRepository.save(newManagerRating);
//    }



    @Override
    @Transactional
    @Async
    public ManagerRating createManagerRating(ManagerRating managerRating) {

        Cfl cfl=cflRepository.findByEmpId(managerRating.getEmpId());

        ManagerRating newManagerRating = new ManagerRating();
        newManagerRating.setEmpId(managerRating.getEmpId());
        newManagerRating.setTalentLevel(managerRating.getTalentLevel());
        newManagerRating.setPotentialLevel(managerRating.getPotentialLevel());
        newManagerRating.setPerformanceLevel(managerRating.getPerformanceLevel());

        Long year= Long.valueOf(LocalDate.now().getYear());


        if(Integer.parseInt(cfl.getYear())<LocalDate.now().getYear()){
            newManagerRating.setAnnual("annual");
            newManagerRating.setYear(Long.valueOf(cfl.getYear()));
        }

//        if(Integer.parseInt(cfl.getYear())<LocalDate.now().getYear()){
//            newManagerRating.setQuarter("annual");
//        }
//        else{
//            newManagerRating.setQuarter(getQuarter());
//
//            if(getQuarter().equals("Q4")){
//                year-=1;
//            }
//        }
//        newManagerRating.setYear(year);

        cflToMentorMail.sendManagerRatingMailToHR(cfl.getHrMail(),cfl.getCflFirstName(),getQuarter(), managerRating.getPotentialLevel(), managerRating.getPerformanceLevel(), managerRating.getTalentLevel(), cfl.getReportingManager());

        return managerRatingRepository.save(newManagerRating);
    }

    @Override
    public ManagerRating getManagerRatingByEmpId(Long id) {
        return managerRatingRepository.findByEmpId(id);
    }

    @Override
    public List<ManagerRating> getManagerRatingByEmpIdAndYear(Long id, Long year) {
        return managerRatingRepository.findByEmpIdAndYear(id,year);
    }

    @Override
    public List<ManagerRating> getAll() {
        List<ManagerRating> managerRatingList= managerRatingRepository.findAll();
        managerRatingList=managerRatingList.stream().peek(i->i.setEmpName(cflRepository.findByEmpId(i.getEmpId()).getCflFirstName())).toList();
        return managerRatingList;
    }

    @Autowired
    private CflRepository cflRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private CflToMentorMail cflToMentorMail;

    public String getTalentLevel(Long empId){
        return managerRatingRepository.findByEmpId(empId).getTalentLevel();
    }


    @Override
    @Transactional
    public List<PerformanceDTO> getListOfSpecificTalentLevelByYear(String talentLevel, Long year) {

        List<ManagerRating>completeManagerRatingList=new ArrayList<>();
        if(talentLevel.equalsIgnoreCase("Talent Pool")){
            List<ManagerRating>managerRatingStarList=managerRatingRepository.findByTalentLevelAndYear("Star", year);
            List<ManagerRating>managerRatingHighPotentialList=managerRatingRepository.findByTalentLevelAndYear("High Potential", year);
            System.out.println(managerRatingHighPotentialList+"managerRatingHighPotentialList111");
            List<ManagerRating>managerRatingList=managerRatingRepository.findByTalentLevelAndYear("High Performer", year);
            managerRatingHighPotentialList.addAll(managerRatingList);
            managerRatingStarList.addAll(managerRatingHighPotentialList);
            completeManagerRatingList.addAll(managerRatingStarList);
        }
        else if(talentLevel.equalsIgnoreCase("Critical Pool")){
            List<ManagerRating>managerRatingStarList=managerRatingRepository.findByTalentLevelAndYear("Solid Performer", year);
            List<ManagerRating>managerRatingHighPotentialList=managerRatingRepository.findByTalentLevelAndYear("Potential Gem", year);
            List<ManagerRating>managerRatingList=managerRatingRepository.findByTalentLevelAndYear("Core Player", year);
            managerRatingHighPotentialList.addAll(managerRatingList);
            managerRatingStarList.addAll(managerRatingHighPotentialList);
            completeManagerRatingList.addAll(managerRatingStarList);
        }
        else if(talentLevel.equalsIgnoreCase("Others")){
            List<ManagerRating>managerRatingStarList=managerRatingRepository.findByTalentLevelAndYear("Average Performer", year);
            List<ManagerRating>managerRatingHighPotentialList=managerRatingRepository.findByTalentLevelAndYear("Inconsistent Player", year);
            managerRatingStarList.addAll(managerRatingHighPotentialList);
            completeManagerRatingList.addAll(managerRatingStarList);
        }
       // List<ManagerRating>managerRatingList=managerRatingRepository.findByTalentLevelAndYear(talentLevel, year, quarter);
      //  System.out.println(managerRatingList+"managerRatingList");
        List<Long> empIdList=completeManagerRatingList.stream().map(ManagerRating::getEmpId).toList();
        System.out.println(empIdList+"empIdList");
        List<PerformanceDTO>performanceDTOList=new ArrayList<>();
        for(Long empId:empIdList){
            PerformanceDTO performanceDTO=new PerformanceDTO();
            Cfl cfl=cflRepository.findByEmpId(empId);
            performanceDTO.setEmpId(cfl.getEmpId());
            performanceDTO.setCflFirstName(cfl.getCflFirstName());
            performanceDTO.setCflLastName(cfl.getCflLastName());
            performanceDTO.setCflEmail(cfl.getCflEmail());
            performanceDTO.setCflDepartment(cfl.getCflDepartment());
            performanceDTO.setFileName(cfl.getFileName());
            performanceDTO.setFileData(cfl.getFileData());

            ManagerRating managerRating=managerRatingRepository.findByEmpId(empId);
            performanceDTO.setPotentialLevel(managerRating.getPotentialLevel());
            performanceDTO.setPerformanceLevel(managerRating.getPerformanceLevel());
            performanceDTO.setTalentLevel(managerRating.getTalentLevel());

            performanceDTOList.add(performanceDTO);
        }
        return performanceDTOList;
    }


    @Override
    public Boolean managerRatingEmail(Long empId, String potentialLevel, String performanceLevel, String talentLevel, String managerEmail) {
        String cflName=cflRepository.findByEmpId(empId).getCflFirstName();
        String cflMail=cflRepository.findByEmpId(empId).getCflEmail();
        String subject="Regarding CFL Rating";
        String potential=potentialLevel;
        String performance=performanceLevel;
        String talent=talentLevel;
        String managerName=managerRepository.findByManagerEmail(managerEmail).getManagerName();
        return cflToMentorMail.sendManagerRatingMailToCfl(cflName,cflMail,subject,getQuarter(), potential, performance, talent, managerName);

    }

    @Override
    public List<ManagerRatingCflTableDTO> getAllRating() {
        List<ManagerRatingCflTableDTO>managerRatingCflTableDTOS=new ArrayList<>();
        List<ManagerRating>managerRatings=managerRatingRepository.findAll();
        for(ManagerRating managerRating : managerRatings){
            ManagerRatingCflTableDTO managerRatingCflTableDTO=new ManagerRatingCflTableDTO();
            Cfl cfl=cflRepository.findByEmpId(managerRating.getEmpId());
            managerRatingCflTableDTO.setEmpId(cfl.getEmpId());

            managerRatingCflTableDTO.setCflName(cfl.getCflFirstName());
            managerRatingCflTableDTO.setCflEmail(cfl.getCflEmail());
            managerRatingCflTableDTO.setCflDepartment(cfl.getCflDepartment());
            managerRatingCflTableDTO.setCflLocation(cfl.getCflLocation());
            managerRatingCflTableDTO.setReportingManagerName(cfl.getReportingManager());
            managerRatingCflTableDTO.setReportingManagerEmail(cfl.getReportingManagerMail());
            managerRatingCflTableDTO.setCflDepartment(cfl.getCflDepartment());
            managerRatingCflTableDTO.setCflSubDepartment(cfl.getCflSubDepartment());


            managerRatingCflTableDTO.setTalentLevel(managerRating.getTalentLevel());
            managerRatingCflTableDTO.setPotentialLevel(managerRating.getPotentialLevel());
            managerRatingCflTableDTO.setPerformanceLevel(managerRating.getPerformanceLevel());
            managerRatingCflTableDTO.setAnnual(managerRating.getAnnual());
            managerRatingCflTableDTOS.add(managerRatingCflTableDTO);
        }
        return managerRatingCflTableDTOS;
    }


}
