package com.cfl.cfl_project.service.impl;

import com.cfl.cfl_project.email.CflToMentorMail;
import com.cfl.cfl_project.model.Cfl;
import com.cfl.cfl_project.model.ManagerRatingQuestionAndAnswer;
import com.cfl.cfl_project.repository.CflRepository;
import com.cfl.cfl_project.service.ManagerRatingQuestionAndAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.cfl.cfl_project.repository.ManagerRatingQuestionAndAnswerRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class ManagerRatingQuestionAndAnswerServiceImpl implements ManagerRatingQuestionAndAnswerService {

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

    @Autowired
    private ManagerRatingQuestionAndAnswerRepository managerRatingQuestionAndAnswerRepository;

//    @Override
//    public ManagerRatingQuestionAndAnswer saveQuestionAndAnswer(ManagerRatingQuestionAndAnswer managerRatingQuestionAndAnswer) {
//        managerRatingQuestionAndAnswer.setQuarter(getQuarter());
//        return managerRatingQuestionAndAnswerRepository.save(managerRatingQuestionAndAnswer);
//    }

    @Autowired
    private CflRepository cflRepository;

    @Autowired
    private CflToMentorMail cflToMentorMail;


    @Override
    @Async
    @Transactional
    public ManagerRatingQuestionAndAnswer saveQuestionAndAnswer(ManagerRatingQuestionAndAnswer managerRatingQuestionAndAnswer) {
        if (managerRatingQuestionAndAnswer == null) {
            throw new IllegalArgumentException("managerRatingQuestionAndAnswer cannot be null");
        }

        managerRatingQuestionAndAnswer.setAnnual("annual");

        Cfl cfl = cflRepository.findByEmpId(managerRatingQuestionAndAnswer.getEmpId());
        if (cfl == null) {
            throw new IllegalStateException("No CFL record found for employee ID: " + managerRatingQuestionAndAnswer.getEmpId());
        }

        // Ensure question answers are not null to avoid sending null values in the email
        String q1 = managerRatingQuestionAndAnswer.getQuestionAnswerText1() != null ? managerRatingQuestionAndAnswer.getQuestionAnswerText1() : "";
        String q2 = managerRatingQuestionAndAnswer.getQuestionAnswerText2() != null ? managerRatingQuestionAndAnswer.getQuestionAnswerText2() : "";
        String q3 = managerRatingQuestionAndAnswer.getQuestionAnswerText3() != null ? managerRatingQuestionAndAnswer.getQuestionAnswerText3() : "";
        String q4 = managerRatingQuestionAndAnswer.getQuestionAnswerText4() != null ? managerRatingQuestionAndAnswer.getQuestionAnswerText4() : "";
        String q5 = managerRatingQuestionAndAnswer.getQuestionAnswerText5() != null ? managerRatingQuestionAndAnswer.getQuestionAnswerText5() : "";

        cflToMentorMail.sendManagerQuestionAnswerMailToHR(
                cfl.getHrMail(), cfl.getCflFirstName(),
                q1, q2, q3, q4, q5, cfl.getReportingManager()
        );

        return managerRatingQuestionAndAnswerRepository.save(managerRatingQuestionAndAnswer);
    }


    @Override
    public ManagerRatingQuestionAndAnswer getQuestionAndAnswer(Long empId) {
        return managerRatingQuestionAndAnswerRepository.findByEmpId(empId);
    }
}
