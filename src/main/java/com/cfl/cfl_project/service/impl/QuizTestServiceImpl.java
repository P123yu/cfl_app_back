package com.cfl.cfl_project.service.impl;

import com.cfl.cfl_project.model.QuizResult;
import com.cfl.cfl_project.model.QuizTest;
import com.cfl.cfl_project.repository.QuizResultRepository;
import com.cfl.cfl_project.repository.QuizTestRepository;
import com.cfl.cfl_project.service.QuizTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizTestServiceImpl implements QuizTestService {

    @Autowired
    private QuizTestRepository quizTestRepository;


    @Autowired QuizResultRepository quizResultRepository;

    @Override
    public List<QuizTest> createQuiz(List<QuizTest> quizTestList) {
        return quizTestRepository.saveAll(quizTestList);
    }

    @Override
    public List<QuizTest> getAllQuiz() {
        return quizTestRepository.findAll();
    }

    @Override
    public List<String> getAllTopics() {
        return quizTestRepository.findAll().stream().map(QuizTest::getTopic).distinct().collect(Collectors.toList());
    }

    @Override
    public List<QuizTest> getAllQuizBasedOnTopic(String topic) {
        return quizTestRepository.findByTopic(topic);
    }

    @Override
    public QuizResult setCflQuizResult(QuizResult quizResult) {
        return quizResultRepository.save(quizResult);
    }

    @Override
    public String getTimeAndDate(String topic) {
        List<QuizTest> quizTests = quizTestRepository.findByTopic(topic);
        if (!quizTests.isEmpty()) {
            QuizTest quizTest = quizTests.get(0);
            return quizTest.getExamDuration() + " " + quizTest.getFromDate() + " " + quizTest.getToDate();
        }
        return "";
    }

    @Transactional
    @Override
    public void deleteAllQuizByTopic(String topic) {
        quizTestRepository.deleteByTopic(topic);
    }


}

