package com.cfl.cfl_project.controller;

import com.cfl.cfl_project.model.SixtyDaysGoals;
import com.cfl.cfl_project.model.ThirtyDaysGoals;
import com.cfl.cfl_project.service.SixtyDaysGoalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/Goals/sixty-days-goals")
public class SixtyDaysGoalsController {

    @Autowired
    private SixtyDaysGoalsService sixtyDaysGoalsService;

    @PostMapping("/emp/{empId}/{quarter}")
    public ResponseEntity<?> createSixtyDaysGoals(
            @PathVariable Long empId,
            @PathVariable String quarter,
            @RequestBody List<SixtyDaysGoals> sixtyDaysGoals) {

        List<SixtyDaysGoals> createdGoals = sixtyDaysGoalsService.createSixtyDaysGoals(empId, quarter, sixtyDaysGoals);
        if(!createdGoals.isEmpty()){
            return new ResponseEntity<>(createdGoals, HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/emp/{empId}/{quarter}")
    public ResponseEntity<?> getSixtyDaysGoalsByEmpIdByQuarter(@PathVariable Long empId,@PathVariable String quarter) {
        List<SixtyDaysGoals> goals = sixtyDaysGoalsService.getSixtyDaysGoalsByEmpIdAndQuarter(empId,quarter);
        if (goals.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(goals, HttpStatus.OK);
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateSixtyDaysGoals(@RequestBody List<SixtyDaysGoals> sixtyDaysGoals) {

        List<SixtyDaysGoals> updatedGoals = sixtyDaysGoalsService.updateSixtyDaysGoals(sixtyDaysGoals);
        if(!updatedGoals.isEmpty()){
            return new ResponseEntity<>(updatedGoals, HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
