package com.cfl.cfl_project.service.impl;

import com.cfl.cfl_project.model.QuizResult;
import com.cfl.cfl_project.repository.QuizResultRepository;
import com.cfl.cfl_project.service.QuizResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizResultServiceImpl implements QuizResultService {

    @Autowired
    private QuizResultRepository quizResultRepository;


    @Override
    public boolean fetchQuizResultByEmpIdAndTopic(Long empId, String topic) {
        boolean result=quizResultRepository.existsByCflIdAndTopic(empId,topic);
        return result ? true :false;
    }

    @Override
    public List<QuizResult> getAllResultByTopic(String topic) {
        return quizResultRepository.findByTopic(topic);
    }
}
