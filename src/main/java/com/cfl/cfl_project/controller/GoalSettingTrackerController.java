package com.cfl.cfl_project.controller;

import com.cfl.cfl_project.dto.CflGoalSettingTrackerCflTableDTO;
import com.cfl.cfl_project.dto.CflSkillCflTableDTO;
import com.cfl.cfl_project.model.GoalSettingTracker;
import com.cfl.cfl_project.service.GoalSettingTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goalSettingTracker")
@CrossOrigin("*")
public class GoalSettingTrackerController {

    @Autowired
    private GoalSettingTrackerService goalSettingTrackerService;

    @GetMapping("/{cflId}/{quarter}")
    public ResponseEntity<?> getAllGoalSettingTrackers(@PathVariable Long cflId,@PathVariable String quarter) {
       GoalSettingTracker goalSettingTracker=goalSettingTrackerService.getTrackingInfoByCflIdAndQuarter(cflId,quarter);
       if(goalSettingTracker!=null){
           return ResponseEntity.ok(goalSettingTracker);
       }
       else{
           return ResponseEntity.status(400).body("Failed to get Goal Setting Tracker");
       }
    }


    @GetMapping("/getAllGoalSettingTrackerReport")
    public ResponseEntity<?> getAllGoalSettingTrackerReport(){
        List<CflGoalSettingTrackerCflTableDTO> cflGoalSettingTrackerCflTableDTOS=goalSettingTrackerService.getAllGoalSettingTracker();
        if(!cflGoalSettingTrackerCflTableDTOS.isEmpty()){
            return ResponseEntity.ok(cflGoalSettingTrackerCflTableDTOS);
        }
        else{
            return ResponseEntity.status(400).body("Failed to get all cflGoalSettingTrackerCflTableDTOS ");
        }
    }


    @GetMapping("/getAllGoalSettingTrackerByQuarterAndYear/{Quarter}/{year}")
    public ResponseEntity<?> getAllGoalSettingTrackerByQuarterAndYear(@PathVariable String Quarter,@PathVariable Long year) {
        List<GoalSettingTracker> goalSettingTrackerList=goalSettingTrackerService.getAllGoalSettingTrackerByQuarterAndYear(Quarter,year);
        if(!goalSettingTrackerList.isEmpty()){
            return ResponseEntity.ok(goalSettingTrackerList);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
