package com.cfl.cfl_project.controller;

import com.cfl.cfl_project.dto.CflSkillCflTableDTO;
import com.cfl.cfl_project.dto.ManagerRatingCflTableDTO;
import com.cfl.cfl_project.model.Cfl;
import com.cfl.cfl_project.model.ManagerRating;
import com.cfl.cfl_project.model.PerformanceDTO;
import com.cfl.cfl_project.service.ManagerRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/managerRating")
@CrossOrigin("*")

public class ManagerRatingController {

    @Autowired
    private ManagerRatingService managerRatingService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ManagerRating managerRating){
        ManagerRating managerRating1=managerRatingService.createManagerRating(managerRating);
        if(managerRating1 !=null){
            return ResponseEntity.ok(managerRating1);
        }
        else{
            return ResponseEntity.status(400).body("Failed to create Manager Rating");
        }
    }


    @GetMapping("/get/{empId}")
    public ResponseEntity<?> getManagerRatingByEmpId(@PathVariable Long empId) {
        ManagerRating managerRating1 = managerRatingService.getManagerRatingByEmpId(empId);
        if (managerRating1 != null) {
            return ResponseEntity.ok(managerRating1);
        } else {
            return ResponseEntity.status(400).body("Failed to create Manager Rating");
        }
    }


    @GetMapping("/getAllManagerRating")
    public ResponseEntity<?>getAllManagerRating() {
       List<ManagerRating>managerRatings= managerRatingService.getAll();
       if(!managerRatings.isEmpty()) {
           return ResponseEntity.ok(managerRatings);
       }
           else{
               return ResponseEntity.status(400).body("Failed to create Manager Rating");
           }
       }


       @PostMapping("/sendRatingMail")
       public ResponseEntity<?> managerRatingEmail(@RequestParam  Long empId,@RequestParam String potentialLevel,@RequestParam String performanceLevel,@RequestParam String talentLevel,@RequestParam String managerEmail) {
           Boolean managerRating=managerRatingService.managerRatingEmail(empId,potentialLevel,performanceLevel,talentLevel,managerEmail);
           if(managerRating!=null){
               return ResponseEntity.ok("Rating sent successfully !!!");
           }
           else{
               return ResponseEntity.badRequest().body("Rating Not sent !!!");
           }
       }


    @GetMapping("/getManagerRatingReport")
    public ResponseEntity<?> getManagerRatingReport(){
        List<ManagerRatingCflTableDTO> managerRatingCflTableDTOS=managerRatingService.getAllRating();
        if(!managerRatingCflTableDTOS.isEmpty()){
            return ResponseEntity.ok(managerRatingCflTableDTOS);
        }
        else{
            return ResponseEntity.status(400).body("Failed to get all managerRatingCflTableDTOS ");
        }
    }



    @GetMapping("/getTalentLevel/{empId}")
    public String getTalentLevel(@PathVariable  Long empId){
        String ans=managerRatingService.getTalentLevel(empId);
        return !ans.isEmpty()?ans:"";
    }

    @GetMapping("/getListOfSpecificTalentLevelByYear/{talentLevel}/{year}")
    public ResponseEntity<?> getListOfSpecificTalentLevelByYear(@PathVariable String talentLevel, @PathVariable  Long year) {
        List<PerformanceDTO>performanceDTOList=managerRatingService.getListOfSpecificTalentLevelByYear(talentLevel, year);
        if(!performanceDTOList.isEmpty()){
            return ResponseEntity.ok(performanceDTOList);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/getAnnualByEmpIdAndYear/{empId}/{year}")
    public ResponseEntity<?> getManagerRatingByEmpIdAndYear(@PathVariable Long empId,@PathVariable Long year){
        List<ManagerRating> managerRatingList=managerRatingService.getManagerRatingByEmpIdAndYear(empId,year);
        if(!managerRatingList.isEmpty()){
            return ResponseEntity.ok(managerRatingList);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
