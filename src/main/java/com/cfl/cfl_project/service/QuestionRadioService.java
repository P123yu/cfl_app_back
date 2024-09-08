package com.cfl.cfl_project.service;

import com.cfl.cfl_project.model.QuestionRadio;
import org.springframework.stereotype.Service;

@Service
public interface QuestionRadioService {
    QuestionRadio create(QuestionRadio questionRadio);

    QuestionRadio getByEmpId(Long empId);

//    QuestionRadio getByEmpIdAndQuarter(Long empId,String quarter);
}
