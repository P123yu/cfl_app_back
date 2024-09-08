package com.cfl.cfl_project.controller;


import com.cfl.cfl_project.model.MentorToMenteeFeedBack;
import com.cfl.cfl_project.service.MentorToMenteeFeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mentorToMenteeFeedBack")
@CrossOrigin("*")
public class MentorToMenteeFeedBackController{

    @Autowired
    private MentorToMenteeFeedBackService mentorToMenteeFeedBackService;

    @PostMapping("/create")
    public ResponseEntity<?>create(@RequestBody MentorToMenteeFeedBack mentorToMenteeFeedBack){
        MentorToMenteeFeedBack mentorToMenteeFeedBackObj=mentorToMenteeFeedBackService.createFeedBack(mentorToMenteeFeedBack);
        if(mentorToMenteeFeedBackObj!=null){
            return ResponseEntity.ok(mentorToMenteeFeedBackObj);
        }
        else{
            return ResponseEntity.status(400).body("Failed to create MentorToMenteeFeedBack");
        }
    }


    @GetMapping("/get/{year}")
    public ResponseEntity<?> getAll(@PathVariable Long year){
        List<MentorToMenteeFeedBack>mentorToMenteeFeedBacks= mentorToMenteeFeedBackService.getAllFeedBack(year);
        if(!mentorToMenteeFeedBacks.isEmpty()){
            return ResponseEntity.ok(mentorToMenteeFeedBacks);
        }
        else{
            return ResponseEntity.status(400).body("Failed to get MentorToMenteeFeedBacks");
        }
    }
}
