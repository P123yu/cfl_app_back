package com.cfl.cfl_project.service;


import com.cfl.cfl_project.model.QuizResult;
import com.cfl.cfl_project.model.QuizTest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuizTestService {

    //create question set
    List<QuizTest> createQuiz(List<QuizTest> quizTestList);

    // get All
    List<QuizTest> getAllQuiz();

    // get All Topics
    List<String> getAllTopics();

    // get All Quiz By Topics
    List<QuizTest> getAllQuizBasedOnTopic(String topic);

    QuizResult setCflQuizResult(QuizResult quizResult);

    String getTimeAndDate(String topic);

    void deleteAllQuizByTopic(String topic);

}
