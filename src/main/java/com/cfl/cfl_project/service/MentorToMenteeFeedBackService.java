package com.cfl.cfl_project.service;

import com.cfl.cfl_project.model.CustomMenteeToMentorFeedBack;
import com.cfl.cfl_project.model.MenteeToMentorFeedBack;
import com.cfl.cfl_project.model.MentorToMenteeFeedBack;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MentorToMenteeFeedBackService {

    // create a feedback mentor to mentee
    MentorToMenteeFeedBack createFeedBack(MentorToMenteeFeedBack mentorToMenteeFeedBack);

    // get feedback
    List<MentorToMenteeFeedBack> getAllFeedBack(Long year);
}
