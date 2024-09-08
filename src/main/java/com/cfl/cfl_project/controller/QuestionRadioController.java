package com.cfl.cfl_project.controller;

import com.cfl.cfl_project.model.QuestionRadio;
import com.cfl.cfl_project.service.QuestionRadioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/questionRadio")
@CrossOrigin
public class QuestionRadioController {

    @Autowired
    private QuestionRadioService questionRadioService;


    @PostMapping("/create")
    public ResponseEntity<?>create(@RequestBody QuestionRadio questionRadio){
        QuestionRadio questionRadioObj=questionRadioService.create(questionRadio);
        if(questionRadioObj !=null){
            return ResponseEntity.ok(questionRadioObj);
        }
        else{
            return ResponseEntity.status(400).body("Failed to create QuestionRadio");
        }
    }

//    @GetMapping("/get/{empId}/{quarter}")
//    public ResponseEntity<?>getByEmpId(@PathVariable Long empId,@PathVariable String quarter){
//        QuestionRadio questionRadioObj=questionRadioService.getByEmpIdAndQuarter(empId,quarter);
//        if(questionRadioObj !=null){
//            return ResponseEntity.ok(questionRadioObj);
//        }
//        else{
//            return ResponseEntity.status(400).body("Failed to get QuestionRadio");
//        }
//    }


    @GetMapping("/get/{empId}")
    public ResponseEntity<?>getByEmpId(@PathVariable Long empId){
        QuestionRadio questionRadioObj=questionRadioService.getByEmpId(empId);
        if(questionRadioObj !=null){
            return ResponseEntity.ok(questionRadioObj);
        }
        else{
            return ResponseEntity.status(400).body("Failed to get QuestionRadio");
        }
    }


}
