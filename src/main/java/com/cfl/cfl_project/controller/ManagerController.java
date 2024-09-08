package com.cfl.cfl_project.controller;

import com.cfl.cfl_project.model.Manager;
import com.cfl.cfl_project.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/manager")
@CrossOrigin("*")

public class ManagerController {

    @Autowired
    private ManagerService managerService;

//    @PostMapping("/create")
//    public ResponseEntity<?>createManager(@RequestParam Long managerId,@RequestParam String managerName,@RequestParam String managerEmail,@RequestParam String managerDepartment,@RequestParam String managerLocation,@RequestParam String managerDesignation,@RequestPart MultipartFile managerFile) throws IOException {
//        Manager manager = managerService.createManager(managerId, managerName, managerEmail, managerDepartment, managerLocation, managerDesignation, managerFile);
//        if(manager!=null){
//            return ResponseEntity.ok(manager);
//        }
//        else{
//            return ResponseEntity.status(400).body("Failed to create manager");
//        }
//    }



//    @PostMapping("/create")
//    public ResponseEntity<?>createManager(@RequestParam Long managerId,@RequestParam String managerName,@RequestParam String managerEmail,@RequestParam String managerDepartment,@RequestParam String managerLocation,@RequestParam String managerDesignation) {
//        Manager manager = managerService.createManager(managerId, managerName, managerEmail, managerDepartment, managerLocation, managerDesignation);
//        if(manager!=null){
//            return ResponseEntity.ok(manager);
//        }
//        else{
//            return ResponseEntity.status(400).body("Failed to create manager");
//        }
//    }


    @PostMapping("/create")
    public ResponseEntity<?>createManager(@RequestBody Manager manager) {
        Manager managerObj = managerService.createChangeRequestMentor(manager);
        if(managerObj!=null){
            return ResponseEntity.ok(managerObj);
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{managerEmail}")
    public ResponseEntity<?> getMentorByEmail(@PathVariable("managerEmail") String managerEmail){
        Manager manager= managerService.getManagerByManagerMail(managerEmail);
        if(manager!=null){
            return ResponseEntity.ok(manager);
        }
        else{
            return ResponseEntity.status(404).body("manager Response not found");
        }
    }


    @GetMapping("/approvedGoalSetting/{cflId}/{quarter}")
    public ResponseEntity<?> getApprovedGoalSetting(@PathVariable Long cflId,@PathVariable String quarter){
        String approved= managerService.createEmailOnApprove(cflId,quarter);
        if(!approved.isEmpty()){
            return ResponseEntity.ok(approved);
        }
        else{
            return ResponseEntity.status(404).body("Approve Response not found");
        }
    }






    @GetMapping("/managerExists/{managerEmail}")
    public ResponseEntity<?> managerExists(@PathVariable String managerEmail) {
        Boolean manager= managerService.managerExists(managerEmail);
        if(manager!=null) {
            return ResponseEntity.ok(manager);
        }
        else{
            return ResponseEntity.status(404).body("Manager Response not found");
        }
    }


    @PostMapping("/uploadManagerFile")
    public ResponseEntity<?> uploadManagerImage(@RequestParam Long managerId, @RequestPart MultipartFile file) throws IOException {
        Manager manager=managerService.uploadManagerImage(managerId,file);
        if(manager!=null){
            return ResponseEntity.ok(manager);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No manager found");
        }
    }


    // for chart

    @GetMapping("/getAllManager")
    public ResponseEntity<?>getAllManager(){
        List<Manager>managerList=managerService.getAllManager();
        if(!managerList.isEmpty()){
            return ResponseEntity.ok(managerList);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/countScreenTime/{userMail}/{timeInMinute}")
    public ResponseEntity<String> countManagerScreenTime(@PathVariable String userMail,@PathVariable String timeInMinute) {
        String result=managerService.countManagerScreenTime(userMail,timeInMinute);
        if(result !=null){
            return ResponseEntity.ok(result);
        }
        else{
            return ResponseEntity.status(400).body("Failed to get manager screen-time");
        }

    }

}
