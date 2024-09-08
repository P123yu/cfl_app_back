package com.cfl.cfl_project.service.impl;

import com.cfl.cfl_project.email.CflToMentorMail;
import com.cfl.cfl_project.model.Cfl;
import com.cfl.cfl_project.model.Exit;
import com.cfl.cfl_project.repository.CflRepository;
import com.cfl.cfl_project.repository.ExitRepository;
import com.cfl.cfl_project.service.ExitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class ExitServiceImpl implements ExitService {

    @Autowired
    private ExitRepository exitRepository;

    @Autowired
    private CflRepository cflRepository;

//    @Override
//    public Exit saveExit(Exit exit) {
//        return exitRepository.save(exit);
//    }


    @Override
    @Transactional
    public Exit saveExit(Exit exit) {
        Long empId=exit.getEmpId();
        boolean result=exitRepository.existsByEmpId(empId);
        if(result){
            exitRepository.deleteByEmpId(empId);
            return exitRepository.save(exit);
        }
        return exitRepository.save(exit);
    }

    @Override
    public Exit findExitByEmpId(Long empId) {
        return exitRepository.findByEmpId(empId).orElse(null);
    }

    @Override
    @Transactional
    public void deleteExitByEmpId(Long empId) {
        exitRepository.deleteByEmpId(empId);
    }

    @Autowired
    private CflToMentorMail cflToMentorMail;


    @Override
    @Async
    public void sendCFLExitMailToManagerAndHR(Long empId) {
        Cfl cfl=cflRepository.findByEmpId(empId);
        cflToMentorMail.sendExitRequestToManager(cfl.getReportingManager(),cfl.getCflFirstName(),cfl.getReportingManagerMail());
        cflToMentorMail.sendExitRequestToHR(cfl.getHrName(),cfl.getCflFirstName(),cfl.getHrMail());
    }


    @Override
    @Async
    public void sendCFLExitWithdrawalMailToManagerAndHR(Long empId) {
        Cfl cfl=cflRepository.findByEmpId(empId);
        cflToMentorMail.sendExitWithdrawalRequestToManager(cfl.getReportingManager(),cfl.getCflFirstName(),cfl.getReportingManagerMail());
        cflToMentorMail.sendExitWithdrawalRequestToHR(cfl.getHrName(),cfl.getCflFirstName(),cfl.getHrMail());
    }

    // cfl confirm exit from their side


    @Override
    @Async
    @Transactional
    public void sendExitRequestAcceptanceMailToManagerAndHRAndCFL(Long empId) {
        Cfl cfl=cflRepository.findByEmpId(empId);
        Exit exit=exitRepository.findByEmpId(empId).orElse(null);
        if(exit !=null){
            exit.setAcceptStatus("true");
            exitRepository.save(exit);
        }
        cflToMentorMail.sendExitRequestAcceptedMailToManager(cfl.getReportingManager(),cfl.getCflFirstName(),cfl.getReportingManagerMail());
        cflToMentorMail.sendExitRequestAcceptedMailToHR(cfl.getHrName(),cfl.getCflFirstName(),cfl.getHrMail(),cfl.getReportingManager());
        cflToMentorMail.sendExitRequestAcceptedMailToCFL(cfl.getCflEmail(),cfl.getCflFirstName(),cfl.getReportingManager());
    }


    @Override
    public Exit addDeclineReason(Long empId, String declineReason) {
        Exit exit=exitRepository.findByEmpId(empId).orElse(null);
        if(exit !=null){
            exit.setDeclineReason(declineReason);
            return exitRepository.save(exit);
        }
        return null;
    }


    @Override
    public void sendExitRequestDeclinedMailToManagerAndHRAndCFL(Long empId,String declineReason) {
        Cfl cfl=cflRepository.findByEmpId(empId);
        cflToMentorMail.sendExitRequestDeclinedMailToManager(cfl.getReportingManager(),cfl.getCflFirstName(),cfl.getReportingManagerMail(),declineReason);
        cflToMentorMail.sendExitRequestDeclinedMailToHR(cfl.getHrName(),cfl.getCflFirstName(),cfl.getHrMail(),cfl.getReportingManager(),declineReason);
        cflToMentorMail.sendExitRequestDeclinedMailToCFL(cfl.getCflEmail(),cfl.getCflFirstName(),cfl.getReportingManager(),declineReason);
    }


    @Override
    public Exit addExtendReason(Long empId, String declineReason,String formattedDate) {
        Exit exit=exitRepository.findByEmpId(empId).orElse(null);
        if(exit !=null){
            exit.setExtendedReason(declineReason);
            exit.setExtendedDate(formattedDate);
            return exitRepository.save(exit);
        }
        return null;
    }


    @Override
    public void sendExitRequestExtendedMailToManagerAndHRAndCFL(Long empId,String extendReason,String formattedDate) {
        Cfl cfl=cflRepository.findByEmpId(empId);
        cflToMentorMail.sendExitRequestExtendedMailToManager(cfl.getReportingManager(),cfl.getCflFirstName(),cfl.getReportingManagerMail(),extendReason,formattedDate);
        cflToMentorMail.sendExitRequestExtendedMailToHR(cfl.getHrName(),cfl.getCflFirstName(),cfl.getHrMail(),cfl.getReportingManager(),extendReason,formattedDate);
        cflToMentorMail.sendExitRequestExtendedMailToCFL(cfl.getCflEmail(),cfl.getCflFirstName(),cfl.getReportingManager(),extendReason,formattedDate);
    }




}

