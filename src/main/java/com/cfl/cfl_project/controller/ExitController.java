package com.cfl.cfl_project.controller;

import com.cfl.cfl_project.model.Exit;
import com.cfl.cfl_project.service.ExitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exit")
@CrossOrigin("*")
public class ExitController {

    @Autowired
    private ExitService exitService;

    @PostMapping("/add")
    public ResponseEntity<?>saveExit(@RequestBody Exit exit){
        System.out.println(exit+"exit");
        Exit exitObj=exitService.saveExit(exit);
        return exitObj !=null ? ResponseEntity.ok(exitObj) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/getByEmpId/{empId}")
    public ResponseEntity<?>findExit(@PathVariable Long empId){
        Exit exitObj=exitService.findExitByEmpId(empId);
        return exitObj !=null ? ResponseEntity.ok(exitObj) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/deleteByEmpId/{empId}")
    public ResponseEntity<String>deleteExitByEmpId(@PathVariable Long empId) {
        try {
            exitService.deleteExitByEmpId(empId);
            return ResponseEntity.ok("deleted");
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/sendCFLExitMailToManagerAndHR/{empId}")
    public ResponseEntity<?>sendCFLExitMailToManagerAndHR(@PathVariable Long empId) {
        try {
            exitService.sendCFLExitMailToManagerAndHR(empId);
            return ResponseEntity.ok("mail sent");
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    // ======================== EXIT WITHDRAWAL MAIL ===========================

    @PostMapping("/sendCFLExitWithdrawalMailToManagerAndHR/{empId}")
    public ResponseEntity<?>sendCFLExitWithdrawalMailToManagerAndHR(@PathVariable Long empId) {
        try {
            exitService.sendCFLExitWithdrawalMailToManagerAndHR(empId);
            return ResponseEntity.ok("withdrawal mail sent");
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    // ======================== EXIT ACCEPT MAIL ===========================

    // manager accept cfl exit requests
    @PostMapping("/sendExitRequestAcceptanceMailToManagerAndHRAndCFL/{empId}")
    public ResponseEntity<?>sendExitRequestAcceptanceMailToManagerAndHRAndCFL(@PathVariable Long empId) {
        try {
            exitService.sendExitRequestAcceptanceMailToManagerAndHRAndCFL(empId);
            return ResponseEntity.ok("exit request mail accepted");
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }


    // ======================== EXIT DECLINE MAIL ===========================


    // adding manager decline reason

    @PostMapping("/addDeclineReason/{empId}")
    public ResponseEntity<?>addDeclineReason(@PathVariable Long empId,@RequestParam String declineReason) {
            Exit exit=exitService.addDeclineReason(empId,declineReason);
            if(exit !=null){
                return ResponseEntity.ok("declined");
            }
            else{
                return ResponseEntity.badRequest().build();
            }
    }

    // manager decline cfl exit requests
    @PostMapping("/sendExitRequestDeclinedMailToManagerAndHRAndCFL/{empId}")
    public ResponseEntity<?>sendExitRequestDeclinedMailToManagerAndHRAndCFL(@PathVariable Long empId,@RequestParam String declineReason) {
        try {
            exitService.sendExitRequestDeclinedMailToManagerAndHRAndCFL(empId,declineReason);
            return ResponseEntity.ok("exit request mail declined");
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }




    // ======================== EXIT EXTEND MAIL ===========================



    // adding manager extend reason

    @PostMapping("/addExtendReason/{empId}")
    public ResponseEntity<?>addExtendReason(@PathVariable Long empId,@RequestParam String extendReason,@RequestParam String formattedDate) {
        Exit exit=exitService.addExtendReason(empId,extendReason,formattedDate);
        if(exit !=null){
            return ResponseEntity.ok("extend");
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }

    // manager extend cfl exit requests
    @PostMapping("/sendExitRequestExtendedMailToManagerAndHRAndCFL/{empId}")
    public ResponseEntity<?>sendExitRequestExtendedMailToManagerAndHRAndCFL(@PathVariable Long empId,@RequestParam String extendReason,@RequestParam String formattedDate) {
        try {
            exitService.sendExitRequestExtendedMailToManagerAndHRAndCFL(empId,extendReason,formattedDate);
            return ResponseEntity.ok("exit request mail extend");
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }







}
