package com.cfl.cfl_project.service;

import com.cfl.cfl_project.model.Mentor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface MentorService {
    Boolean getByUserName(String userName);
    Boolean getByMentorEmail(String mentorEmail);

//    Mentor createMentor(Long mentorId, String empId, String mentorName, String mentorEmail, String mentorDepartment, String mentorLocation, String mentorDesignation, MultipartFile mentorFile);

    Mentor createChangeRequestMentor(Mentor mentor);
    Mentor getMentorByMentorMail(String mentorEmail);
    List<String> getAll();

    Mentor uploadMentorImage(Long mentorId, MultipartFile file) throws IOException;

    // for chart
    List<Mentor>getAllMentor();

    String countMentorScreenTime(String userMail,String timeInMinute);


}
