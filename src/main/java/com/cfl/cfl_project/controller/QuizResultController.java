package com.cfl.cfl_project.controller;

import com.cfl.cfl_project.model.QuizResult;
import com.cfl.cfl_project.service.QuizResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/quiz-result")
public class QuizResultController {

    @Autowired
    private QuizResultService quizResultService;

    @GetMapping("/get/{empId}/{topic}")
    public ResponseEntity<?> fetchQuizResultByEmpIdAndTopic(@PathVariable Long empId, @PathVariable String topic) {
        boolean result=quizResultService.fetchQuizResultByEmpIdAndTopic(empId,topic);
        return result ? ResponseEntity.ok(true) : ResponseEntity.ok(false);
    }


    @GetMapping("/getAllResultByTopic/{topic}")
    public ResponseEntity<?> getAllResultByTopic(@PathVariable String topic) {
        List<QuizResult> result=quizResultService.getAllResultByTopic(topic);
        return !result.isEmpty() ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }
}
