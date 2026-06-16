package com.cfl.cfl_project.service;

import com.cfl.cfl_project.dto.CompleteCflTableDTO;
import com.cfl.cfl_project.model.Cfl;
import com.cfl.cfl_project.model.MailHistory;
import com.cfl.cfl_project.model.Register;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface CflService {

//    Cfl createCfl(Long empId,
//                  String cflFirstName,String cflMiddleName, String cflLastName, String cflEmail,
//                  String cflDepartment, String cflDesignation, String reportingManager, String reportingManagerMail,
//                  String hrMail, String cflLocation, String joiningDate, String sscResult,
//                  String hscResult, String underGraduateResult, String postGraduateResult,
//                  String collegeName, String collegeBranch, String technicalSkills,
//                  String nonTechnicalSkills) throws IOException;

    List<Cfl>getAllCfl();

    Cfl uploadCflImage(Long empId,MultipartFile file) throws IOException;

    Cfl getParticularCflByEmpId(Long empId);

    Cfl createCfl(Cfl cfl);

    List<Cfl> createListOfCfl(List<Cfl> list);

    List<Cfl> getAllByYear(String year);


    Boolean sentMailToSenior(Long empId, String email, String ccEmail, String subject, String message, String type);
    Boolean sentMailToSenior(Long empId, String email, String ccEmail, String subject, String message, String type, String meetingDate, String meetingTime);

    List<MailHistory>getByMailHistoryByEmpId(Long empId);

    boolean getByCflEmail(String cflEmail,String type);
    boolean getByCflEmail(String cflEmail,String type, String date, String time);
    boolean getByCflEmail(String cflEmail,String type, String date, String time, String seniorEmail);

    Cfl getByCflInfoByCflEmail(String cflEmail);

    boolean getByCflDeclinedEmail(String cflEmail,String type);


    // get List of Cfl By mentorMail
    List<Cfl> getAllByMentorEmail(String managerEmail);

    // get List of Cfl By hrMail
    List<Cfl> getAllByHrEmail(String hrEmail);

    // list of cfl based on manager email
    List<Cfl> getAllByManagerEmail(String managerEmail);


    Cfl getCflByEmpId(Long empId);

    // fetch cfl data while cfl login via email
    Cfl getCflByEmailDuringLogin(String cflEmail);

    // fetch mentor name data while cfl login via email
    String getMentorNameByMentorEmail(String mentorEmail);


    // fetch manager name data while cfl login via email
    String getManagerNameByManagerEmail(String managerEmail);

    List<Cfl> getAllCflByMentorEmailBasedOnYear(String mentorEmail,String year);

    List<Cfl> getAllCflByManagerEmailBasedOnYear(String managerEmail,String year);



    // Goal Setting Request ----------------------------------------------
    // send automate mail request to manager
    void sendMailToManagerRegardingGoalSetting();

    // send automated goal setting mail request to manager
    void sendMailFromManagerToCFLAndHr(String cflEmail);

    // send automate goal setting mail request to manager
    void sendExtendMailFromManagerToCFLAndHr(String cflEmail);



    // Goal Setting Request Review  ----------------------------------------------

    // send automate mail request to manager
    void sendMailToManagerRegardingGoalSettingReview();

    // send automate accept mail request to manager
    void sendReviewMailFromManagerToCFLAndHr(String cflEmail);

    // send automate extend mail request to manager
    void sendExtendReviewMailFromManagerToCFLAndHr(String cflEmail);



    // probation Request ----------------------------------------------------

    // send automate probation mail request to manager
    void sendMailToManagerRegardingProbation();

    // send automate probation accept mail request to manager
    void sendProbationMailFromManagerToCFLAndHr(String cflEmail);

    // send automate probation extend mail request to manager
    void sendProbationExtendMailFromManagerToCFLAndHr(String cflEmail);

//    // count cfl
//    void sendManagerWiseCflCount(Boolean status);

    // approve mail
    Boolean approveMail(String quarter,Long empId,String mgrEmail);

    // rewards and recognition
    Boolean isCflEmailExists(String cflEmail);
    Boolean isMentorEmailExists(String mentorEmail);
    Boolean isManagerEmailExists(String managerEmail);


    String findCflMentorOrManagerEmail(String mail);


    // generate otp
    void generateOtp(String email,String userRole);

    // verify otp
    Boolean verifyOtp(String email, String otp);

    // enter new password
    Register enterNewPassword(String email, String newPassword, String userRole);

    // update cfl
    Cfl updateCfl(Long id,Cfl cfl);


    List<CompleteCflTableDTO> getAllDetailedReports();

    String countCflScreenTime(String userMail,String timeInMinute);

    List<Cfl>getAllCflByProbationStatusAndYear(Boolean probationStatus,String year);

//    Cfl getCflBasedOnEmpIdOREmail(Long empId,String email);

    Cfl changeRequest(Long empId,String newValueForChange,String changeType);


    // send mail to cfl regarding change request
    void  sendChangeRequestMailToCFL(String cflMail,String changeType);

    void generateMailForCFLRegardingProbationExtension(String cflMail);

    void generateMailForHRRegardingProbationExtension(String hrMail);

    void generateMailForManagerRegardingProbationExtension(String cflMail,String managerMail);


    void sendProbationConfirmationFeedBackToManager(String cflMail,String managerMail);


    // == annual appraisal

    void sendMailToManagerRegardingCFLRatingForAnnualAppraisal(String cflEmail,String cflKeyAccomplishment);

    // check for annual appraisal status
    boolean isAnnualAppraisalFilledByCfl(Long empId);



}
