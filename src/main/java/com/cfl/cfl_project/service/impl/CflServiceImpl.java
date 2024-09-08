
package com.cfl.cfl_project.service.impl;

import com.cfl.cfl_project.dto.*;
import com.cfl.cfl_project.model.*;
import com.cfl.cfl_project.repository.*;
import com.cfl.cfl_project.service.CflService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.cfl.cfl_project.email.CflToMentorMail;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class CflServiceImpl implements CflService {

    @Autowired
    private CflRepository cflRepository;



    @Autowired
    private AnnualAppraisalInfoRepository annualAppraisalInfoRepository;



    @Override
    public List<Cfl> getAllCfl() {
        return cflRepository.findAll();
    }

    @Override
    public Cfl uploadCflImage(Long empId, MultipartFile file) throws IOException {
        Cfl cfl=cflRepository.findByEmpId(empId);
        cfl.setFileName(file.getOriginalFilename());
        cfl.setFileData(file.getBytes());
        Cfl cflFile=cflRepository.save(cfl);
        return cflFile;
    }



    @Override
    @Transactional
    public Cfl getParticularCflByEmpId(Long empId) {
        Cfl cfl=cflRepository.findByEmpId(empId);
        Manager manager=managerRepository.findByManagerEmail(cfl.getReportingManagerMail());
        Mentor mentor=mentorRepository.findByMentorEmail(cfl.getMentorEmail());
        if(manager !=null){
            cfl.setManagerId(manager.getManagerId());
        }
        if(mentor !=null){
            cfl.setMentorId(mentor.getMentorId());
        }
        return cfl;
    }


    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private HRRepository hrRepository;
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



    @Override
    @Transactional
    public Cfl createCfl(Cfl cfl) {
        Boolean result=cflRepository.existsByCflEmail(cfl.getCflEmail());
        Cfl newCfl=null;

        if(!result) {

            cfl.setGoalSettingStatusHrToMgr(true);
            cfl.setGoalSettingReviewStatusHrToMgr(false);
            cfl.setCflMgrQuarterStatus(true);
            cfl.setProbationStatus(true);


            String cflJoiningDate=cfl.getJoiningDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate date=LocalDate.parse(cflJoiningDate, formatter);

            cfl.setProbationDate(date.plusMonths(6));

            Manager manager = new Manager();
            manager.setManagerId(cfl.getManagerId());
            manager.setManagerName(cfl.getReportingManager());
            manager.setManagerEmail(cfl.getReportingManagerMail());
            manager.setManagerDepartment(cfl.getCflDepartment());
            manager.setManagerLocation(cfl.getManagerLocation());
            manager.setManagerDesignation(cfl.getManagerDesignation());
            if(!managerRepository.existsByManagerEmail(cfl.getReportingManagerMail())){
                managerRepository.save(manager);
            }

            Mentor mentor = new Mentor();
            mentor.setMentorId(cfl.getMentorId());
            mentor.setMentorName(cfl.getMentorName());
            mentor.setMentorEmail(cfl.getMentorEmail());
            mentor.setMentorDepartment(cfl.getMentorDepartment());
            mentor.setMentorDesignation(cfl.getMentorDesignation());
            mentor.setMentorLocation(cfl.getMentorLocation());
            if(!mentorRepository.existsByMentorEmail(cfl.getMentorEmail())){
                mentorRepository.save(mentor);
            }


            HR hr = new HR();
            hr.setHrId(cfl.getHrId());
            hr.setHrName(cfl.getHrName());
            hr.setHrEmail(cfl.getHrMail());
            hr.setHrLocation(cfl.getHrLocation());
            if(!hrRepository.existsByHrEmail(cfl.getHrMail())){
                hrRepository.save(hr);
            }
            newCfl=cflRepository.save(cfl);
        }

        return newCfl;
    }

    @Transactional
    @Override
    public List<Cfl> createListOfCfl(List<Cfl> list) {

        List<Mentor>mentorList=new ArrayList<>();
        List<HR>hrList=new ArrayList<>();
        List<Manager>managerList=new ArrayList<>();
        List<Cfl>cflList=new ArrayList<>();
        for(Cfl cfl : list){
            cfl.setGoalSettingStatusHrToMgr(true);
            cfl.setGoalSettingReviewStatusHrToMgr(false);
            cfl.setCflMgrQuarterStatus(true);
            cfl.setProbationStatus(true);


            String cflJoiningDate=cfl.getJoiningDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate date=LocalDate.parse(cflJoiningDate, formatter);

            cfl.setProbationDate(date.plusMonths(6));


            Mentor mentorObj=new Mentor();
            mentorObj.setMentorId(cfl.getMentorId());
            mentorObj.setMentorName(cfl.getMentorName());
            mentorObj.setMentorEmail(cfl.getMentorEmail());
            mentorObj.setMentorDepartment(cfl.getMentorDepartment());
            mentorObj.setMentorDesignation(cfl.getMentorDesignation());
            mentorObj.setMentorLocation(cfl.getMentorLocation());

            // Add mentor if email not already present
            if (mentorList.stream().noneMatch(m -> m.getMentorEmail().equalsIgnoreCase(mentorObj.getMentorEmail()))) {
                if(!mentorRepository.existsByMentorEmail(mentorObj.getMentorEmail())){
                    mentorList.add(mentorObj);
                }
            }


            HR hrObj=new HR();
            hrObj.setHrId(cfl.getHrId());
            hrObj.setHrName(cfl.getHrName());
            hrObj.setHrEmail(cfl.getHrMail());
            hrObj.setHrLocation(cfl.getHrLocation());
            // Add hr if email not already present
            if (hrList.stream().noneMatch(m -> m.getHrEmail().equalsIgnoreCase(hrObj.getHrEmail()))) {
                if(!hrRepository.existsByHrEmail(hrObj.getHrEmail())){
                    hrList.add(hrObj);
                }
            }

            Manager managerObj=new Manager();
            managerObj.setManagerId(cfl.getManagerId());
            managerObj.setManagerName(cfl.getReportingManager());
            managerObj.setManagerDepartment(cfl.getCflDepartment());
            managerObj.setManagerEmail(cfl.getReportingManagerMail());
            managerObj.setManagerDesignation(cfl.getManagerDesignation());
            managerObj.setManagerLocation(cfl.getManagerLocation());

            // Add mentor if email not already present
            if (managerList.stream().noneMatch(m -> m.getManagerEmail().equalsIgnoreCase(managerObj.getManagerEmail()))) {
                if(!managerRepository.existsByManagerEmail(managerObj.getManagerEmail())){
                    managerList.add(managerObj);
                }
            }
            cflList.add(cfl);
        }


        mentorRepository.saveAll(mentorList);
        hrRepository.saveAll(hrList);
        managerRepository.saveAll(managerList);

        cflList=cflRepository.saveAll(cflList);
        return cflList;
    }


    @Override
    public List<Cfl> getAllByYear(String year) {
        return cflRepository.findByYear(year);
    }




    @Autowired
    private CflToMentorMail cflToMentorMail;

    @Autowired
    private MailHistoryRepository mailHistoryRepository;


    @Async
    @Override
    public Boolean sentMailToSenior(Long empId, String email, String ccEmail, String subject, String message, String type) {
        return sentMailToSenior(empId, email, ccEmail, subject, message, type, null, null);
    }

    @Async
    @Override
    public Boolean sentMailToSenior(Long empId, String email, String ccEmail, String subject, String message, String type, String meetingDate, String meetingTime) {
        // Fetching CFL details
        Cfl cfl = cflRepository.findByEmpId(empId);
        if (cfl == null) {
            // Log error or throw exception, depending on business logic
            return false;
        }

        // Extracting necessary details from CFL
        String cflDesignation = cfl.getCflDesignation();
        String cflDepartment = cfl.getCflDepartment();
        String cflLocation = cfl.getCflLocation();
        String reportingManager = cfl.getReportingManager();
        String managerEmail = cfl.getReportingManagerMail();
        String hrEmail = cfl.getHrMail();
        String year = cfl.getYear();
        String cflName = cfl.getCflFirstName() + "_" + cfl.getCflLastName();
        String cflEmail = cfl.getCflEmail();
        String hrName=cfl.getHrName();
        String mentorName=cfl.getMentorName();

        // Message body content
        String body = message;

        // Saving mail history
        MailHistory mailHistory = new MailHistory();
        mailHistory.setEmpId(empId);
        mailHistory.setMailDate(LocalDate.now());
        mailHistory.setMailTime(LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
        mailHistoryRepository.save(mailHistory);

        // Logging the mail type
        System.out.println("Mail type: " + type);
        return   cflToMentorMail.sendEmail(cflName, cflEmail, cflDesignation, cflDepartment, cflLocation, reportingManager,hrName,mentorName, email, subject, body, year, ccEmail, type, meetingDate, meetingTime);

    }

    @Override
    public List<MailHistory> getByMailHistoryByEmpId(Long empId) {
        return mailHistoryRepository.findByEmpId(empId);
    }


    @Override
    public List<Cfl> getAllByMentorEmail(String mentorEmail) {
        return cflRepository.findByMentorEmail(mentorEmail);
    }

    @Override
    public List<Cfl> getAllByHrEmail(String mentorEmail) {
        return cflRepository.findByHrMail(mentorEmail);
    }


    @Override
    public List<Cfl> getAllByManagerEmail(String managerEmail) {
        return cflRepository.findByReportingManagerMail(managerEmail);
    }


    @Override
    public Cfl getCflByEmpId(Long empId) {
        return cflRepository.findByEmpId(empId);
    }

    @Override
    public Cfl getCflByEmailDuringLogin(String cflEmail) {
        return cflRepository.findByCflEmail(cflEmail);
    }

    @Override
    public String getMentorNameByMentorEmail(String mentorEmail) {
        return cflRepository.findByMentorEmail(mentorEmail).get(0).getMentorName();
    }

    @Override
    public String getManagerNameByManagerEmail(String managerEmail) {
        return cflRepository.findByReportingManagerMail(managerEmail).get(0).getReportingManager();
    }

    @Override
    public List<Cfl> getAllCflByMentorEmailBasedOnYear(String mentorEmail,String year) {
        return cflRepository.findByMentorEmailAndYear(mentorEmail,year);
    }

    @Override
    public List<Cfl> getAllCflByManagerEmailBasedOnYear(String managerEmail, String year) {
        return  cflRepository.findByReportingManagerMailAndYear(managerEmail,year);
    }

    // mentor-mentee requests =====================================================

    private String generateGoogleMeetLink() {
        String chars = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        // 3 letters
        for (int i = 0; i < 3; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        sb.append("-");
        // 4 letters
        for (int i = 0; i < 4; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        sb.append("-");
        // 3 letters
        for (int i = 0; i < 3; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return "https://meet.google.com/" + sb.toString();
    }

    @Override
    public boolean getByCflEmail(String cflEmail,String type) {
        return getByCflEmail(cflEmail, type, null, null);
    }

    @Override
    public boolean getByCflEmail(String cflEmail,String type, String date, String time) {

        // Retrieve the Cfl entity based on the email
        Cfl cfl = cflRepository.findByCflEmail(cflEmail);

        if (cfl != null) {
            String meetLink = generateGoogleMeetLink();
            String cflName = cfl.getCflFirstName();

            if(type.equalsIgnoreCase("HR")){
                String subject="HR session acceptance";
                String message="We are happy to share that your hr "+cfl.getHrName()+" has accepted your meeting request.<br/><br/>" +
                        "<strong>Scheduled Details:</strong><br/>" +
                        "Date: " + (date != null ? date : "N/A") + "<br/>" +
                        "Time: " + (time != null ? time : "N/A") + "<br/>" +
                        "Google Meet Link: <a href=\"" + meetLink + "\">" + meetLink + "</a>";
                String hrName=cfl.getHrName();
                String hrEmail=cfl.getHrMail();
                cflToMentorMail.sendMailFromHRToCFL(cflEmail,hrName,cflName,subject,message);
                cflToMentorMail.sendMailFromHRToCFL(hrEmail,hrName,hrName,subject,message.replace("your hr "+cfl.getHrName(), "you"));
                return true;
            }


            else if(type.equalsIgnoreCase("Manager")){
                String subject="Manager session acceptance";
                String message="We are happy to share that your manager "+cfl.getReportingManager()+" has accepted your meeting request.<br/><br/>" +
                        "<strong>Scheduled Details:</strong><br/>" +
                        "Date: " + (date != null ? date : "N/A") + "<br/>" +
                        "Time: " + (time != null ? time : "N/A") + "<br/>" +
                        "Google Meet Link: <a href=\"" + meetLink + "\">" + meetLink + "</a>";
                String managerName=cfl.getReportingManager();
                String managerEmail=cfl.getReportingManagerMail();
                cflToMentorMail.sendMailFromManagerToCFL(cflEmail,managerName,cflName,subject,message);
                cflToMentorMail.sendMailFromManagerToCFL(managerEmail,managerName,managerName,subject,message.replace("your manager "+cfl.getReportingManager(), "you"));
                return true;
            }



            // Retrieve and process the current acceptance value
            String currentAcceptance = cfl.getEmailAcceptance();
            if (currentAcceptance == null || currentAcceptance.isEmpty()) {
                currentAcceptance = "a";
            }
           else {
                currentAcceptance += ",a";
            }

            // Set the updated email acceptance value
            cfl.setEmailAcceptance(currentAcceptance);


            String subject="Mentoring session acceptance";
            String message="We are happy to share that your mentor "+cfl.getMentorName()+" has accepted your mentoring request.<br/><br/>" +
                    "<strong>Scheduled Details:</strong><br/>" +
                    "Date: " + (date != null ? date : "N/A") + "<br/>" +
                    "Time: " + (time != null ? time : "N/A") + "<br/>" +
                    "Google Meet Link: <a href=\"" + meetLink + "\">" + meetLink + "</a>";
            String status="accept";

            String mentorName=cfl.getMentorName();
            String mentorEmail=cfl.getMentorEmail();
            cflToMentorMail.sendMailFromMentorToMentee(cflEmail,mentorName,cflName,subject,message,status);
            cflToMentorMail.sendMailFromMentorToMentee(mentorEmail,mentorName,mentorName,subject,message.replace("your mentor "+cfl.getMentorName(), "you"),status);
            cflRepository.save(cfl);
            return true;
        }
        return false;
    }

    @Override
    public Cfl getByCflInfoByCflEmail(String cflEmail) {
        return cflRepository.findByCflEmail(cflEmail);
    }


    // ---- mentor extend the mentor-mentee request ------------------------

    @Transactional
    @Async
    @Override
    public boolean getByCflDeclinedEmail(String cflEmail,String type) {
        Cfl cfl = cflRepository.findByCflEmail(cflEmail);
        if (cfl != null) {

            if(type.equalsIgnoreCase("HR")){
                LocalDate date=LocalDate.now().plusDays(7);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String formattedDate = date.format(formatter);
                String subject="HR session extended";
                String message="Kindly note that considering business deliverables, your hr "+cfl.getHrName()+" has postponed your mentoring session on. "+formattedDate;
                String cflName= cfl.getCflFirstName();
                String hrName=cfl.getHrName();
                cflToMentorMail.sendMailFromHRToCFLExtend(cflEmail,hrName,cflName,subject,message);
                cfl.setHrMeetingExtendedDate(date);
                cflRepository.save(cfl);
                return true;
            }


            else if(type.equalsIgnoreCase("Manager")){
                LocalDate date=LocalDate.now().plusDays(7);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String formattedDate = date.format(formatter);
                String subject="Manager session extended";
                String message="Kindly note that considering business deliverables, your manager "+cfl.getReportingManager()+" has postponed your mentoring session on. "+formattedDate;
                String cflName= cfl.getCflFirstName();
                String managerName=cfl.getReportingManager();
                cflToMentorMail.sendMailFromManagerToCFLExtend(cflEmail,managerName,cflName,subject,message);
                cfl.setManagerMeetingExtendedDate(date);
                cflRepository.save(cfl);
                return true;
            }


            String currentDeclined = cfl.getEmailDeclined();
            if (currentDeclined == null || currentDeclined.isEmpty()) {
                currentDeclined = "p";
            }
          else {
                currentDeclined += ",p";
            }
            cfl.setEmailDeclined(currentDeclined);

            // one week date extension
            LocalDate date=LocalDate.now();
            LocalDate extendedDate=date.plusDays(7);

            // save to cfl table the mentoring extension date
            cfl.setExtendedMentoringDate(extendedDate);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String formattedDate = extendedDate.format(formatter);

            String subject="Mentoring session extended";
            String message="Kindly note that considering business deliverables, your mentor has postponed your mentoring session on. "+formattedDate;
            String status="postpone";

            String cflName= cfl.getCflFirstName();
            String mentorName=cfl.getMentorName();

            cflToMentorMail.sendMailFromMentorToMentee(cflEmail,mentorName,cflName,subject,message,status);
            cflRepository.save(cfl);
            return true;
        }
        return false;
    }




    @Transactional
//    @Scheduled(cron = "0 * * * * ?")
    @Scheduled(cron = "0 0 10 * * ?")
    public void sendMailBackToMentorAfterOneWeek() {
        LocalDate date = LocalDate.now();
        List<Cfl> newCflList = new ArrayList<>();
        List<Cfl> cflList = cflRepository.findAll();
        for (Cfl cfl : cflList) {
            if (cfl.getExtendedMentoringDate() != null) {
                if (date.isEqual(cfl.getExtendedMentoringDate())) {
                    String message = "Dear sir " + cfl.getMentorName() + ",\n" +
                            "I am reaching out to request a mentoring session with you at your earliest convenience. " +
                            "As I continue to grow in my career and deepen my understanding of financial planning, " +
                            "I greatly value your expertise and the invaluable insights you offer. " +
                            "Your guidance would be tremendously helpful as I navigate some key areas I have been focusing on recently.";
                  //  sentMailToSenior(cfl.getEmpId(), cfl.getCflEmail(), "cmsfutureleaders@gmail.com", "Regarding Mentoring session", message, "Mentor");
                    int year=  LocalDate.now().getYear();
                    cflToMentorMail.sendEmail(cfl.getCflFirstName(), cfl.getCflEmail(), cfl.getCflDesignation(), cfl.getCflDepartment(), cfl.getCflLocation(), cfl.getReportingManager(), cfl.getHrName(), cfl.getMentorName(), cfl.getMentorEmail(), "Regarding mentoring session", message, String.valueOf(year), "cmsfutureleaders@gmail.com","Mentor");
                    cfl.setExtendedMentoringDate(null);
                    newCflList.add(cfl);
                }
            }
            if (!newCflList.isEmpty()) {
                cflRepository.saveAll(newCflList);
            }
        }
    }




    // send normal mail back to hr after one week

    @Transactional
//    @Scheduled(cron = "0 * * * * ?")
    @Scheduled(cron = "0 0 10 * * ?")
    public void sendMailBackToHRAfterOneWeek() {
        LocalDate date = LocalDate.now();
        List<Cfl> newCflList = new ArrayList<>();
        List<Cfl> cflList = cflRepository.findAll();
        for (Cfl cfl : cflList) {
            if (cfl.getHrMeetingExtendedDate() != null) {
                if (date.isEqual(cfl.getHrMeetingExtendedDate())) {
                    String message = "Dear Sir/Mam " + cfl.getHrName() + ",\n" +
                            "I hope you are doing well. I am reaching out to request a meeting with you at your earliest convenience to discuss some points. " +
                            "Your guidance would be greatly appreciated as I address these important topics.";

                   // sentMailToSenior(cfl.getEmpId(), cfl.getHrMail(), "cmsfutureleaders@gmail.com", "Regarding Meeting", message, " HR");
                    int year=  LocalDate.now().getYear();
                    cflToMentorMail.sendEmail(cfl.getCflFirstName(), cfl.getCflEmail(), cfl.getCflDesignation(), cfl.getCflDepartment(), cfl.getCflLocation(), cfl.getReportingManager(), cfl.getHrName(), cfl.getMentorName(), cfl.getHrMail(), "Regarding Meeting with HR", message, String.valueOf(year), "cmsfutureleaders@gmail.com","HR");
                    cfl.setHrMeetingExtendedDate(null);
                    newCflList.add(cfl);

                }
            }
            if (!newCflList.isEmpty()) {
                cflRepository.saveAll(newCflList);
            }
        }
    }





    // send normal meeting mail back to manager after one week

    @Transactional
//    @Scheduled(cron = "0 * * * * ?")
    @Scheduled(cron = "0 0 10 * * ?")
    public void sendMailBackToManagerWithCFLAfterOneWeek() {
        LocalDate date = LocalDate.now();
        List<Cfl> newCflList = new ArrayList<>();
        List<Cfl> cflList = cflRepository.findAll();
        for (Cfl cfl : cflList) {
            if (cfl.getManagerMeetingExtendedDate() != null) {
                if (date.isEqual(cfl.getManagerMeetingExtendedDate())) {
                    String message = "Dear " + cfl.getReportingManager() + ",\n" +
                            "I hope you are doing well. I am reaching out to request a meeting with you at your earliest convenience to discuss some points. " +
                            "Your guidance would be greatly appreciated as I address these important topics.";

                   // sentMailToSenior(cfl.getEmpId(), cfl.getHrMail(), "cmsfutureleaders@gmail.com", "Regarding Meeting", message, " Manager");
                    int year=  LocalDate.now().getYear();
                    cflToMentorMail.sendEmail(cfl.getCflFirstName(), cfl.getCflEmail(), cfl.getCflDesignation(), cfl.getCflDepartment(), cfl.getCflLocation(), cfl.getReportingManager(), cfl.getHrName(), cfl.getMentorName(), cfl.getReportingManagerMail(), "Regarding Meeting with Manager", message, String.valueOf(year), "cmsfutureleaders@gmail.com","Manager");
                    cfl.setManagerMeetingExtendedDate(null);
                    newCflList.add(cfl);
                }
            }
            if (!newCflList.isEmpty()) {
                cflRepository.saveAll(newCflList);
            }
        }
    }





    // ============================GOAL SETTING========================================

    // automatically generated email for goal setting to manager

    @Autowired
    private GoalSettingTrackerRepository goalSettingTrackerRepository;



@Scheduled(cron = "0 0 10 22 12 ?", zone = "Asia/Kolkata")
@Scheduled(cron = "0 0 10 22 3 ?", zone = "Asia/Kolkata")
@Scheduled(cron = "0 0 10 22 6 ?", zone = "Asia/Kolkata")
@Scheduled(cron = "0 0 10 22 9 ?", zone = "Asia/Kolkata")

//    @Scheduled(cron = "0 * * * * ?")
    @Transactional
    public void sendMailToManagerRegardingGoalSettingEveryQuarter() {
        List<Cfl> cflGoalSettingStatus = cflRepository.findByGoalSettingStatusHrToMgr(false);
        List<Cfl> cflGoalSettings = new ArrayList<>();
        for (Cfl cflStatus : cflGoalSettingStatus) {
            cflStatus.setGoalSettingStatusHrToMgr(true);
            cflStatus.setCflMgrQuarterStatus(true);
            cflGoalSettings.add(cflStatus);
        }
        cflRepository.saveAll(cflGoalSettings);
    }


    @Override
    @Scheduled(cron = "0 0 10 * * ?")
//    @Scheduled(cron = "0 * * * * ?")

    public void sendMailToManagerRegardingGoalSetting() {
        List<Cfl> allUpdatedStatus = new ArrayList<>();
        List<GoalSettingTracker> goals = new ArrayList<>();

        List<Cfl> cflGoalSettingStatus = cflRepository.findByGoalSettingStatusHrToMgr(true) ;
        for (Cfl cflStatus : cflGoalSettingStatus) {
            String mgrEmail = cflStatus.getReportingManagerMail();
            String cflFirstName = cflStatus.getCflFirstName();
            String cflEmail = cflStatus.getCflEmail();
            String hrEmail=cflStatus.getHrMail();
            String mgrName= cflStatus.getReportingManager();
            if (mgrEmail != null && !mgrEmail.isEmpty()) {
                // Send individual email for each manager and their respective CFL
                String subject = "Regarding Goal Setting With CFL";
                cflToMentorMail.sendGoalSettingToManager(mgrEmail,mgrName, cflFirstName, subject,cflEmail,hrEmail,getQuarter());
            }
            // Update goal setting status to false after sending the email
            Cfl statusUpdate = cflRepository.findByCflEmail(cflEmail);
            statusUpdate.setGoalSettingStatusHrToMgr(false);

            // goal setting tracker

            GoalSettingTracker goalSettingTracker=new GoalSettingTracker();
            goalSettingTracker.setCflId(cflStatus.getEmpId());
            goalSettingTracker.setGoalInitiatedFromHrToManager(true);
            goalSettingTracker.setQuarter(getQuarter());
            goalSettingTracker.setCflName(cflStatus.getCflFirstName());
            int year=LocalDate.now().getYear();
            if(getQuarter().equalsIgnoreCase("Q4")){
                year-=1;
            }
            goalSettingTracker.setYear((long) year);
            goals.add(goalSettingTracker);
            allUpdatedStatus.add(statusUpdate);
        }
        // Save all updated CFLs with status changes
        cflRepository.saveAll(allUpdatedStatus);
        goalSettingTrackerRepository.saveAll(goals);
    }




    // manager accepted goal setting response to hr and cfl  -----------

    @Override
    public void sendMailFromManagerToCFLAndHr(String cflEmail) {
        Cfl cfl=cflRepository.findByCflEmail(cflEmail);
        String managerEmail = cfl.getReportingManagerMail();
        String managerName=managerRepository.findByManagerEmail(managerEmail).getManagerName();
        String cflName = cfl.getCflFirstName();
        String hrMail = cfl.getHrMail();
        LocalDate date = LocalDate.now();
        //  set false if manager accept the goal setting request
        cfl.setCflMgrQuarterStatus(false);
        cflRepository.save(cfl);

        // goal setting tracker
        GoalSettingTracker goalSettingTracker=goalSettingTrackerRepository.findByCflIdAndQuarter(cfl.getEmpId(),getQuarter());
        goalSettingTracker.setResponseSendByManagerToCfl(true);
        goalSettingTracker.setResponseSendByManagerToHr(true);

        int year=LocalDate.now().getYear();
        if(getQuarter().equalsIgnoreCase("Q4")){
            year-=1;
        }
        goalSettingTracker.setYear((long) year);
        goalSettingTrackerRepository.save(goalSettingTracker);


        cflToMentorMail.sendGoalSettingToCflAndHr(managerName,managerEmail,cflName,cflEmail,hrMail,date,getQuarter());
    }


    //  manager extended goal setting response to hr and cfl -----------------------
    @Transactional
    public void sendExtendMailFromManagerToCFLAndHr(String cflEmail) {
        Cfl cfl = cflRepository.findByCflEmail(cflEmail);
        if (cfl == null) {
            return;
        }
        String managerEmail = cfl.getReportingManagerMail();
        String managerName = managerRepository.findByManagerEmail(managerEmail).getManagerName();
        String cflName = cfl.getCflFirstName();
        String hrMail = cfl.getHrMail();
        LocalDate date = LocalDate.now();

        cfl.setExtendedDate(date.plusDays(7));
        cflRepository.save(cfl);

        sendGoalSettingExtendMailBackToMgr(managerEmail, cflEmail);
        cflToMentorMail.sendGoalSettingExtendToCflAndHr(managerName, cflName, cflEmail, hrMail, date, getQuarter());
    }

    public void sendGoalSettingExtendMailBackToMgr(String managerEmail, String cflEmail) {
        System.out.println(managerEmail+" "+cflEmail);
        Cfl cfl = cflRepository.findByCflEmail(cflEmail);
        if (cfl == null) {
            return;
        }

        String managerName = managerRepository.findByManagerEmail(managerEmail).getManagerName();
        String cflName = cfl.getCflFirstName();
        LocalDate date = LocalDate.now();

        // Send the email
        cflToMentorMail.sendExtendMailBackToMgr(managerName,cflName, managerEmail, date);
    }

//    @Scheduled(cron = "0 0 10 * * ?")

    @Transactional
//    @Scheduled(cron = "0 * * * * ?")
    @Scheduled(cron = "0 0 10 * * ?")
    public void sendMailBackToManagerAfterOneWeek(){
    LocalDate date=LocalDate.now();
        List<Cfl> newCflList = new ArrayList<>();
         List<Cfl> cflList=cflRepository.findAll();
         for(Cfl cfl:cflList){
             if(cfl.getExtendedDate() !=null){
                 if(date.isEqual(cfl.getExtendedDate())){
                     cfl.setGoalSettingStatusHrToMgr(true);
                     cfl.setCflMgrQuarterStatus(true);
                     cfl.setExtendedDate(null);
                     newCflList.add(cfl);
                 }
             }
         }

         if(!newCflList.isEmpty()){
             cflRepository.saveAll(newCflList);
         }
    }







// ===========================GOAL SETTING REVIEW =================================


    @Scheduled(cron = "0 0 10 15 12 ?", zone = "Asia/Kolkata")
    @Scheduled(cron = "0 0 10 15 3 ?", zone = "Asia/Kolkata")
    @Scheduled(cron = "0 0 10 15 6 ?", zone = "Asia/Kolkata")
    @Scheduled(cron = "0 0 10 15 9 ?", zone = "Asia/Kolkata")


   //  @Scheduled(cron = "0 * * * * ?")

    @Transactional
    public void sendMailToManagerRegardingGoalSettingReviewEveryQuarter() {
        List<Cfl> cflGoalSettingStatus = cflRepository.findByGoalSettingReviewStatusHrToMgr(false);
        List<Cfl> cflGoalSettings = new ArrayList<>();
        for (Cfl cflStatus : cflGoalSettingStatus) {
            cflStatus.setGoalSettingReviewStatusHrToMgr(true);
            cflGoalSettings.add(cflStatus);
        }
        cflRepository.saveAll(cflGoalSettings);
    }


    @Override
    @Scheduled(cron = "0 0 10 * * ?")
    // @Scheduled(cron = "0 * * * * ?")
    public void sendMailToManagerRegardingGoalSettingReview() {
        List<Cfl> allUpdatedStatus = new ArrayList<>();
        List<Cfl> cflGoalReviewSettingStatus = cflRepository.findByGoalSettingReviewStatusHrToMgr(true) ;
        for (Cfl cflStatus : cflGoalReviewSettingStatus) {
            String mgrEmail = cflStatus.getReportingManagerMail();
            String cflFirstName = cflStatus.getCflFirstName();
            String cflEmail = cflStatus.getCflEmail();
            String hrEmail=cflStatus.getHrMail();
            if (mgrEmail != null && !mgrEmail.isEmpty()) {
                // Send individual email for each manager and their respective CFL
                String subject = "Regarding Goal Setting Review With CFL";
                cflToMentorMail.sendGoalSettingReviewToManager(mgrEmail, cflFirstName, subject,cflEmail,hrEmail,getQuarter());
            }
            // Update goal setting review status to false after sending the email
            Cfl statusUpdate = cflRepository.findByCflEmail(cflEmail);
            statusUpdate.setGoalSettingReviewStatusHrToMgr(false);
            allUpdatedStatus.add(statusUpdate);
        }
        cflRepository.saveAll(allUpdatedStatus);
    }



    // manager accepted goal setting review response to hr and cfl  -----------

    @Override
    public void sendReviewMailFromManagerToCFLAndHr(String cflEmail) {
        Cfl cfl=cflRepository.findByCflEmail(cflEmail);
        String managerEmail = cfl.getReportingManagerMail();
        String managerName=managerRepository.findByManagerEmail(managerEmail).getManagerName();
        String cflName = cfl.getCflFirstName();
        String hrMail = cfl.getHrMail();
        LocalDate date = LocalDate.now();


        cflRepository.save(cfl);
        cflToMentorMail.sendGoalSettingReviewToCflAndHr(managerName,managerEmail,cflName,cflEmail,hrMail,date,getQuarter());
    }


    @Transactional
//    @Scheduled(cron = "0 * * * * ?")
    @Scheduled(cron = "0 0 10 * * ?")
    public void sendGoalSettingReviewMailBackToManagerAfterOneWeek(){
        LocalDate date=LocalDate.now();
        List<Cfl> newCflList = new ArrayList<>();
        List<Cfl> cflList=cflRepository.findAll();
        for(Cfl cfl:cflList){
            if(cfl.getGoalSettingReviewExtendedDate() !=null){
                if(date.isEqual(cfl.getGoalSettingReviewExtendedDate())){
                    cflToMentorMail.sendGoalSettingReviewToManager(cfl.getReportingManagerMail(), cfl.getCflFirstName(), "Regarding Goal Setting Review",cfl.getCflEmail(),cfl.getHrMail(),getQuarter());
                    cfl.setGoalSettingReviewExtendedDate(null);
                    newCflList.add(cfl);
                }
            }
        }

        if(!newCflList.isEmpty()){
            cflRepository.saveAll(newCflList);
        }
    }




    //  manager extended goal setting review response to hr and cfl -----------------------
    @Override
    @Transactional
    public void sendExtendReviewMailFromManagerToCFLAndHr(String cflEmail) {
        Cfl cfl=cflRepository.findByCflEmail(cflEmail);
        String managerEmail = cfl.getReportingManagerMail();
        String managerName=managerRepository.findByManagerEmail(managerEmail).getManagerName();
        String cflName = cfl.getCflFirstName();
        String hrMail = cfl.getHrMail();
        LocalDate date = LocalDate.now();

        // set extension date in db
        LocalDate extensionDate=date.plusDays(7);
        cfl.setGoalSettingReviewExtendedDate(extensionDate);

        cflRepository.save(cfl);

        sendExtendMailReviewBackToMgr(managerEmail,cflEmail);
        cflToMentorMail.sendGoalSettingExtendReviewToCflAndHr(managerName,cflName,cflEmail,hrMail,date,getQuarter());
    }

    public void sendExtendMailReviewBackToMgr(String managerEmail,String cflEmail) {
        System.out.println(managerEmail + " " + cflEmail);
        Cfl cfl = cflRepository.findByCflEmail(cflEmail);
        if (cfl == null) {
            return;
        }

        String managerName = managerRepository.findByManagerEmail(managerEmail).getManagerName();
        String cflName = cfl.getCflFirstName();
        LocalDate date = LocalDate.now();

        // Send the email
        cflToMentorMail.sendExtendGoalSettingReviewMailBackToMgr(managerName, cflName, managerEmail, date);
    }


    // =============================PROBATION============================================
    //===================================================================================

    @Autowired
    private ProbationTrackerRepository probationTrackerRepository;

    // automatically generated email for probation to manager
    public LocalDate getProbationDate(){
        int year=LocalDate.now().getYear();
        if(getQuarter().equalsIgnoreCase("Q4")){
            year-=1;
        }
        if(!cflRepository.findAllByYear(String.valueOf(year)).isEmpty()){
            return cflRepository.findAllByYear(String.valueOf(year)).get(0).getProbationDate();
        }
        return null;
    }

    // automatically generated email for probation to manager

    @Override
    @Scheduled(cron = "0 30 11 * * ?", zone = "Asia/Kolkata")
    @Transactional
    public void sendMailToManagerRegardingProbation() {
        LocalDate today = LocalDate.now();
        if(getProbationDate() !=null) {
            if (today.isEqual(getProbationDate()) || today.isAfter(getProbationDate())) {
                List<Cfl> allUpdatedStatus = new ArrayList<>();
                Set<ProbationTracker> probation = new LinkedHashSet<>();

                List<Cfl> cflGoalSettingStatus = cflRepository.findByProbationStatus(true);
                for (Cfl cflStatus : cflGoalSettingStatus) {
                    String mgrEmail = cflStatus.getReportingManagerMail();
                    String cflFirstName = cflStatus.getCflFirstName();
                    String cflEmail = cflStatus.getCflEmail();
                    String hrEmail = cflStatus.getHrMail();
                    if (mgrEmail != null && !mgrEmail.isEmpty()) {
                        String subject = "Regarding Probation Period Confirmation";
                        cflToMentorMail.sendProbationStatusToManager(mgrEmail, cflFirstName, subject, cflEmail, hrEmail);
                    }
                    Cfl statusUpdate = cflRepository.findByCflEmail(cflEmail);

                    // probation tracker

                    if(!probationTrackerRepository.existsByCflId(cflStatus.getEmpId())){
                        ProbationTracker probationTracker = new ProbationTracker();
                        probationTracker.setCflId(cflStatus.getEmpId());
                        probationTracker.setProbationInitiatedFromHrToManager(true);
                        probation.add(probationTracker);
                    }

                    allUpdatedStatus.add(statusUpdate);
                }
                cflRepository.saveAll(allUpdatedStatus);
                System.out.println(probation);
                probationTrackerRepository.saveAll(probation);
            }
        }
    }


    // =============== Probation Acceptance =================================

    // manager accepted probation response to hr and cfl --------------------
    @Override
    public void sendProbationMailFromManagerToCFLAndHr(String cflEmail) {
        Cfl cfl=cflRepository.findByCflEmail(cflEmail);
        String managerEmail = cfl.getReportingManagerMail();
        String managerName=managerRepository.findByManagerEmail(managerEmail).getManagerName();
        String cflName = cfl.getCflFirstName();
        String hrMail = cfl.getHrMail();
        LocalDate date = LocalDate.now();

        // probation tracker
        ProbationTracker probationTracker=probationTrackerRepository.findByCflId(cfl.getEmpId());
        probationTracker.setResponseSendByManagerToCfl(true);
        probationTracker.setResponseSendByManagerToHr(true);
        probationTrackerRepository.save(probationTracker);


        // close mail sending of probation only when manager accepts
        cfl.setProbationStatus(false);
        cflRepository.save(cfl);

        cflToMentorMail.sendProbationToCflAndHr(managerName,cflName,cflEmail,hrMail,date);
    }


    // ==================== Probation Extension =================

    //  manager extended probation response to hr and cfl -----------------------
    @Async
    @Override
    @Transactional
    public void sendProbationExtendMailFromManagerToCFLAndHr(String cflEmail) {
        Cfl cfl=cflRepository.findByCflEmail(cflEmail);
        String managerEmail = cfl.getReportingManagerMail();
        String managerName=managerRepository.findByManagerEmail(managerEmail).getManagerName();
        String cflName = cfl.getCflFirstName();
        String hrMail = cfl.getHrMail();
        LocalDate date = LocalDate.now();
        sendGoalSettingExtendProbationMailBackToMgr(managerEmail,cflEmail);

        cfl.setExtendedProbationDate(date.plusDays(7));
        cflRepository.save(cfl);

        cflToMentorMail.sendProbationExtendToCflAndHr(managerName,cflName,cflEmail,hrMail,date.plusDays(7));
    }

    @Transactional
//    @Scheduled(cron = "0 * * * * ?")
    @Scheduled(cron = "0 0 10 * * ?")
    public void sendProbationExtensionMailBackToManagerAfterOneWeek(){
        LocalDate date=LocalDate.now();
        List<Cfl> newCflList = new ArrayList<>();
        List<Cfl> cflList=cflRepository.findAll();
        for(Cfl cfl:cflList){
            if(cfl.getExtendedProbationDate() !=null){
                if(date.isEqual(cfl.getExtendedProbationDate())){
                    cflToMentorMail.sendProbationStatusToManager(cfl.getReportingManagerMail(), cfl.getCflFirstName(), "Regarding Probation Meeting", cfl.getCflEmail(), cfl.getHrMail());
                    cfl.setGoalSettingReviewExtendedDate(null);
                    newCflList.add(cfl);
                }
            }
        }

        if(!newCflList.isEmpty()){
            cflRepository.saveAll(newCflList);
        }
    }


    public void sendGoalSettingExtendProbationMailBackToMgr(String managerEmail,String cflEmail) {
        System.out.println(managerEmail + " " + cflEmail);
        Cfl cfl = cflRepository.findByCflEmail(cflEmail);
        if (cfl == null) {
            return;
        }

        String managerName = managerRepository.findByManagerEmail(managerEmail).getManagerName();
        String cflName = cfl.getCflFirstName();
        LocalDate date = LocalDate.now();

        // Send the email
        cflToMentorMail.sendExtendProbationMailBackToMgr(managerName, cflName, managerEmail, date);
    }

    // ============== Approve Manager ===========================

    @Async
    @Override
    public Boolean approveMail(String quarter,Long empId,String mgrEmail) {
        String cflName=cflRepository.findByEmpId(empId).getCflFirstName();
        String cflEmail=cflRepository.findByEmpId(empId).getCflEmail();
        String managerName=managerRepository.findByManagerEmail(mgrEmail).getManagerName();
        String subject="Regarding Goal Setting Approval for quarter "+quarter;
        return cflToMentorMail.sendApproveConfirmationToCfl(cflName,cflEmail,quarter,managerName,subject);
    }

    @Override
    public Boolean isCflEmailExists(String cflEmail) {
        return cflRepository.existsByCflEmail(cflEmail);
    }

    @Override
    public Boolean isMentorEmailExists(String mentorEmail) {
        return cflRepository.existsByMentorEmail(mentorEmail);
    }

    @Override
    public Boolean isManagerEmailExists(String managerEmail) {
        return cflRepository.existsByReportingManagerMail(managerEmail);
    }


    // ============== Mentoring Log Book =============================

    @Scheduled(cron = "0 0 10 1 * ?")
    public void sendMentoringLogBook(){
        LocalDate date=LocalDate.now();
        String year = String.valueOf(date.getYear());
        List<Cfl> currentYearCflList=cflRepository.findAllByYear(year);
        System.out.println(currentYearCflList);
        List<String>listOfEmails=new ArrayList<String>();
        for(Cfl cfl:currentYearCflList){
            String cflEmail=cfl.getCflEmail();
            listOfEmails.add(cflEmail);
        }
        cflToMentorMail.sendMentoringLogBook(listOfEmails);
    }

    // ====================Annual appraisal ===================

    // trigger mail for all manager regarding annual appraisal due
//    @Scheduled(cron = "0 0 10 10 5 ?")
    @Scheduled(cron = "0 0 16 20 6 ?")
    public void sendEmailRegardingAnnualAppraisal(){
        System.out.println("cfl appraisal  called");
        LocalDate date=LocalDate.now();
        String year = String.valueOf(date.getYear()-1);
        List<Cfl> currentYearCflList=cflRepository.findAllByYear(year);
        System.out.println(currentYearCflList);
        List<String>listOfEmails=new ArrayList<String>();
        for(Cfl cfl:currentYearCflList){
//            String managerEmail=cfl.getReportingManagerMail();
            if(cfl.getCflEmail() !=null){
                String cflEmail=cfl.getCflEmail();
                listOfEmails.add(cflEmail);
            }
        }
        Set<String> setOfEmails=new HashSet<>(listOfEmails);
        List<String> listOfSetMails = new ArrayList<>(setOfEmails);
        cflToMentorMail.sendEmailRegardingAnnualAppraisal(listOfSetMails);
    }



    @Override
    public String findCflMentorOrManagerEmail(String mail) {
        try {
            // Step 1: Try to find the CFL's name using email
            Cfl cfl = cflRepository.findByCflEmail(mail);
            if (cfl != null) {
                String cflName = cfl.getCflFirstName() + " " + cfl.getCflLastName();
                if (!cflName.trim().isEmpty()) {
                    return cflName;
                } else {
                    throw new EntityNotFoundException("CFL not found for email: " + mail);
                }
            } else {
                throw new EntityNotFoundException("CFL not found for email: " + mail);
            }
        } catch (EntityNotFoundException e) {
            // Step 2: If CFL not found, search for mentor's name using email
            try {
                List<Cfl> mentors = cflRepository.findByMentorEmail(mail);
                if (!mentors.isEmpty()) {
                    String mentorName = mentors.get(0).getMentorName();
                    if (!mentorName.trim().isEmpty()) {
                        return mentorName;
                    } else {
                        throw new EntityNotFoundException("Mentor not found for email: " + mail);
                    }
                } else {
                    throw new EntityNotFoundException("Mentor not found for email: " + mail);
                }
            } catch (EntityNotFoundException ex) {
                // Step 3: If mentor not found, search for manager's name using email
                try {
                    List<Cfl> managers = cflRepository.findByReportingManagerMail(mail);
                    if (!managers.isEmpty()) {
                        String managerName = managers.get(0).getReportingManager();
                        if (!managerName.trim().isEmpty()) {
                            return managerName;
                        } else {
                            throw new EntityNotFoundException("Manager not found for email: " + mail);
                        }
                    } else {
                        throw new EntityNotFoundException("Manager not found for email: " + mail);
                    }
                } catch (EntityNotFoundException exc) {
                    // Final fallback if none are found
                    return "No CFL, mentor, or manager found for email: " + mail;
                }
            }
        }
    }


    // otp process
    @Override
    @Transactional
    public void generateOtp(String email,String userRole) {

        // Generate a random 4-digit OTP
        Random random = new Random();
        int otp = 1000 + random.nextInt(9000);
        String otpString = String.valueOf(otp);

        // Check each repository to find where the email belongs

        if(userRole.equalsIgnoreCase("CFL")){
            Cfl cfl = cflRepository.findByCflEmail(email);
            if (cfl != null) {
                cfl.setOtp(otpString);
                cflRepository.save(cfl);
                cflToMentorMail.sendOtpEmail(email, otpString);
                System.out.println("Generated OTP for Cfl: " + otp);
                return;
            }
        }


        if(userRole.equalsIgnoreCase("MENTOR")) {
            Mentor mentor = mentorRepository.findByMentorEmail(email);
            if (mentor != null) {
                mentor.setOtp(otpString);
                mentorRepository.save(mentor);
                cflToMentorMail.sendOtpEmail(email, otpString);
                System.out.println("Generated OTP for Mentor: " + otp);
                return;
            }
        }

        if(userRole.equalsIgnoreCase("HR")) {
            HR hr = hrRepository.findByHrEmail(email);
            if (hr != null) {
                hr.setOtp(otpString);
                hrRepository.save(hr);
                cflToMentorMail.sendOtpEmail(email, otpString);
                System.out.println("Generated OTP for HR: " + otp);
                return;
            }
        }

        if(userRole.equalsIgnoreCase("MANAGER")) {
            Manager manager = managerRepository.findByManagerEmail(email);
            if (manager != null) {
                manager.setOtp(otpString);
                managerRepository.save(manager);
                cflToMentorMail.sendOtpEmail(email, otpString);
                System.out.println("Generated OTP for Manager: " + otp);
                return;
            }
        }

        // If no matching email was found, log or handle accordingly
        System.out.println("Email not found in any records.");
        throw new RuntimeException("Email not found");
    }

    @Override
    public Boolean verifyOtp(String email, String otp) {
        Boolean result = false;

        // Check OTP for Cfl
        Cfl cfl = cflRepository.findByCflEmail(email);
        if (cfl != null && otp.equals(cfl.getOtp())) {
            result = true;
            return result;
        }

        // Check OTP for Mentor
        Mentor mentor = mentorRepository.findByMentorEmail(email);
        if (mentor != null && otp.equals(mentor.getOtp())) {
            result = true;
            return result;
        }

        // Check OTP for HR
        HR hr = hrRepository.findByHrEmail(email);
        if (hr != null && otp.equals(hr.getOtp())) {
            result = true;
            return result;
        }

        // Check OTP for Manager
        Manager manager = managerRepository.findByManagerEmail(email);
        if (manager != null && otp.equals(manager.getOtp())) {
            result = true;
            return result;
        }

        return result;
    }


    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Register enterNewPassword(String email, String newPassword,String userRole) {
        Register register=registerRepository.findByUserNameAndRole(email,Role.valueOf(userRole));
        String password=passwordEncoder.encode(newPassword);
        register.setUserPassword(password);
        return registerRepository.save(register);
    }


    @Override
    public Cfl updateCfl(Long id, Cfl cfl) {
        cfl.setId(id);
        return cflRepository.save(cfl);
    }



    @Autowired
    private CflSkillRepository cflSkillRepository;

    @Autowired
    private ThirtyDaysGoalsRepository thirtyDaysGoalsRepository;

    @Autowired
    private SixtyDaysGoalsRepository sixtyDaysGoalsRepository;

    @Autowired
    private NinetyDaysGoalsRepository ninetyDaysGoalsRepository;

    @Autowired
    private ProbationConfirmationRepository probationConfirmationRepository;

    @Autowired
    private ManagerRatingRepository managerRatingRepository;

    @Autowired
    private LogBookRepository logBookRepository;



    @Override
    public List<CompleteCflTableDTO> getAllDetailedReports() {
        List<CompleteCflTableDTO> completeCflTableDTOS = new ArrayList<>();

        // Fetch all data needed for the reports
        List<CflSkill> cflSkills = cflSkillRepository.findAll();
        List<ProbationConfirmation> probationConfirmations = probationConfirmationRepository.findAll();

        // Create a map to hold reports by EmpId
        Map<Long, CompleteCflTableDTO> reportMap = new HashMap<>();

        // Process CflSkills
        for (CflSkill cflSkill : cflSkills) {
            CompleteCflTableDTO dto = new CompleteCflTableDTO();
            Cfl cfl = cflRepository.findByEmpId(cflSkill.getEmpId());

            if (cfl != null) {
                // Set common data from Cfl
                dto.setEmpId(cfl.getEmpId());
                dto.setCflName(cfl.getCflFirstName());
                dto.setCflEmail(cfl.getCflEmail());
                dto.setCflDepartment(cfl.getCflDepartment());
                dto.setCflLocation(cfl.getCflLocation());
                dto.setReportingManagerName(cfl.getReportingManager());
                dto.setReportingManagerEmail(cfl.getReportingManagerMail());
                dto.setCflSubDepartment(cfl.getCflSubDepartment());
            }

            // Add skill-specific data
            dto.setQuarter(cflSkill.getQuarter());
            dto.setPrimarySkills(cflSkill.getPrimarySkills());
            dto.setSecondarySkills(cflSkill.getSecondarySkills());
            dto.setOtherSkills(cflSkill.getOtherSkills());

            // Add to report map
            reportMap.put(cflSkill.getEmpId(), dto);
        }


        List<ManagerRating>managerRatings=managerRatingRepository.findAll();
        for(ManagerRating managerRating : managerRatings){


            Long empId = managerRating.getEmpId();
            CompleteCflTableDTO dto = reportMap.getOrDefault(empId, new CompleteCflTableDTO());
            Cfl cfl=cflRepository.findByEmpId(managerRating.getEmpId());
            dto.setEmpId(cfl.getEmpId());

            dto.setCflName(cfl.getCflFirstName());
            dto.setCflEmail(cfl.getCflEmail());
            dto.setCflDepartment(cfl.getCflDepartment());
            dto.setCflLocation(cfl.getCflLocation());
            dto.setReportingManagerName(cfl.getReportingManager());
            dto.setReportingManagerEmail(cfl.getReportingManagerMail());
            dto.setCflDepartment(cfl.getCflDepartment());
            dto.setCflSubDepartment(cfl.getCflSubDepartment());


            dto.setTalentLevel(managerRating.getTalentLevel());
            dto.setPotentialLevel(managerRating.getPotentialLevel());
            dto.setPerformanceLevel(managerRating.getPerformanceLevel());
            dto.setQuarter(managerRating.getAnnual());
            reportMap.put(managerRating.getEmpId(), dto);
        }


        // Assuming the goalSetting field exists in CompleteCflTableDTO
        List<ThirtyDaysGoals> thirtyDaysGoals = thirtyDaysGoalsRepository.findAll();
        List<SixtyDaysGoals> sixtyDaysGoals = sixtyDaysGoalsRepository.findAll();
        List<NinetyDaysGoals> ninetyDaysGoals = ninetyDaysGoalsRepository.findAll();

        // 30 Days Goals
        for (ThirtyDaysGoals thirtyDaysGoalsObj : thirtyDaysGoals) {
            Long empId = thirtyDaysGoalsObj.getEmpId();
            CompleteCflTableDTO dto = reportMap.getOrDefault(empId, new CompleteCflTableDTO());

            // Set 30-day goals
            dto.setThirtyDaysGoal(thirtyDaysGoalsObj.getGoal());
            dto.setThirtyDaysDeliverable(thirtyDaysGoalsObj.getDeliverable());
            dto.setThirtyDaysStatus(thirtyDaysGoalsObj.getStatus());
            dto.setThirtyDaysWeightage(thirtyDaysGoalsObj.getWeightage());
            dto.setThirtyDaysQuarter(thirtyDaysGoalsObj.getQuarter());

            reportMap.put(empId, dto);
        }

        // 60 Days Goals
        for (SixtyDaysGoals sixtyDaysGoalsObj : sixtyDaysGoals) {
            Long empId = sixtyDaysGoalsObj.getEmpId();
            CompleteCflTableDTO dto = reportMap.getOrDefault(empId, new CompleteCflTableDTO());

            // Set 60-day goals
            dto.setSixtyDaysGoal(sixtyDaysGoalsObj.getGoal());
            dto.setSixtyDaysDeliverable(sixtyDaysGoalsObj.getDeliverable());
            dto.setSixtyDaysStatus(sixtyDaysGoalsObj.getStatus());
            dto.setSixtyDaysWeightage(sixtyDaysGoalsObj.getWeightage());
            dto.setSixtyDaysQuarter(sixtyDaysGoalsObj.getQuarter());

            reportMap.put(empId, dto);
        }

        // 90 Days Goals
        for (NinetyDaysGoals ninetyDaysGoalsObj : ninetyDaysGoals) {
            Long empId = ninetyDaysGoalsObj.getEmpId();
            CompleteCflTableDTO dto = reportMap.getOrDefault(empId, new CompleteCflTableDTO());

            // Set 90-day goals
            dto.setNinetyDaysGoal(ninetyDaysGoalsObj.getGoal());
            dto.setNinetyDaysDeliverable(ninetyDaysGoalsObj.getDeliverable());
            dto.setNinetyDaysStatus(ninetyDaysGoalsObj.getStatus());
            dto.setNinetyDaysWeightage(ninetyDaysGoalsObj.getWeightage());
            dto.setNinetyDaysQuarter(ninetyDaysGoalsObj.getQuarter());
            reportMap.put(empId, dto);
        }


        // logbook
        List<LogBook>logBooks=logBookRepository.findAll();
        for(LogBook logBook : logBooks){
            Long empId=logBook.getEmpId();
            CompleteCflTableDTO dto = reportMap.getOrDefault(empId, new CompleteCflTableDTO());
            dto.setSubmittedDate(logBook.getDate());
            dto.setSubmittedTime(logBook.getTime());
            dto.setLogBookName(logBook.getLogBookFileName());
            reportMap.put(empId, dto);
        }

        // Process ProbationConfirmations
        for (ProbationConfirmation probationConfirmation : probationConfirmations) {
            CompleteCflTableDTO dto = reportMap.getOrDefault(probationConfirmation.getEmployeeCode(), new CompleteCflTableDTO());
            Cfl cfl = cflRepository.findByEmpId(probationConfirmation.getEmployeeCode());

            dto.setDesignation(cfl.getCflDesignation());
            dto.setLocation(cfl.getCflLocation());
            dto.setDepartment(cfl.getCflDepartment());
            dto.setProject(cfl.getCflProject());
            dto.setDateOfJoining(cfl.getJoiningDate());
            dto.setDateOfConfirmation(probationConfirmation.getDateOfConfirmation());


            // Set probation confirmation-specific data
            dto.setPerformanceStandard1(probationConfirmation.getDropdown1());
            dto.setQualityOfWork1(probationConfirmation.getDropdown2());
            dto.setSubjectKnowledge1(probationConfirmation.getDropdown3());
            dto.setInitiativeAndWillingness1(probationConfirmation.getDropdown4());
            dto.setAttendance1(probationConfirmation.getDropdown5());
            dto.setTeamWork1(probationConfirmation.getDropdown6());
            dto.setOrganizingAndTeamManagement1(probationConfirmation.getDropdown7());
            dto.setAttitudeTowardsWork1(probationConfirmation.getDropdown8());
            dto.setWellVersedWithCompanyPolicies1(probationConfirmation.getDropdown9());
            dto.setCompanyCodeOfConduct1(probationConfirmation.getDropdown10());

            // Similarly, for the second set of fields
            dto.setPerformanceStandard2(probationConfirmation.getDropdown11());
            dto.setQualityOfWork2(probationConfirmation.getDropdown12());
            dto.setSubjectKnowledge2(probationConfirmation.getDropdown13());
            dto.setInitiativeAndWillingness2(probationConfirmation.getDropdown14());
            dto.setAttendance2(probationConfirmation.getDropdown15());
            dto.setTeamWork2(probationConfirmation.getDropdown16());
            dto.setOrganizingAndTeamManagement2(probationConfirmation.getDropdown17());
            dto.setAttitudeTowardsWork2(probationConfirmation.getDropdown18());
            dto.setWellVersedWithCompanyPolicies2(probationConfirmation.getDropdown19());
            dto.setCompanyCodeOfConduct2(probationConfirmation.getDropdown20());

            // Additional fields
            dto.setRemark3(probationConfirmation.getRemark3());
            dto.setRemark6(probationConfirmation.getRemark6());
            dto.setConfirmation(probationConfirmation.getConfirmation());
            dto.setReportingManagerName(probationConfirmation.getReportingManagerName());
            dto.setReportingManagerSignature(probationConfirmation.getReportingManagerSignature());
            dto.setBuHeadName(probationConfirmation.getBuHeadName());
            dto.setBuHeadSignature(probationConfirmation.getBuHeadSignature());
            dto.setHrRepresentativeName(probationConfirmation.getHrRepresentativeName());
            dto.setHrRepresentativeSignature(probationConfirmation.getHrRepresentativeSignature());
            dto.setStatus(probationConfirmation.getStatus());

            // Update report map
            reportMap.put(probationConfirmation.getEmployeeCode(), dto);
        }

        // Return the final list of completed reports
        completeCflTableDTOS.addAll(reportMap.values());
        return completeCflTableDTOS;
    }

    @Override
    public String countCflScreenTime(String userMail,String timeInMinute) {
        Cfl cfl=cflRepository.findByCflEmail(userMail);
        String totalCflScreenTime="";
        if(cfl!=null) {
            String screenTime = cfl.getCflScreenTime();
            if (screenTime == null || screenTime.isEmpty()) {
                screenTime = timeInMinute;
            } else {
                screenTime += "," + timeInMinute;
            }
            cfl.setCflScreenTime(screenTime);
            cfl = cflRepository.save(cfl);
            totalCflScreenTime=cfl.getCflScreenTime();
        }
        return totalCflScreenTime;
    }


    @Override
    public List<Cfl> getAllCflByProbationStatusAndYear(Boolean probationStatus,String year) {
        return cflRepository.findByProbationStatusAndYear(probationStatus,year);
    }

    @Override
    @Transactional
    public Cfl changeRequest(Long empId, String newValueForChange,String changeType) {
        System.out.println(changeType+"changeType");
        System.out.println(newValueForChange+"newValueForChange");
        if(changeType.equals("Location")){
            Cfl cfl=cflRepository.findByEmpId(empId);

            String currentLocation = cfl.getCflLocation();
            String lastRecorded = null;

            if (cfl.getLocationChange() != null && !cfl.getLocationChange().isEmpty()) {
                String[] history = cfl.getLocationChange().split(",");
                String lastEntry = history[history.length - 1];
                lastRecorded = lastEntry.split("\\+")[0];  // get the last manager name
            }

            if (lastRecorded == null || !lastRecorded.equals(currentLocation)) {
                String newEntry = currentLocation + "+" + String.valueOf(LocalDateTime.now()) + ",";
                String updatedHistory = (cfl.getLocationChange() == null ? "" : cfl.getLocationChange()) + newEntry;
                cfl.setLocationChange(updatedHistory);
            }



            cfl.setCflLocation(newValueForChange);
            cfl=cflRepository.save(cfl);
            return cfl;
        }
        else if(changeType.equals("Department")){

            Cfl cfl=cflRepository.findByEmpId(empId);


            String currentDepartment = cfl.getCflDepartment();
            String lastRecorded = null;

            if (cfl.getDepartmentChange() != null && !cfl.getDepartmentChange().isEmpty()) {
                String[] history = cfl.getDepartmentChange().split(",");
                String lastEntry = history[history.length - 1];
                lastRecorded = lastEntry.split("\\+")[0];  // get the last manager name
            }

            if (lastRecorded == null || !lastRecorded.equals(currentDepartment)) {
                String newEntry = currentDepartment + "+" + String.valueOf(LocalDateTime.now()) + ",";
                String updatedHistory = (cfl.getDepartmentChange() == null ? "" : cfl.getDepartmentChange()) + newEntry;
                cfl.setDepartmentChange(updatedHistory);
            }


            cfl.setCflDepartment(newValueForChange);
            cfl=cflRepository.save(cfl);
            return cfl;
        }
        else if(changeType.equals("Project")){
            Cfl cfl=cflRepository.findByEmpId(empId);

            String currentProject = cfl.getCflProject();
            String lastRecorded = null;

            if (cfl.getProjectChange() != null && !cfl.getProjectChange().isEmpty()) {
                String[] history = cfl.getProjectChange().split(",");
                String lastEntry = history[history.length - 1];
                lastRecorded = lastEntry.split("\\+")[0];  // get the last manager name
            }

            if (lastRecorded == null || !lastRecorded.equals(currentProject)) {
                String newEntry = currentProject + "+" + String.valueOf(LocalDateTime.now()) + ",";
                String updatedHistory = (cfl.getProjectChange() == null ? "" : cfl.getProjectChange()) + newEntry;
                cfl.setProjectChange(updatedHistory);
            }




            cfl.setCflProject(newValueForChange);
            cfl=cflRepository.save(cfl);
            return cfl;
        }
        else if(changeType.equals("Manager")){
            Cfl cfl=cflRepository.findByEmpId(empId);
            System.out.println(cfl+"cfl");
            String currentManager = cfl.getReportingManager();
            String lastRecorded = null;

            if (cfl.getManagerChange() != null && !cfl.getManagerChange().isEmpty()) {
                String[] history = cfl.getManagerChange().split(",");
                String lastEntry = history[history.length - 1];
                lastRecorded = lastEntry.split("\\+")[0];  // get last manager name
            }

            if (lastRecorded == null || !lastRecorded.equals(currentManager)) {
                String newEntry = currentManager + "+" + String.valueOf(LocalDateTime.now()) + ",";
                String updatedHistory = (cfl.getManagerChange() == null ? "" : cfl.getManagerChange()) + newEntry;
                cfl.setManagerChange(updatedHistory);
            }

            Manager manager=managerRepository.findByManagerId(Long.valueOf(newValueForChange));
            System.out.println(manager+"manager");
            if(manager != null){
                cfl.setManagerId(Long.valueOf(newValueForChange));
                // pull all details of new manager details from cfl table
                cfl.setManagerDepartment(manager.getManagerDepartment());
                cfl.setManagerDesignation(manager.getManagerDesignation());
                cfl.setManagerLocation(manager.getManagerLocation());
                cfl.setReportingManagerMail(manager.getManagerEmail());
                cfl.setReportingManager(manager.getManagerName());
                cfl=cflRepository.save(cfl);
                return cfl;
            }
            else{
                return null;
            }
        }
        else if(changeType.equals("Mentor")){
            Cfl cfl=cflRepository.findByEmpId(empId);

            String currentMentor = cfl.getMentorName();
            String lastRecorded = null;

            if (cfl.getMentorChange() != null && !cfl.getMentorChange().isEmpty()) {
                String[] history = cfl.getMentorChange().split(",");
                String lastEntry = history[history.length - 1];
                lastRecorded = lastEntry.split("\\+")[0];  // get the last manager name
            }

            if (lastRecorded == null || !lastRecorded.equals(currentMentor)) {
                String newEntry = currentMentor + "+" + String.valueOf(LocalDateTime.now()) + ",";
                String updatedHistory = (cfl.getMentorChange() == null ? "" : cfl.getMentorChange()) + newEntry;
                cfl.setMentorChange(updatedHistory);
            }




            Mentor mentor=mentorRepository.findByMentorId(Long.valueOf(newValueForChange));
            if(mentor != null){
                cfl.setManagerId(Long.valueOf(newValueForChange));
                // pull all details of new mentor details from the cfl table
                cfl.setMentorDepartment(mentor.getMentorDepartment());
                cfl.setMentorDesignation(mentor.getMentorDesignation());
                cfl.setMentorLocation(mentor.getMentorLocation());
                cfl.setMentorEmail(mentor.getMentorEmail());
                cfl.setMentorName(mentor.getMentorName());
                cfl=cflRepository.save(cfl);
                return cfl;
            }
            else{
                return null;
            }
        }
        return null;
    }

    @Override
    public void sendChangeRequestMailToCFL(String cflMail, String changeType) {
        cflToMentorMail.sendChangeRequestMailToCFL(cflMail,changeType);
    }

    @Override
    public void generateMailForCFLRegardingProbationExtension(String cflMail) {
        cflToMentorMail.sendProbationExtensionForThreeMonthToCFL(cflMail);
    }


    @Override
    public void generateMailForHRRegardingProbationExtension(String hrMail) {
        cflToMentorMail.sendProbationExtensionForThreeMonthToHR(hrMail);
    }

//    @Override
//    public void generateMailForManagerRegardingProbationExtension(String cflMail, String managerMail) {
//        String strDate=cflRepository.findByCflEmail(cflMail).getJoiningDate();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
//        LocalDate date = LocalDate.parse(strDate, formatter);
//        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        cflToMentorMail.sendProbationExtensionForThreeMonthToManager(LocalDate.parse(date.format(outputFormatter)),cflMail,managerMail);
//    }


    @Override
    public void generateMailForManagerRegardingProbationExtension(String cflMail, String managerMail) {
        String strDate = cflRepository.findByCflEmail(cflMail).getJoiningDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate date = LocalDate.parse(strDate, formatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String fromDateFormatted = date.plusMonths(6).format(outputFormatter);
        String toDateFormatted = date.plusMonths(9).format(outputFormatter);
        cflToMentorMail.sendProbationExtensionForThreeMonthToManager(fromDateFormatted, toDateFormatted, cflMail, managerMail);
    }

    @Override
    public void sendProbationConfirmationFeedBackToManager(String cflMail, String managerMail) {
        cflToMentorMail.sendProbationConfirmationFeedBackToManager(cflMail,managerMail);
    }

    @Override
    @Transactional
    @Async
    public void sendMailToManagerRegardingCFLRatingForAnnualAppraisal(String cflEmail,String cflKeyAccomplishment) {
        Cfl cfl=cflRepository.findByCflEmail(cflEmail);
        annualAppraisalInfoRepository.save(AnnualAppraisalInfo.builder().empId(cfl.getEmpId()).empName(cfl.getCflFirstName()).year(Long.valueOf(cfl.getYear())).build());
        cflToMentorMail.sendMailToManagerRegardingCFLRatingForAnnualAppraisal(cflEmail,cfl.getReportingManager(),cfl.getReportingManagerMail(),cfl.getHrMail(),cflKeyAccomplishment);
    }


    @Override
    public boolean isAnnualAppraisalFilledByCfl(Long empId) {
        return annualAppraisalInfoRepository.existsByEmpId(empId);
    }


//    @Override
//    public Cfl getCflBasedOnEmpIdOREmail(Long empId, String email) {
//        System.out.println(empId+" "+email);
//        return cflRepository.findByEmpIdOrCflEmail(empId,email).orElse(null);
//    }


    @Scheduled(cron = "0 0 10 1 * ?")
    public String deleteCflDataScreenOnTime(){
        List<Cfl> cflList= cflRepository.findAll().stream().peek(i->i.setCflScreenTime(null)).toList();
        if(cflList.isEmpty()){
            return "refreshed";
        }
        else{
            return "not refreshed";
        }
    }
}

