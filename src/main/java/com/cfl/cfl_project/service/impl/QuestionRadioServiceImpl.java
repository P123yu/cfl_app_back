package com.cfl.cfl_project.service.impl;

import com.cfl.cfl_project.model.Cfl;
import com.cfl.cfl_project.model.QuestionRadio;
import com.cfl.cfl_project.repository.CflRepository;
import com.cfl.cfl_project.repository.QuestionRadioRepository;
import com.cfl.cfl_project.service.QuestionRadioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class QuestionRadioServiceImpl implements QuestionRadioService {

    @Autowired
    private QuestionRadioRepository questionRadioRepository;

    // for setting quarter auto
    public static String getQuarter() {
        LocalDate date=LocalDate.now();
        int month = date.getMonthValue();

        if (month >= 1 && month <= 3) {
            return "Q4";
        } else if (month >= 4 && month <= 6) {
            return "Q1";
        } else if (month >= 7 && month <= 9) {
            return "Q2";
        } else if (month >= 10 && month <= 12) {
            return "Q3";
        } else {
            throw new IllegalArgumentException("Invalid month: " + month);
        }
    }


//    @Override
//    public QuestionRadio create(QuestionRadio questionRadio) {
//        QuestionRadio questionRadioObj = new QuestionRadio();
//        questionRadioObj.setEmpId(questionRadio.getEmpId());
//        questionRadioObj.setQuestion1(questionRadio.getQuestion1());
//        questionRadioObj.setQuestion2(questionRadio.getQuestion2());
//        questionRadioObj.setQuestion3(questionRadio.getQuestion3());
//        questionRadioObj.setQuestion4(questionRadio.getQuestion4());
//        questionRadioObj.setQuestion5(questionRadio.getQuestion5());
//        questionRadioObj.setQuarter(getQuarter());
//
//        Long year= Long.valueOf(LocalDate.now().getYear());
//        if(getQuarter().equalsIgnoreCase("Q4")){
//            year-=1;
//        }
//        questionRadioObj.setYear(year);
//        return questionRadioRepository.save(questionRadioObj);
//    }


    @Autowired
    private CflRepository cflRepository;

    @Override
    public QuestionRadio create(QuestionRadio questionRadio) {

        Cfl cfl=cflRepository.findByEmpId(questionRadio.getEmpId());
        QuestionRadio questionRadioObj = new QuestionRadio();
        questionRadioObj.setEmpId(questionRadio.getEmpId());
        questionRadioObj.setQuestion1(questionRadio.getQuestion1());
        questionRadioObj.setQuestion2(questionRadio.getQuestion2());
        questionRadioObj.setQuestion3(questionRadio.getQuestion3());
        questionRadioObj.setQuestion4(questionRadio.getQuestion4());
        questionRadioObj.setQuestion5(questionRadio.getQuestion5());
//        questionRadioObj.setQuarter(getQuarter());

        Long year= Long.valueOf(LocalDate.now().getYear());

        if(Integer.parseInt(cfl.getYear())<LocalDate.now().getYear()){
            questionRadioObj.setQuarter("annual");
            questionRadioObj.setYear(Long.valueOf(cfl.getYear()));
        }




//        if(Integer.parseInt(cfl.getYear())<LocalDate.now().getYear()){
//            questionRadioObj.setQuarter("annual");
//        }
//        else{
////            questionRadioObj.setQuarter(getQuarter());
//        if(getQuarter().equalsIgnoreCase("Q4")){
//            year-=1;
//        }
//        }

//        questionRadioObj.setYear(year);

        return questionRadioRepository.save(questionRadioObj);
    }

//    @Override
//    public QuestionRadio getByEmpIdAndQuarter(Long empId,String quarter) {
//        return questionRadioRepository.findByEmpIdAndQuarter(empId,quarter);
//    }

    @Override
    public QuestionRadio getByEmpId(Long empId) {
        return questionRadioRepository.findByEmpId(empId);
    }
}
