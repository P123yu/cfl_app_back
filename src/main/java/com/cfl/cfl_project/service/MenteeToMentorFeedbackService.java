package com.cfl.cfl_project.service;

import com.cfl.cfl_project.model.CustomMenteeToMentorFeedBack;
import com.cfl.cfl_project.model.MenteeToMentorFeedBack;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MenteeToMentorFeedbackService {
    // create a feedback mentee to mentor
    MenteeToMentorFeedBack createFeedBack(MenteeToMentorFeedBack menteeToMentorFeedback);

    // get feedback
    List<CustomMenteeToMentorFeedBack> getAllFeedBack(Long year);

}
