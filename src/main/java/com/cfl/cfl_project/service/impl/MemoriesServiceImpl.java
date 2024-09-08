package com.cfl.cfl_project.service.impl;

import com.cfl.cfl_project.model.Memories;
import com.cfl.cfl_project.repository.MemoriesRepository;
import com.cfl.cfl_project.service.MemoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MemoriesServiceImpl implements MemoriesService {

    @Autowired
    private MemoriesRepository memoriesRepository;

    @Override
    public List<Memories> createListOfMemories(List<MultipartFile> files,Long year) throws IOException {
        List<Memories> memories=new ArrayList<>();
       for(MultipartFile memory:files){
           Memories memoryObj=new Memories();
           memoryObj.setFileName(memory.getOriginalFilename());
           memoryObj.setFileData(memory.getBytes());
           memoryObj.setYear(year);
           memories.add(memoryObj);
       }
       return memoriesRepository.saveAll(memories);
    }


    @Override
    public List<Memories> getAllMemoriesByYear(Long year) {
        return memoriesRepository.findByYear(year);
    }
}
