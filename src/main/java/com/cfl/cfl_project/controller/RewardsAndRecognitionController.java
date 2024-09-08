package com.cfl.cfl_project.controller;

import com.cfl.cfl_project.model.Cfl;
import com.cfl.cfl_project.model.RewardsAndRecognition;
import com.cfl.cfl_project.service.RewardsAndRecognitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rewardsAndRecognition")
@CrossOrigin("*")
public class RewardsAndRecognitionController {

    @Autowired
    private RewardsAndRecognitionService rewardsAndRecognitionService;

    @PostMapping("/create")
    public ResponseEntity<?>create(@RequestParam String rewardsPersonName, @RequestParam String messagedPersonName, @RequestParam String message, @RequestParam MultipartFile file) throws IOException{
        RewardsAndRecognition rewardsAndRecognition=rewardsAndRecognitionService.create(rewardsPersonName, messagedPersonName, message, file);
        if(rewardsAndRecognition !=null){
            return ResponseEntity.ok(rewardsAndRecognition);
        }
        else{
            return ResponseEntity.status(400).body("Failed to create rewards and recognition");
        }
    }


//    @GetMapping("/get/{rewardedPersonName}")
//    public ResponseEntity<?>getRewardsAndRecognition(@PathVariable String rewardedPersonName){
//        RewardsAndRecognition rewardsAndRecognition=rewardsAndRecognitionService.getByRewardsPersonName(rewardedPersonName);
//        if(rewardsAndRecognition!=null){
//            return ResponseEntity.ok(rewardsAndRecognition);
//        }
//        else{
//            return ResponseEntity.status(400).body("Failed to get rewards and recognition");
//        }
//    }

//
//
//    @GetMapping("/getType/{rewardsAndRecognitionType}")
//    public ResponseEntity<?>getByRewardsAndRecognitionType(@PathVariable String rewardsAndRecognitionType){
//        List<RewardsRecognitionDTO> rewardsAndRecognition=rewardsAndRecognitionService.getByRewardsAndRecognitionType(rewardsAndRecognitionType);
//        if(!rewardsAndRecognition.isEmpty()){
//            return ResponseEntity.ok(rewardsAndRecognition);
//        }
//        else{
//            return ResponseEntity.status(400).body("Failed to get rewards and recognition");
//        }
//    }





    @GetMapping("/getType/{rewardsAndRecognitionType}")
    public ResponseEntity<?>getByRewardsAndRecognitionType(@PathVariable String rewardsAndRecognitionType){
        List<RewardsAndRecognition> rewardsAndRecognition=rewardsAndRecognitionService.getByRewardsAndRecognitionByType(rewardsAndRecognitionType);
        if(!rewardsAndRecognition.isEmpty()){
            return ResponseEntity.ok(rewardsAndRecognition);
        }
        else{
            return ResponseEntity.status(400).body("Failed to get rewards and recognition");
        }
    }


    @PostMapping("/createBravo")
    public ResponseEntity<?>createBravo(@RequestParam String rewardsPersonName, @RequestParam String messagedPersonName, @RequestParam String message, @RequestParam MultipartFile file) throws IOException{
        RewardsAndRecognition rewardsAndRecognition=rewardsAndRecognitionService.createBravo(rewardsPersonName, messagedPersonName, message, file);
        if(rewardsAndRecognition !=null){
            return ResponseEntity.ok(rewardsAndRecognition);
        }
        else{
            return ResponseEntity.status(400).body("Failed to create Bravo rewards and recognition");
        }
    }


    @GetMapping("/list-of-cfl/{year}")
    public ResponseEntity<?>getAllCflByYear(@PathVariable String year) {
           List<String> stringList= rewardsAndRecognitionService.listOfCfl(year);
           if(!stringList.isEmpty()){
               return ResponseEntity.ok(stringList);
           }
           return ResponseEntity.notFound().build();
        }


}
