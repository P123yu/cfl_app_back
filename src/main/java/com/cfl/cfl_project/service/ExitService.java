package com.cfl.cfl_project.service;

import com.cfl.cfl_project.model.Exit;
import org.springframework.stereotype.Service;

@Service
public interface ExitService {

    Exit saveExit(Exit exit);

    Exit findExitByEmpId(Long empId);

    void deleteExitByEmpId(Long empId);

    void sendCFLExitMailToManagerAndHR(Long empId);

    void sendCFLExitWithdrawalMailToManagerAndHR(Long empId);

    void sendExitRequestAcceptanceMailToManagerAndHRAndCFL(Long empId);


    Exit addDeclineReason(Long empId,String declineReason);

    void sendExitRequestDeclinedMailToManagerAndHRAndCFL(Long empId,String declineReason);



    Exit addExtendReason(Long empId,String declineReason,String formattedDate);

    void sendExitRequestExtendedMailToManagerAndHRAndCFL(Long empId,String declineReason,String formattedDate);





}
