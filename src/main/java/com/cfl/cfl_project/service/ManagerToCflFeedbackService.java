package com.cfl.cfl_project.service;


import com.cfl.cfl_project.model.ManagerToCflFeedBack;
import com.cfl.cfl_project.model.MentorToMenteeFeedBack;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ManagerToCflFeedbackService {

    // create a feedback mentor to mentee
    ManagerToCflFeedBack createFeedBack(ManagerToCflFeedBack managerToCflFeedBack);

    // get feedback
    List<ManagerToCflFeedBack> getAllFeedBack(Long year);
}
