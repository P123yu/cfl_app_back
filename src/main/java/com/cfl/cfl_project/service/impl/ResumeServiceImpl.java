package com.cfl.cfl_project.service.impl;

import com.cfl.cfl_project.model.LogBook;
import com.cfl.cfl_project.model.Resume;
import com.cfl.cfl_project.repository.ResumeRepository;
import com.cfl.cfl_project.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ResumeServiceImpl implements ResumeService {

    @Autowired
    private ResumeRepository  resumeRepository;


    @Override
    public Resume uploadResume(Long empId, MultipartFile resumeFile) throws IOException {
        Resume resume = new Resume();
        resume.setEmpId(empId);
        LocalDate date = LocalDate.now();
        resume.setDate(date);
        LocalTime time= LocalTime.now().truncatedTo(ChronoUnit.SECONDS);;
        resume.setTime(time);
        resume.setResumeFileName(resumeFile.getOriginalFilename());
        resume.setResumeFileData(resumeFile.getBytes());
        return resumeRepository.save(resume);
    }

    @Override
    public List<Resume> downloadResume(Long empId) {
        return resumeRepository.findByEmpId(empId);
    }
}
