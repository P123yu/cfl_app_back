package com.cfl.cfl_project.controller;


import com.cfl.cfl_project.model.Memories;
import com.cfl.cfl_project.service.MemoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/memories")
@CrossOrigin("*")
public class MemoriesController {

    @Autowired
    private MemoriesService memoriesService;

//    @PostMapping("/post")
//    public ResponseEntity<?> createListOfMemories(@RequestPart List<Map<MultipartFile, Long>> memories){
//        List<Memories> savedMemories=memoriesService.createListOfMemories(memories);
//        if(savedMemories !=null){
//            return ResponseEntity.ok(savedMemories);
//        }
//        else{
//            return ResponseEntity.status(400).body("Failed to create memories");
//        }
//    }


    @PostMapping("/post/{year}")
    public ResponseEntity<?> createListOfMemories(@RequestPart List<MultipartFile>files,@PathVariable Long year)throws IOException {
        List<Memories> savedMemories = memoriesService.createListOfMemories(files,year);
        if (savedMemories != null) {
            return ResponseEntity.ok(savedMemories);
        } else {
            return ResponseEntity.status(400).body("Failed to create memories");
        }
    }

    @GetMapping("/getMemoriesByYear/{year}")
    public ResponseEntity<?>getAllMemoriesByYear(@PathVariable Long year){
       List<Memories>memoriesList= memoriesService.getAllMemoriesByYear(year);
       if(!memoriesList.isEmpty()){
           return ResponseEntity.ok(memoriesList);
       }
       else{
           return ResponseEntity.status(500).body("Memories not found for the given year");
       }
    }
}
