package com.cfl.cfl_project.controller;

import com.cfl.cfl_project.model.HR;
import com.cfl.cfl_project.model.Mentor;
import com.cfl.cfl_project.service.HRService;
import com.cfl.cfl_project.service.MentorService;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/hr")
@CrossOrigin("*")
public class HRController {

    @Autowired
    private HRService hrService;

    // search in register

//    @GetMapping("/get/{mEmail}")
//    private ResponseEntity<?> getByUserName(@PathVariable("mentorEmail") String userName){
//        Boolean mentorResponse=mentorService.getByUserName(userName);
//        if(mentorResponse){
//            return ResponseEntity.ok(true);
//        }
//        else{
//            return ResponseEntity.ok(false);
//        }
//
//    }



//    @GetMapping("/getHrEmail/{hrEmail}")
//    private ResponseEntity<?> getByHrEmail(@PathVariable String hrEmail){
//        Boolean Response=hrService.getByHrEmail(hrEmail);
//        if(mentorResponse){
//            return ResponseEntity.ok(true);
//        }
//        else{
//            return ResponseEntity.ok(false);
//        }
//
//    }

//
//    @PostMapping("/create")
//    public ResponseEntity<?> createMentor(
//            @RequestParam("mentorId") Long mentorId,
//            @RequestParam("empId") String empId,
//            @RequestParam("mentorName") String mentorName,
//            @RequestParam("mentorEmail") String mentorEmail,
//            @RequestParam("mentorDepartment") String mentorDepartment,
//            @RequestParam("mentorLocation") String mentorLocation,
//            @RequestParam("mentorDesignation") String mentorDesignation,
//            @RequestParam("mentorFile") MultipartFile mentorFile) throws IOException {
//
//            Mentor mentor = mentorService.createMentor(mentorId, empId, mentorName, mentorEmail,
//                    mentorDepartment, mentorLocation, mentorDesignation, mentorFile);
//            if(mentor != null){
//                return ResponseEntity.ok(mentor);
//            }
//            else{
//                return ResponseEntity.status(500).body(null);
//            }
//    }



    @PostMapping("/create")
    public ResponseEntity<?> createMentor(
            @RequestParam("hrId") Long hrId,
            @RequestParam("hrName") String hrName,
            @RequestParam("hrEmail") String hrEmail,
            @RequestParam("hrLocation") String hrLocation,
            @RequestParam("hrFile") MultipartFile hrFile) throws IOException {

        HR hr = hrService.createHR(hrId,  hrName, hrEmail,
                hrLocation,  hrFile);
        if(hr != null){
            return ResponseEntity.ok(hr);
        }
        else{
            return ResponseEntity.status(500).body(null);
        }
    }


    @GetMapping("/{hrEmail}")
    public ResponseEntity<?> getHRByEmail(@PathVariable("hrEmail") String hrEmail){
        System.out.println(hrEmail+"hrEmail...");
       HR hr= hrService.getHRByHRMail(hrEmail);
       if(hr !=null){
           return ResponseEntity.ok(hr);
       }
       else{
           return ResponseEntity.status(500).body("hr Response not found");
       }
    }

//
//    // filter by mentor email
//    @GetMapping("/getAll")
//    public ResponseEntity<?> getAll(){
//        List<String> mentorResponse=mentorService.getAll();
//        if(!mentorResponse.isEmpty()){
//            return ResponseEntity.ok(mentorResponse);
//        }
//        else{
//            return ResponseEntity.status(500).body("Mentor Response not found");
//        }
//    }
//

    @PostMapping("/uploadHRFile")
    public ResponseEntity<?> uploadHRImage(@RequestParam Long hrId, @RequestPart MultipartFile file) throws IOException {
        HR hr=hrService.uploadHRImage(hrId,file);
        if(hr!=null){
            return ResponseEntity.ok(hr);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hr found");
        }
    }



    // for chart

    @GetMapping("/getAllHR")
    public ResponseEntity<?>getAllHR(){
        List<HR> hrList=hrService.getAllHR();
        if(!hrList.isEmpty()){
            return ResponseEntity.ok(hrList);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/countScreenTime/{userMail}/{timeInMinute}")
    public ResponseEntity<String> countHRScreenTime(@PathVariable String userMail,@PathVariable String timeInMinute) {
        String result=hrService.countHRScreenTime(userMail,timeInMinute);
        if(result !=null){
            return ResponseEntity.ok(result);
        }
        else{
            return ResponseEntity.status(400).body("Failed to get hr screen-time");
        }
    }

}
