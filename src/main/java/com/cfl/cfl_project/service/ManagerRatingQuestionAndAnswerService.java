package com.cfl.cfl_project.service;

import com.cfl.cfl_project.model.ManagerRatingQuestionAndAnswer;
import org.springframework.stereotype.Service;

@Service
public interface ManagerRatingQuestionAndAnswerService {
    ManagerRatingQuestionAndAnswer saveQuestionAndAnswer(ManagerRatingQuestionAndAnswer managerRatingQuestionAndAnswer);
    ManagerRatingQuestionAndAnswer getQuestionAndAnswer(Long empId);
}
