package com.cfl.cfl_project.controller;

import com.cfl.cfl_project.model.QuizResult;
import com.cfl.cfl_project.model.QuizTest;
import com.cfl.cfl_project.service.QuizTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
@CrossOrigin("*")

public class QuizTestController {

    @Autowired
    private QuizTestService quizService;

    @PostMapping("/create")
    public ResponseEntity<?> createQuiz(@RequestBody List<QuizTest> quizTestList){
        List<QuizTest>quizTestLists=quizService.createQuiz(quizTestList);
        if(!quizTestLists.isEmpty()){
            return ResponseEntity.ok(quizTestLists);
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }



    @GetMapping("/getAll")
    public ResponseEntity<?>getAllQuiz(){
        List<QuizTest>quizTestLists=quizService.getAllQuiz();
        if(!quizTestLists.isEmpty()){
            return ResponseEntity.ok(quizTestLists);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/getAllQuizBasedOnTopic/{topic}")
    public ResponseEntity<?>getAllQuizBasedOnTopic(@PathVariable String topic){
        List<QuizTest>quizTestLists=quizService.getAllQuizBasedOnTopic(topic);
        if(!quizTestLists.isEmpty()){
            return ResponseEntity.ok(quizTestLists);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/getAllTopic")
    public ResponseEntity<?>getAllTopic(){
        List<String>topicLists=quizService.getAllTopics();
        if(!topicLists.isEmpty()){
            return ResponseEntity.ok(topicLists);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/saveResult")
    public ResponseEntity<?> setCflQuizResult(@RequestBody QuizResult quizResult){
        QuizResult quizResults=quizService.setCflQuizResult(quizResult);
        if(quizResults !=null ){
            return ResponseEntity.ok(quizResults);
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }



    @GetMapping("/getTimeAndDate/{topic}")
    public ResponseEntity<?> getTimeAndDate(@PathVariable String topic){
        String result=quizService.getTimeAndDate(topic);
        if(!result.isEmpty()){
            return ResponseEntity.ok(result);
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/deleteAllQuizByTopic/{topic}")
    public ResponseEntity<?> deleteAllQuizByTopic(@PathVariable String topic){
        try{
            quizService.deleteAllQuizByTopic(topic);
            return ResponseEntity.ok(true);
        }
        catch(Exception e){
            return ResponseEntity.ok(false);
        }
    }

}
