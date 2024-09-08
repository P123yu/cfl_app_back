package com.cfl.cfl_project.controller;

import com.cfl.cfl_project.model.ManagerToCflFeedBack;
import com.cfl.cfl_project.model.MentorToMenteeFeedBack;
import com.cfl.cfl_project.service.ManagerToCflFeedbackService;
import com.cfl.cfl_project.service.MentorToMenteeFeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/managerToCflFeedBack")
@CrossOrigin("*")
public class ManagerToCflFeedBackController{

    @Autowired
    private ManagerToCflFeedbackService managerToCflFeedbackService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ManagerToCflFeedBack managerToCflFeedBack){
        ManagerToCflFeedBack managerToCflFeedBackObj=managerToCflFeedbackService.createFeedBack(managerToCflFeedBack);
        if(managerToCflFeedBackObj!=null){
            return ResponseEntity.ok(managerToCflFeedBackObj);
        }
        else{
            return ResponseEntity.status(400).body("Failed to create ManagerToCflFeedBack");
        }
    }


    @GetMapping("/get/{year}")
    public ResponseEntity<?> getAll(@PathVariable Long year){
        List<ManagerToCflFeedBack> managerToCflFeedBacks= managerToCflFeedbackService.getAllFeedBack(year);
        if(!managerToCflFeedBacks.isEmpty()){
            return ResponseEntity.ok(managerToCflFeedBacks);
        }
        else{
            return ResponseEntity.status(400).body("Failed to get ManagerToCflFeedBack");
        }
    }
}
