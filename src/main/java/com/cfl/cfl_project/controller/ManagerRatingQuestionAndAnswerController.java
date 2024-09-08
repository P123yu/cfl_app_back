package com.cfl.cfl_project.controller;

import com.cfl.cfl_project.model.ManagerRatingQuestionAndAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cfl.cfl_project.service.ManagerRatingQuestionAndAnswerService;

@RestController
@CrossOrigin
@RequestMapping("/question-answer")
public class ManagerRatingQuestionAndAnswerController {

    @Autowired
    private ManagerRatingQuestionAndAnswerService managerRatingQuestionAndAnswerService;

    @PostMapping("/create")
    public ResponseEntity<?> createQuestionAndAnswer(@RequestBody ManagerRatingQuestionAndAnswer managerRatingQuestionAndAnswer) {
        ManagerRatingQuestionAndAnswer managerRatingQuestionAndAnswerObj=managerRatingQuestionAndAnswerService.saveQuestionAndAnswer(managerRatingQuestionAndAnswer);
        if(managerRatingQuestionAndAnswerObj !=null){
            return ResponseEntity.ok(managerRatingQuestionAndAnswerObj);
        }
        else{
            return ResponseEntity.status(400).body("Failed to create ManagerRatingQuestionAndAnswer");
        }
    }


    @GetMapping("/getByEmpId/{empId}")
    public ResponseEntity<?> getQuestionAndAnswerByEmpId(@PathVariable Long empId){
        ManagerRatingQuestionAndAnswer managerRatingQuestionAndAnswerObj=managerRatingQuestionAndAnswerService.getQuestionAndAnswer(empId);
        if(managerRatingQuestionAndAnswerObj!=null){
            return ResponseEntity.ok(managerRatingQuestionAndAnswerObj);
        }
        else{
            return ResponseEntity.status(400).body("Failed to get ManagerRatingQuestionAndAnswer");
        }
    }

}
