package com.cfl.cfl_project.controller;

import com.cfl.cfl_project.model.Video;
import com.cfl.cfl_project.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/videos")
@CrossOrigin("*")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Video video){
        Video videoObj=videoService.create(video);
        if(videoObj!=null){
            return ResponseEntity.ok(videoObj);
        }
        else{
            return ResponseEntity.status(400).body("Failed to create Video");
        }
    }



    @GetMapping("/get/{year}")
    public ResponseEntity<?> create(@PathVariable String year){
        List<Video> videoObj=videoService.getByYear(year);
        if(videoObj!=null){
            return ResponseEntity.ok(videoObj);
        }
        else{
            return ResponseEntity.status(400).body("Failed To Get Video By Year");
        }
    }

}
