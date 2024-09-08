package com.cfl.cfl_project.service.impl;

import com.cfl.cfl_project.model.CustomMenteeToMentorFeedBack;
import com.cfl.cfl_project.model.MenteeToMentorFeedBack;
import com.cfl.cfl_project.repository.CflRepository;
import com.cfl.cfl_project.repository.MenteeToMentorFeedbackRepository;
import com.cfl.cfl_project.repository.MentorRepository;
import com.cfl.cfl_project.service.MenteeToMentorFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenteeToMentorFeedbackServiceImpl implements MenteeToMentorFeedbackService {

    @Autowired
    private MenteeToMentorFeedbackRepository menteeToMentorFeedBackRepository;

    @Override
    public MenteeToMentorFeedBack createFeedBack(MenteeToMentorFeedBack menteeToMentorFeedback) {
        MenteeToMentorFeedBack menteeToMentorFeedBackObj=new MenteeToMentorFeedBack();
        menteeToMentorFeedBackObj.setMenteeId(menteeToMentorFeedback.getMenteeId());
        menteeToMentorFeedBackObj.setFeedbackMessage(menteeToMentorFeedback.getFeedbackMessage());
        LocalDate date= LocalDate.now();
        menteeToMentorFeedBackObj.setFeedbackDate(date);
        menteeToMentorFeedBackObj.setYear((long) date.getYear());
        return menteeToMentorFeedBackRepository.save(menteeToMentorFeedBackObj);
    }


    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private CflRepository cflRepository;



    @Override
    public  List<CustomMenteeToMentorFeedBack> getAllFeedBack(Long year) {
        List<CustomMenteeToMentorFeedBack>customMenteeToMentorFeedBackList=new ArrayList<>();
        List<MenteeToMentorFeedBack> menteeToMentorFeedBackLists=menteeToMentorFeedBackRepository.findByYear(year);
        for(MenteeToMentorFeedBack menteeToMentorFeedBackObj :menteeToMentorFeedBackLists){
            CustomMenteeToMentorFeedBack customMenteeToMentorFeedBack=new CustomMenteeToMentorFeedBack();
            customMenteeToMentorFeedBack.setMenteeId(menteeToMentorFeedBackObj.getMenteeId());
            customMenteeToMentorFeedBack.setFeedbackMessage(menteeToMentorFeedBackObj.getFeedbackMessage());
            customMenteeToMentorFeedBack.setFeedbackDate(menteeToMentorFeedBackObj.getFeedbackDate());
            customMenteeToMentorFeedBack.setMenteeName(cflRepository.findByEmpId(menteeToMentorFeedBackObj.getMenteeId()).getCflFirstName());
            customMenteeToMentorFeedBack.setMentorName(cflRepository.findByEmpId(menteeToMentorFeedBackObj.getMenteeId()).getMentorName());
            customMenteeToMentorFeedBackList.add(customMenteeToMentorFeedBack);
        }
        return customMenteeToMentorFeedBackList;
    }
}
