package com.cfl.cfl_project.service;

import com.cfl.cfl_project.model.HR;
import com.cfl.cfl_project.model.Mentor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface HRService {
 //   Boolean getByUserName(String userName);
 //   Boolean getByHREmail(String HREmail);
    HR createHR(Long hrId, String hrName, String hrEmail, String hrLocation,  MultipartFile mentorFile) throws IOException;
//    MentorResponse getMentorByMentorMail(String mentorEmail);
    HR getHRByHRMail(String HREmail);
 //   List<String> getAll();
    HR uploadHRImage(Long hrId, MultipartFile file) throws IOException;


    // for chart
    List<HR>getAllHR();

    String countHRScreenTime(String userMail,String timeInMinute);


}
