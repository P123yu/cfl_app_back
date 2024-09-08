package com.cfl.cfl_project.service.impl;

import com.cfl.cfl_project.model.CustomMenteeToMentorFeedBack;
import com.cfl.cfl_project.model.MenteeToMentorFeedBack;
import com.cfl.cfl_project.model.MentorToMenteeFeedBack;
import com.cfl.cfl_project.repository.CflRepository;
import com.cfl.cfl_project.repository.MentorRepository;
import com.cfl.cfl_project.repository.MentorToMenteeFeedBackRepository;
import com.cfl.cfl_project.service.MentorToMenteeFeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MentorToMenteeFeedBackServiceImpl implements MentorToMenteeFeedBackService {

    @Autowired
    private MentorToMenteeFeedBackRepository mentorToMenteeFeedBackRepository;

    @Autowired
    private CflRepository cflRepository;

    @Autowired
    private MentorRepository mentorRepository;

    @Override
    public MentorToMenteeFeedBack createFeedBack(MentorToMenteeFeedBack mentorToMenteeFeedBack) {
        MentorToMenteeFeedBack mentorToMenteeFeedBackObj=new MentorToMenteeFeedBack();
        mentorToMenteeFeedBackObj.setMenteeId(mentorToMenteeFeedBack.getMenteeId());
        mentorToMenteeFeedBackObj.setMenteeName(cflRepository.findByEmpId(mentorToMenteeFeedBack.getMenteeId()).getCflFirstName());
        mentorToMenteeFeedBackObj.setMentorEmail(mentorToMenteeFeedBack.getMentorEmail());
        mentorToMenteeFeedBackObj.setMentorName(mentorRepository.findByMentorEmail(mentorToMenteeFeedBack.getMentorEmail()).getMentorName());
        mentorToMenteeFeedBackObj.setFeedbackMessage(mentorToMenteeFeedBack.getFeedbackMessage());
        LocalDate date= LocalDate.now();
        mentorToMenteeFeedBackObj.setFeedbackDate(date);
        mentorToMenteeFeedBackObj.setYear((long) date.getYear());
        return mentorToMenteeFeedBackRepository.save(mentorToMenteeFeedBackObj);
    }



    @Override
    public List<MentorToMenteeFeedBack> getAllFeedBack(Long year) {
        return mentorToMenteeFeedBackRepository.findAll();
    }
}
