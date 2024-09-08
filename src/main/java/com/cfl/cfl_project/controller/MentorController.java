package com.cfl.cfl_project.controller;

import com.cfl.cfl_project.model.Cfl;
import com.cfl.cfl_project.model.Mentor;
import com.cfl.cfl_project.model.MentorResponse;
import com.cfl.cfl_project.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/mentor")
@CrossOrigin("*")
public class MentorController {

    @Autowired
    private MentorService mentorService;

    // search in register

    @GetMapping("/get/{mentorEmail}")
    private ResponseEntity<?> getByUserName(@PathVariable("mentorEmail") String userName){
        Boolean mentorResponse=mentorService.getByUserName(userName);
        if(mentorResponse){
            return ResponseEntity.ok(true);
        }
        else{
            return ResponseEntity.ok(false);
        }

    }



    @GetMapping("/getMentorEmail/{mentorEmail}")
    private ResponseEntity<?> getByMentorEmail(@PathVariable("mentorEmail") String mentorEmail){
        Boolean mentorResponse=mentorService.getByMentorEmail(mentorEmail);
        if(mentorResponse){
            return ResponseEntity.ok(true);
        }
        else{
            return ResponseEntity.ok(false);
        }

    }

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



//    @PostMapping("/create")
//    public ResponseEntity<?> createMentor(
//            @RequestParam("mentorId") Long mentorId,
//            @RequestParam("mentorName") String mentorName,
//            @RequestParam("mentorEmail") String mentorEmail,
//            @RequestParam("mentorDepartment") String mentorDepartment,
//            @RequestParam("mentorLocation") String mentorLocation,
//            @RequestParam("mentorDesignation") String mentorDesignation,
//            @RequestParam("mentorFile") MultipartFile mentorFile) throws IOException {
//
//        Mentor mentor = mentorService.createMentor(mentorId,  mentorName, mentorEmail,
//                mentorDepartment, mentorLocation, mentorDesignation, mentorFile);
//        if(mentor != null){
//            return ResponseEntity.ok(mentor);
//        }
//        else{
//            return ResponseEntity.status(500).body(null);
//        }
//    }



//    @PostMapping("/create")
//    public ResponseEntity<?> createChangeRequestMentor(
//            @RequestParam("mentorId") Long mentorId,
//            @RequestParam("mentorName") String mentorName,
//            @RequestParam("mentorEmail") String mentorEmail,
//            @RequestParam("mentorDepartment") String mentorDepartment,
//            @RequestParam("mentorLocation") String mentorLocation,
//            @RequestParam("mentorDesignation") String mentorDesignation) {
//
//        Mentor mentor = mentorService.createChangeRequestMentor(mentorId,  mentorName, mentorEmail,
//                mentorDepartment, mentorLocation, mentorDesignation);
//        if(mentor != null){
//            return ResponseEntity.ok(mentor);
//        }
//        else{
//            return ResponseEntity.status(500).body(null);
//        }
//    }


    @PostMapping("/create")
    public ResponseEntity<?> createChangeRequestMentor(@RequestBody Mentor mentor) {
        System.out.println(mentor+"mentor111");
        Mentor mentorObj = mentorService.createChangeRequestMentor(mentor);
        if(mentorObj != null){
            return ResponseEntity.ok(mentorObj);
        }
        else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{mentorEmail}")
    public ResponseEntity<?> getMentorByEmail(@PathVariable("mentorEmail") String mentorEmail){
       Mentor mentorResponse= mentorService.getMentorByMentorMail(mentorEmail);
       if(mentorResponse !=null){
           return ResponseEntity.ok(mentorResponse);
       }
       else{
           return ResponseEntity.status(500).body("mentor Response not found");
       }
    }


    // filter by mentor email
    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
        List<String> mentorResponse=mentorService.getAll();
        if(!mentorResponse.isEmpty()){
            return ResponseEntity.ok(mentorResponse);
        }
        else{
            return ResponseEntity.status(500).body("Mentor Response not found");
        }
    }


    @PostMapping("/uploadMentorFile")
    public ResponseEntity<?> uploadMentorImage(@RequestParam Long mentorId, @RequestPart MultipartFile file) throws IOException {
        Mentor mentor=mentorService.uploadMentorImage(mentorId,file);
        if(mentor!=null){
            return ResponseEntity.ok(mentor);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Cfl found");
        }
    }



    // for chart

    @GetMapping("/getAllMentor")
    public ResponseEntity<?>getAllMentor(){
        List<Mentor> mentorList=mentorService.getAllMentor();
        if(!mentorList.isEmpty()){
            return ResponseEntity.ok(mentorList);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/countScreenTime/{userMail}/{timeInMinute}")
    public ResponseEntity<String> countMentorScreenTime(@PathVariable String userMail,@PathVariable String timeInMinute) {
        String result=mentorService.countMentorScreenTime(userMail,timeInMinute);
        if(result !=null){
            return ResponseEntity.ok(result);
        }
        else{
            return ResponseEntity.status(400).body("Failed to get mentor screen-time");
        }

    }

}
