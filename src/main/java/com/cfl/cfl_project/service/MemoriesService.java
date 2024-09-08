package com.cfl.cfl_project.service;

import com.cfl.cfl_project.model.Memories;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public interface MemoriesService {
    List<Memories> createListOfMemories(List<MultipartFile> files,Long year) throws IOException;
    List<Memories>getAllMemoriesByYear(Long year);
}
