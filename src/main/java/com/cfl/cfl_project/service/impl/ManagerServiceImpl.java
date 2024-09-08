package com.cfl.cfl_project.service.impl;

import com.cfl.cfl_project.email.CflToMentorMail;
import com.cfl.cfl_project.model.*;
import com.cfl.cfl_project.repository.*;
import com.cfl.cfl_project.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ManagerServiceImpl implements ManagerService {


    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private CflRepository cflRepository;

//    @Override
//    public Manager createManager(Long managerId, String managerName, String managerEmail, String managerDepartment, String managerLocation, String managerDesignation, MultipartFile managerFile) throws IOException {
//        Cfl cfl=new Cfl();
////        cfl.setManagerEmail(managerEmail);
//        cfl.setReportingManagerMail(managerEmail);
//        Manager manager =new Manager();
//        manager.setManagerId(managerId);
//        manager.setManagerName(managerName);
//        manager.setManagerEmail(managerEmail);
//        manager.setManagerDepartment(managerDepartment);
//        manager.setManagerLocation(managerLocation);
//        manager.setManagerDesignation(managerDesignation);
//       // manager.setManagerLocation();
//        manager.setManagerFileName(managerFile.getOriginalFilename());
//        manager.setManagerFileData(managerFile.getBytes());
//        return managerRepository.save(manager);
//    }



//    @Override
//    public Manager createManager(Long managerId, String managerName, String managerEmail, String managerDepartment, String managerLocation, String managerDesignation) {
//        Cfl cfl=new Cfl();
//        cfl.setReportingManagerMail(managerEmail);
//        Manager manager =new Manager();
//        manager.setManagerId(managerId);
//        manager.setManagerName(managerName);
//        manager.setManagerEmail(managerEmail);
//        manager.setManagerDepartment(managerDepartment);
//        manager.setManagerLocation(managerLocation);
//        manager.setManagerDesignation(managerDesignation);
//        return managerRepository.save(manager);
//    }

    @Override
    public Manager createChangeRequestMentor(Manager manager) {
        return managerRepository.save(manager);
    }

    @Override
    public Manager getManagerByManagerMail(String managerEmail) {
        return managerRepository.findByManagerEmail(managerEmail);
    }

    @Autowired
    private ThirtyDaysGoalsRepository thirtyDaysGoalsRepository;

    @Autowired
    private SixtyDaysGoalsRepository sixtyDaysGoalsRepository;

    @Autowired
    private NinetyDaysGoalsRepository ninetyDaysGoalsRepository;


    @Autowired
    private CflToMentorMail cflToMentorMail;

    @Async
    @Override
    public String createEmailOnApprove(Long cflId,String quarter) {
        List<ThirtyDaysGoals>allThirtyDaysGoalsByEmpId=thirtyDaysGoalsRepository.findByEmpIdAndQuarter(cflId,quarter);
        List<SixtyDaysGoals>allSixtyDaysGoalsByEmpId=sixtyDaysGoalsRepository.findByEmpIdAndQuarter(cflId,quarter);
        List<NinetyDaysGoals>allNinetyDaysGoalsByEmpId=ninetyDaysGoalsRepository.findByEmpIdAndQuarter(cflId,quarter);
        allThirtyDaysGoalsByEmpId.forEach(i->i.setStatus("approved"));
        allSixtyDaysGoalsByEmpId.forEach(i->i.setStatus("approved"));
        allNinetyDaysGoalsByEmpId.forEach(i->i.setStatus("approved"));
        thirtyDaysGoalsRepository.saveAll(allThirtyDaysGoalsByEmpId);
        sixtyDaysGoalsRepository.saveAll(allSixtyDaysGoalsByEmpId);
        ninetyDaysGoalsRepository.saveAll(allNinetyDaysGoalsByEmpId);
        String subject="Regarding Goal Setting";
        String message="Your goal setting has been approved";

        String cflEmail=cflRepository.findByEmpId(cflId).getCflEmail();
        cflToMentorMail.sendGoalSetting(cflEmail,subject,message);
        return "approved";
    }

    @Override
    public Boolean managerExists(String managerEmail) {
        return managerRepository.existsByManagerEmail(managerEmail);
    }

    @Override
    public Manager uploadManagerImage(Long managerId, MultipartFile file) throws IOException {
        Manager manager=managerRepository.findByManagerId(managerId);
        manager.setManagerFileName(file.getOriginalFilename());
        manager.setManagerFileData(file.getBytes());
        return managerRepository.save(manager);
    }

    @Override
    public List<Manager> getAllManager() {
        return managerRepository.findAll();
    }

    @Override
    public String countManagerScreenTime(String userMail, String timeInMinute) {
        Manager manager=managerRepository.findByManagerEmail(userMail);
        String totalScreenTime="";
        if(manager!=null) {
            String screenTime = manager.getManagerScreenTime();
            if (screenTime == null || screenTime.isEmpty()) {
                screenTime = timeInMinute;
            } else {
                screenTime += "," + timeInMinute;
            }
            manager.setManagerScreenTime(screenTime);
            manager = managerRepository.save(manager);
            totalScreenTime= manager.getManagerScreenTime();
        }
        return totalScreenTime;
    }



    @Scheduled(cron = "0 0 10 1 * ?")
    public String deleteManagerDataScreenOnTime(){
        List<Manager> managerList= managerRepository.findAll().stream().peek(i->i.setManagerScreenTime(null)).toList();
        if(managerList.isEmpty()){
            return "refreshed";
        }
        else{
            return "not refreshed";
        }
    }


}
