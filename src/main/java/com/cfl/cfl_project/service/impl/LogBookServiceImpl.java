package com.cfl.cfl_project.service.impl;

import com.cfl.cfl_project.dto.CflLogBookCflTableDTO;
import com.cfl.cfl_project.dto.CflSkillCflTableDTO;
import com.cfl.cfl_project.model.Cfl;
import com.cfl.cfl_project.model.CflSkill;
import com.cfl.cfl_project.model.LogBook;
import com.cfl.cfl_project.repository.CflRepository;
import com.cfl.cfl_project.repository.LogBookRepository;
import com.cfl.cfl_project.service.LogBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


@Service
public class LogBookServiceImpl implements LogBookService {

    @Autowired
    private LogBookRepository logBookRepository;

    @Override
    public LogBook uploadLogBook(Long empId, MultipartFile certificateFile) throws IOException {
        LogBook logBook = new LogBook();
        logBook.setEmpId(empId);
        LocalDate date = LocalDate.now();
        logBook.setDate(date);
        LocalTime time= LocalTime.now().truncatedTo(ChronoUnit.SECONDS);;
        logBook.setTime(time);
        logBook.setLogBookFileName(certificateFile.getOriginalFilename());
        logBook.setLogBookFileData(certificateFile.getBytes());
        return logBookRepository.save(logBook);
    }

    @Override
    public List<LogBook> downloadLogBook(Long empId) {
        return logBookRepository.findByEmpId(empId);
    }

    @Autowired
    private CflRepository cflRepository;


    @Override
    public List<CflLogBookCflTableDTO> downloadLogBookReport() {
        List<CflLogBookCflTableDTO>cflLogBookCflTableDTOS=new ArrayList<>();
        List<LogBook>logBooks=logBookRepository.findAll();
        for(LogBook logBook : logBooks){
            CflLogBookCflTableDTO cflLogBookCflTableDTO=new CflLogBookCflTableDTO();
            Cfl cfl=cflRepository.findByEmpId(logBook.getEmpId());
            cflLogBookCflTableDTO.setEmpId(logBook.getEmpId());

            cflLogBookCflTableDTO.setCflName(cfl.getCflFirstName());
            cflLogBookCflTableDTO.setCflEmail(cfl.getCflEmail());
            cflLogBookCflTableDTO.setCflDepartment(cfl.getCflDepartment());
            cflLogBookCflTableDTO.setCflLocation(cfl.getCflLocation());
            cflLogBookCflTableDTO.setReportingManagerName(cfl.getReportingManager());
            cflLogBookCflTableDTO.setReportingManagerEmail(cfl.getReportingManagerMail());
            cflLogBookCflTableDTO.setCflDepartment(cfl.getCflDepartment());
            cflLogBookCflTableDTO.setCflSubDepartment(cfl.getCflSubDepartment());


            cflLogBookCflTableDTO.setSubmittedDate(logBook.getDate());
            cflLogBookCflTableDTO.setSubmittedTime(logBook.getTime());
            cflLogBookCflTableDTO.setLogBookName(logBook.getLogBookFileName());


            cflLogBookCflTableDTOS.add(cflLogBookCflTableDTO);
        }
        return cflLogBookCflTableDTOS;
    }

}
