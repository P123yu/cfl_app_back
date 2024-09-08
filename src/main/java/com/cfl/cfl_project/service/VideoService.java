package com.cfl.cfl_project.service;

import com.cfl.cfl_project.model.Video;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface VideoService {

    Video create(Video video);
    List<Video> getByYear(String year);
}
