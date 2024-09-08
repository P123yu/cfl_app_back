//package com.cfl.cfl_project.controller;
//
//import com.cfl.cfl_project.dto.CflGoalSettingCflTableDTO;
//import com.cfl.cfl_project.dto.CflSkillCflTableDTO;
//import com.cfl.cfl_project.model.SixtyDaysGoals;
//import com.cfl.cfl_project.model.ThirtyDaysGoals;
//import com.cfl.cfl_project.service.ThirtyDaysGoalsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import java.util.List;
//
//@RestController
//@CrossOrigin("*")
//@RequestMapping("/Goals/thirty-days-goals")
//public class ThirtyDaysGoalsController {
//
//    @Autowired
//    private ThirtyDaysGoalsService thirtyDaysGoalsService;
//
//    // Endpoint to create new thirty days goals
//    @PostMapping("/emp/{empId}")
//    public ResponseEntity<List<ThirtyDaysGoals>> createThirtyDaysGoals(
//            @PathVariable Long empId,
//            @RequestBody List<ThirtyDaysGoals> thirtyDaysGoals) {
//
//        List<ThirtyDaysGoals> createdGoals = thirtyDaysGoalsService.createThirtyDaysGoals(empId, thirtyDaysGoals);
//        if(!createdGoals.isEmpty()){
//            return new ResponseEntity<>(createdGoals, HttpStatus.CREATED);
//        }
//        else{
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }
//
//
//    // Endpoint to get thirty days goals by employee ID
//    @GetMapping("/emp/{empId}/{quarter}/{year}")
//    public ResponseEntity<List<ThirtyDaysGoals>> getThirtyDaysGoalsByEmpIdAndQuarter(@PathVariable Long empId,@PathVariable String quarter,@PathVariable Long year) {
//        List<ThirtyDaysGoals> goals = thirtyDaysGoalsService.getThirtyDaysGoalsByEmpIdAndQuarter(empId,quarter,year);
//        if (goals.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(goals, HttpStatus.OK);
//    }
//
//
//    @PutMapping("/update")
//    public ResponseEntity<?> updateThirtyDaysGoals(@RequestBody List<ThirtyDaysGoals> thirtyDaysGoals) {
//
//        List<ThirtyDaysGoals> updatedGoals = thirtyDaysGoalsService.updateThirtyDaysGoals(thirtyDaysGoals);
//        if(!updatedGoals.isEmpty()){
//            return new ResponseEntity<>(updatedGoals, HttpStatus.CREATED);
//        }
//        else{
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//
//    @GetMapping("/getAllGoalSetting")
//    public ResponseEntity<?> getAllGoalSetting(){
//        List<CflGoalSettingCflTableDTO> cflGoalSettingCflTableDTOS=thirtyDaysGoalsService.getAllGoalSetting();
//        if(!cflGoalSettingCflTableDTOS.isEmpty()){
//            return ResponseEntity.ok(cflGoalSettingCflTableDTOS);
//        }
//        else{
//            return ResponseEntity.status(400).body("Failed to get all cflGoalSettingCflTableDTOS ");
//        }
//    }
//
//
//
//
//}





package com.cfl.cfl_project.controller;

import com.cfl.cfl_project.dto.CflGoalSettingCflTableDTO;
import com.cfl.cfl_project.dto.CflSkillCflTableDTO;
import com.cfl.cfl_project.model.SixtyDaysGoals;
import com.cfl.cfl_project.model.ThirtyDaysGoals;
import com.cfl.cfl_project.service.ThirtyDaysGoalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/Goals/thirty-days-goals")
public class ThirtyDaysGoalsController {

    @Autowired
    private ThirtyDaysGoalsService thirtyDaysGoalsService;

    // Endpoint to create new thirty days goals
    @PostMapping("/emp/{empId}/{quarter}")
    public ResponseEntity<List<ThirtyDaysGoals>> createThirtyDaysGoals(
            @PathVariable Long empId,
            @PathVariable String quarter,
            @RequestBody List<ThirtyDaysGoals> thirtyDaysGoals) {

        List<ThirtyDaysGoals> createdGoals = thirtyDaysGoalsService.createThirtyDaysGoals(empId, quarter, thirtyDaysGoals);
        if(!createdGoals.isEmpty()){
            return new ResponseEntity<>(createdGoals, HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    // Endpoint to get thirty days goals by employee ID
    @GetMapping("/emp/{empId}/{quarter}/{year}")
    public ResponseEntity<List<ThirtyDaysGoals>> getThirtyDaysGoalsByEmpIdAndQuarter(@PathVariable Long empId,@PathVariable String quarter,@PathVariable Long year) {
        List<ThirtyDaysGoals> goals = thirtyDaysGoalsService.getThirtyDaysGoalsByEmpIdAndQuarter(empId,quarter,year);
        if (goals.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(goals, HttpStatus.OK);
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateThirtyDaysGoals(@RequestBody List<ThirtyDaysGoals> thirtyDaysGoals) {

        List<ThirtyDaysGoals> updatedGoals = thirtyDaysGoalsService.updateThirtyDaysGoals(thirtyDaysGoals);
        if(!updatedGoals.isEmpty()){
            return new ResponseEntity<>(updatedGoals, HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getAllGoalSetting")
    public ResponseEntity<?> getAllGoalSetting(){
        List<CflGoalSettingCflTableDTO> cflGoalSettingCflTableDTOS=thirtyDaysGoalsService.getAllGoalSetting();
        if(!cflGoalSettingCflTableDTOS.isEmpty()){
            return ResponseEntity.ok(cflGoalSettingCflTableDTOS);
        }
        else{
            return ResponseEntity.status(400).body("Failed to get all cflGoalSettingCflTableDTOS ");
        }
    }

    @GetMapping("/countTotalQuarter/{empId}/{year}")
    public Integer countTotalQuarter(@PathVariable Long empId,@PathVariable Long year) {
        return thirtyDaysGoalsService.countTotalQuarter(empId,year);
    }


    @GetMapping("/showAllCflWhichFilledGoals/{quarter}")
    public ResponseEntity<List<String>> showListOfCflNameWhoCompletedGoalSetting(@PathVariable String quarter) {
        List<String>cflList= thirtyDaysGoalsService.showListOfCflNameWhoCompletedGoalSetting(quarter);
        return !cflList.isEmpty() ? ResponseEntity.ok(cflList) : ResponseEntity.notFound().build();
    }

}
