package com.cfl.cfl_project.service;

import com.cfl.cfl_project.model.HR;
import com.cfl.cfl_project.model.Manager;
import com.cfl.cfl_project.model.Mentor;
import com.cfl.cfl_project.model.MentorResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface ManagerService {
//    Manager createManager(Long managerId, String managerName, String managerEmail, String managerDepartment, String managerLocation, String managerDesignation, MultipartFile managerFile) throws IOException;

//    Manager createManager(Long managerId, String managerName, String managerEmail, String managerDepartment, String managerLocation, String managerDesignation);

    Manager createChangeRequestMentor(Manager manager);

    Manager getManagerByManagerMail(String managerEmail);
    String  createEmailOnApprove(Long cflId,String quarter);
    Boolean managerExists(String managerEmail);
    Manager uploadManagerImage(Long managerId, MultipartFile file) throws IOException;


    // for chart
    List<Manager>getAllManager();

    String countManagerScreenTime(String userMail,String timeInMinute);

}
