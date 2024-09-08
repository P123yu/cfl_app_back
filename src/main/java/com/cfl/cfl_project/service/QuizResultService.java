package com.cfl.cfl_project.service;


import com.cfl.cfl_project.model.QuizResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuizResultService {

    boolean fetchQuizResultByEmpIdAndTopic(Long empId,String topic);

    List<QuizResult> getAllResultByTopic(String topic);
}
