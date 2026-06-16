package com.cfl.cfl_project.email;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Properties;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Configuration
@Component
public class CflToMentorMail {

//    @Value("${mail.host}")
//    private String mailHost;
//
//    @Value("${mail.port}")
//    private int mailPort;
//
//    @Value("${mail.username}")
//    private String mailUsername;
//
//    @Value("${mail.password}")
//    private String mailPassword;

//    private final TemplateEngine templateEngine;
//
//
//    public CflToMentorMail(TemplateEngine templateEngine) {
//        this.templateEngine = templateEngine;
//    }



    @Value("${spring.mail.host}")
    private String mailHost;

    @Value("${spring.mail.port}")
    private int mailPort;

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Value("${spring.mail.password}")
    private String mailPassword;
    private final SpringTemplateEngine templateEngine;

    public CflToMentorMail(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setPort(mailPort);
        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassword);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        return mailSender;
    }


    // Delegate method to preserve compatibility
    public boolean sendEmail(String cflName,String cflEmail,String cflDesignation,String cflDepartment,String cflLocation,String reportingManager,String hrName, String mentorName, String email, String subject, String body,String year,String ccEmail,String type) {
        return sendEmail(cflName, cflEmail, cflDesignation, cflDepartment, cflLocation, reportingManager, hrName, mentorName, email, subject, body, year, ccEmail, type, null, null);
    }

    // mentee to mentor with date and time
    public boolean sendEmail(String cflName,String cflEmail,String cflDesignation,String cflDepartment,String cflLocation,String reportingManager,String hrName, String mentorName, String email, String subject, String body,String year,String ccEmail,String type, String meetingDate, String meetingTime) {
        boolean isSent = false;
        Context context = new Context();
        context.setVariable("body", body);
        context.setVariable("cflName", cflName);
        context.setVariable("cflEmail", cflEmail);
        context.setVariable("cflDesignation", cflDesignation);
        context.setVariable("cflDepartment", cflDepartment);
        context.setVariable("cflLocation", cflLocation);
        context.setVariable("reportingManager", reportingManager);
        context.setVariable("hrName", hrName);
        context.setVariable("mentorName", mentorName);
        context.setVariable("meetingDate", meetingDate);
        context.setVariable("meetingTime", meetingTime);
        context.setVariable("seniorEmail", email);

        context.setVariable("type", type);
        LocalDate date=LocalDate.now();
        context.setVariable("year",  date.getYear());
        context.setVariable("link", "https://cal.com/startsmart/startsmart");

        String process="";
        if(type.equals("Mentor")) {
            process = templateEngine.process("CflToMentorMailTemplate.html", context);
        }

        else if(type.equals("Manager")){
            process = templateEngine.process("CflToManagerMailTemplate.html", context);
        }

        else if(type.equals("HR")){
            process = templateEngine.process("CflToHRMailTemplate.html", context);
        }

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(process, true);

            // cc
            String []ccList=ccEmail.split(",");
            helper.setCc(ccList);
            javaMailSender().send(mimeMessage);
            isSent = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isSent;
    }


    // from mentor to mentee



    public void sendMailFromMentorToMentee(String cflEmail,String mentorName, String cflName,String subject, String message, String status) {

        // Set up the email content
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("cflEmail",cflEmail);
        context.setVariable("mentorName",mentorName);
        context.setVariable("cflName",cflName);
        context.setVariable("status", status);

        String process = templateEngine.process("MentorToMenteeEmailTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            helper.setFrom(mailUsername);
            helper.setTo(cflEmail);
            helper.setSubject(subject);
            helper.setText(process, true);

            javaMailSender().send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    // hr to cfl accept

public void sendMailFromHRToCFL(String cflEmail,String hrName, String cflName,String subject, String message) {

    // Set up the email content
    Context context = new Context();
    context.setVariable("message", message);
    context.setVariable("cflEmail",cflEmail);
    context.setVariable("hrName",hrName);
    context.setVariable("cflName",cflName);

    String process = templateEngine.process("HRToCFLMailTemplate.html", context);

    try {
        MimeMessage mimeMessage = javaMailSender().createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        helper.setFrom(mailUsername);
        helper.setTo(cflEmail);
        helper.setSubject(subject);
        helper.setText(process, true);

        javaMailSender().send(mimeMessage);
    } catch (Exception e) {
        e.printStackTrace();
    }

}




    // hr to cfl accept

    public void sendMailFromHRToCFLExtend(String cflEmail,String hrName, String cflName,String subject, String message) {

        // Set up the email content
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("cflEmail",cflEmail);
        context.setVariable("hrName",hrName);
        context.setVariable("cflName",cflName);

        String process = templateEngine.process("HRToCFLExtendMailTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(cflEmail);
            helper.setSubject(subject);
            helper.setText(process, true);

            javaMailSender().send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    // manager to cfl accept


public void sendMailFromManagerToCFL(String cflEmail,String managerName, String cflName,String subject, String message) {

    // Set up the email content
    Context context = new Context();
    context.setVariable("message", message);
    context.setVariable("cflEmail",cflEmail);
    context.setVariable("managerName",managerName);
    context.setVariable("cflName",cflName);

    String process = templateEngine.process("ManagerToCFLMailTemplate.html", context);

    try {
        MimeMessage mimeMessage = javaMailSender().createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        helper.setFrom(mailUsername);
        helper.setTo(cflEmail);
        helper.setSubject(subject);
        helper.setText(process, true);

        javaMailSender().send(mimeMessage);
    } catch (Exception e) {
        e.printStackTrace();
    }

}



    // manager to cfl accept


    public void sendMailFromManagerToCFLExtend(String cflEmail,String managerName, String cflName,String subject, String message) {

        // Set up the email content
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("cflEmail",cflEmail);
        context.setVariable("managerName",managerName);
        context.setVariable("cflName",cflName);

        String process = templateEngine.process("ManagerToCFLExtendMailTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(cflEmail);
            helper.setSubject(subject);
            helper.setText(process, true);

            javaMailSender().send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public boolean sendGoalSetting(String recipientEmail, String subject, String message) {
        boolean isSent = true;

        // Set up the email content
        Context context = new Context();
        context.setVariable("message", message);

        String process = templateEngine.process("MentorToMenteeMailResponse.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(recipientEmail);
            helper.setSubject(subject);
            helper.setText(process, true);

            javaMailSender().send(mimeMessage);
            isSent = true;
        } catch (Exception e) {
            e.printStackTrace();
            isSent = false;
        }

        return isSent;
    }

    // sendPasswordEmail

    public void sendPasswordEmail(String cflEmail,String password) {

        // Set up the email content
        Context context = new Context();
        context.setVariable("cflName", cflEmail.split("_")[0]);
        context.setVariable("password", password);

        String process = templateEngine.process("CFLAccountCreationMailTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(cflEmail);
            helper.setSubject("Welcome To StartSmart");
            helper.setText(process, true);

            javaMailSender().send(mimeMessage);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }



    // sendPasswordEmail

    public void sendMentorPasswordEmail(String mentorEmail,String password,String role) {

        // Set up the email content
        Context context = new Context();
        context.setVariable("mentorName", mentorEmail.split("_")[0]);
        context.setVariable("password", password);
        context.setVariable("role",role);

        String process = templateEngine.process("MentorAccountCreationMailTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(mentorEmail);
            helper.setSubject("Welcome To StartSmart");
            helper.setText(process, true);

            javaMailSender().send(mimeMessage);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }




    // sendPasswordEmail

    public void sendManagerPasswordEmail(String managerEmail,String password,String role) {

        // Set up the email content
        Context context = new Context();
        context.setVariable("managerName", managerEmail.split("_")[0]);
        context.setVariable("password", password);
        context.setVariable("role", role);


        String process = templateEngine.process("ManagerAccountCreationMailTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(managerEmail);
            helper.setSubject("Welcome To StartSmart");
            helper.setText(process, true);

            javaMailSender().send(mimeMessage);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    // sendPasswordEmail

    public void sendHrPasswordEmail(String hrEmail,String password,String role) {

        // Set up the email content
        Context context = new Context();
        context.setVariable("hrName", hrEmail.split("_")[0]);
        context.setVariable("password", password);
        context.setVariable("role", role);


        String process = templateEngine.process("HrAccountCreationMailTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(hrEmail);
            helper.setSubject("Welcome To StartSmart");
            helper.setText(process, true);

            javaMailSender().send(mimeMessage);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }




    // approve mail



    public boolean sendApproveConfirmationToCfl(String cflName, String cflMail, String quarter,String managerName,String subject) {
        boolean isSent = true;

        // Set up the email content
        Context context = new Context();
        context.setVariable("cflName", cflName);
        context.setVariable("cflMail", cflMail);
        context.setVariable("quarter", quarter);
        context.setVariable("mgrName", managerName);


        String process = templateEngine.process("MailApproveForGoalSettingEmailTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(cflMail);
            helper.setSubject(subject);
            helper.setText(process, true);


            javaMailSender().send(mimeMessage);
            isSent = true;
        } catch (Exception e) {
            e.printStackTrace();
            isSent = false;
        }

        return isSent;
    }



    // managerRating Mail


    public boolean sendManagerRatingMailToCfl(String cflName, String cflMail,String subject, String quarter,String potential, String performance,String  talent,String managerName) {
        boolean isSent = true;

        // Set up the email content
        Context context = new Context();
        context.setVariable("cflName", cflName);
        context.setVariable("quarter", quarter);
        context.setVariable("potential", potential);
        context.setVariable("performance", performance);
        context.setVariable("talent", talent);
        context.setVariable("mgrName", managerName);


        String process = templateEngine.process("ManagerRatingToCflEmailTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(cflMail);
            helper.setSubject(subject);
            helper.setText(process, true);


            javaMailSender().send(mimeMessage);
            isSent = true;
        } catch (Exception e) {
            e.printStackTrace();
            isSent = false;
        }

        return isSent;
    }



    public void sendManagerRatingMailToHR(String hrMail, String cflName, String quarter, String potential, String performance, String  talent, String managerName) {

        System.out.println(hrMail+"hrMail");
        boolean isSent = true;

        // Set up the email content
        Context context = new Context();
        context.setVariable("hrMail", hrMail);
        context.setVariable("hrName", hrMail.split("_")[0]);
        context.setVariable("cflName", cflName);
        context.setVariable("quarter", quarter);
        context.setVariable("potential", potential);
        context.setVariable("performance", performance);
        context.setVariable("talent", talent);
        context.setVariable("mgrName", managerName);

        System.out.println("Called B 90");


        String process = templateEngine.process("ManagerRatingToHREmailTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(hrMail);
            helper.setSubject("Regarding Annual Appraisal");
            helper.setText(process, true);


            javaMailSender().send(mimeMessage);
//            isSent = true;
        } catch (Exception e) {
            e.printStackTrace();
//            isSent = false;
        }

    }

    public void sendManagerQuestionAnswerMailToHR(String hrMail, String cflName, String ans1, String ans2, String ans3, String  ans4, String ans5, String managerName) {

        System.out.println(hrMail+"hrMail");
        boolean isSent = true;

        // Set up the email content
        Context context = new Context();
        context.setVariable("hrMail", hrMail);
        context.setVariable("hrName", hrMail.split("_")[0]);
        context.setVariable("cflName", cflName);
        context.setVariable("ans1", ans1);
        context.setVariable("ans2", ans2);
        context.setVariable("ans3", ans3);
        context.setVariable("ans4", ans4);
        context.setVariable("ans5", ans5);

        context.setVariable("mgrName", managerName);



        String process = templateEngine.process("ManagerQuestionRatingToHREmailTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(hrMail);
            helper.setSubject("Regarding Annual Appraisal");
            helper.setText(process, true);


            javaMailSender().send(mimeMessage);
//            isSent = true;
        } catch (Exception e) {
            e.printStackTrace();
//            isSent = false;
        }

    }





    public void sendGoalSettingToManager(String managerEmail,String managerName, String cflName, String subject,String cflEmail,String hrEmail,String quarter) {
       // Set up the email content
        Context context = new Context();

        context.setVariable("mgrName",managerName);
        context.setVariable("cflName", cflName);
        context.setVariable("subject", subject);
        context.setVariable("cflEmail", cflEmail);
        context.setVariable("hrEmail", hrEmail);
        context.setVariable("managerEmail", managerEmail);
        context.setVariable("quarter",quarter);

        System.out.print(cflEmail+"cflEmail8888");

        if(quarter.substring(1,2).equals("1")){
            context.setVariable("times","first");
        }
        else if(quarter.substring(1,2).equals("2")){
            context.setVariable("times","second");
        }
        else if(quarter.substring(1,2).equals("3")){
            context.setVariable("times","third");
        }
        else if(quarter.substring(1,2).equals("4")){
            context.setVariable("times","fourth");
        }

        context.setVariable("link", "https://cal.com/startsmart/startsmart");

        String process = templateEngine.process("CflGoalSettingEmailTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(managerEmail);
            helper.setSubject(subject);
            helper.setText(process, true);

            javaMailSender().send(mimeMessage);
            System.out.println("Email successfully sent to " + managerEmail);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to send email to " + managerEmail + ": " + e.getMessage());
        }
    }




    // goal setting review to manager

    public void sendGoalSettingReviewToManager(String managerEmail, String cflName, String subject,String cflEmail,String hrEmail,String quarter) {
        // Set up the email content
        Context context = new Context();
        context.setVariable("cflName", cflName);
        context.setVariable("subject", subject);
        context.setVariable("cflEmail", cflEmail);
        context.setVariable("hrEmail", hrEmail);
        context.setVariable("quarter", quarter);
        context.setVariable("mgrName", managerEmail.split("_")[0]);
        if(quarter.substring(1,2).equals("1")){
            context.setVariable("times","first");
        }
        else if(quarter.substring(1,2).equals("2")){
            context.setVariable("times","second");
        }
        else if(quarter.substring(1,2).equals("3")){
            context.setVariable("times","third");
        }
        else if(quarter.substring(1,2).equals("4")){
            context.setVariable("times","fourth");
        }
        context.setVariable("link", "https://cal.com/startsmart/startsmart");

        String process = templateEngine.process("CflGoalSettingReviewEmailTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(managerEmail);
            helper.setSubject(subject);
            helper.setText(process, true);

            javaMailSender().send(mimeMessage);
            System.out.println("Email successfully sent to " + managerEmail);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to send email to " + managerEmail + ": " + e.getMessage());
        }
    }




    public void sendProbationStatusToManager(String managerEmail, String cflName, String subject,String cflEmail,String hrEmail) {


        // Define the past and current dates
        LocalDate pastDate = LocalDate.of(2025, 2, 4);  // Corrected instantiation of past date
        LocalDate todayDate = LocalDate.now();

        // Calculate the difference in days
        long agedDay = ChronoUnit.DAYS.between(pastDate, todayDate);

        System.out.println("Difference in days: " + agedDay);


        // Set up the email content
        Context context = new Context();
        context.setVariable("cflName", cflName);
        context.setVariable("subject", subject);
        context.setVariable("cflEmail", cflEmail);
        context.setVariable("hrEmail", hrEmail);
        context.setVariable("link", "https://cal.com/startsmart/startsmart");
        context.setVariable("managerName", managerEmail.split("_")[0]);
        context.setVariable("agedDay",agedDay);

        String process = templateEngine.process("ProbationConfirmationToManager.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(managerEmail);
            helper.setSubject(subject);
            helper.setText(process, true);


            javaMailSender().send(mimeMessage);
            System.out.println("Email successfully sent to " + managerEmail);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to send email to " + managerEmail + ": " + e.getMessage());
        }
    }



    // change request mail to cfl
    public void sendChangeRequestMailToCFL(String cflMail,String changeType) {

        String subject="Regarding "+changeType+" changes";

        // Set up the email content
        Context context = new Context();
        String cflName= cflMail.split("_")[0].substring(0,1).toUpperCase()+cflMail.split("_")[0].substring(1);
        context.setVariable("cflName", cflName);
        context.setVariable("subject", subject);
        context.setVariable("cflMail", cflMail);
        context.setVariable("changeType", changeType);

        String process = templateEngine.process("ChangeRequestMailToCFLEmailTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(cflMail);
            helper.setSubject(subject);
            helper.setText(process, true);

            javaMailSender().send(mimeMessage);
            System.out.println("Email successfully sent to " + cflMail);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to send email to " + cflMail + ": " + e.getMessage());
        }
    }







    // PROBATION EXTENSION FOR THREE MONTH ==========================
    public void sendProbationExtensionForThreeMonthToCFL(String cflEmail) {

        String subject="Regarding probation extension for three month";

        // Set up the email content
        Context context = new Context();
        String cflName= cflEmail.split("_")[0].substring(0,1).toUpperCase()+cflEmail.split("_")[0].substring(1);
        context.setVariable("cflName", cflName);
        context.setVariable("subject", subject);
        context.setVariable("cflEmail", cflEmail);

        String process = templateEngine.process("ProbationExtensionForThreeMonthCFLTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(cflEmail);
            helper.setSubject(subject);
            helper.setText(process, true);

            javaMailSender().send(mimeMessage);
            System.out.println("Email successfully sent to " + cflEmail);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to send email to " + cflEmail + ": " + e.getMessage());
        }
    }



    public void sendProbationExtensionForThreeMonthToHR(String hrEmail) {

        String subject="Regarding probation extension for three month";

        // Set up the email content
        Context context = new Context();
        String hrName= hrEmail.split("_")[0].substring(0,1).toUpperCase()+hrEmail.split("_")[0].substring(1);
        context.setVariable("hrName", hrName);
        context.setVariable("subject", subject);
        context.setVariable("hrEmail", hrEmail);

        String process = templateEngine.process("ProbationExtensionForThreeMonthHRTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(hrEmail);
            helper.setSubject(subject);
            helper.setText(process, true);
            javaMailSender().send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to send email to " + hrEmail + ": " + e.getMessage());
        }
    }



    public void sendProbationExtensionForThreeMonthToManager( String fromDateFormatted ,String toDateFormatted,String cflEmail,String managerMail) {

        String subject="Regarding probation extension for three month";

        Context context = new Context();
        String cflName= cflEmail.split("_")[0].substring(0,1).toUpperCase()+cflEmail.split("_")[0].substring(1);
        String managerName= managerMail.split("_")[0].substring(0,1).toUpperCase()+managerMail.split("_")[0].substring(1);

        context.setVariable("cflName", cflName);
        context.setVariable("subject", subject);
        context.setVariable("cflEmail", cflEmail);
        context.setVariable("managerName", managerName);
        context.setVariable("fromDate",fromDateFormatted);
        context.setVariable("toDate",toDateFormatted);

        String process = templateEngine.process("ProbationExtensionForThreeMonthManagerTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(managerMail);
            helper.setSubject(subject);
            helper.setText(process, true);
            javaMailSender().send(mimeMessage);
            System.out.println("Email successfully sent to " + managerMail);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to send email to " + managerMail + ": " + e.getMessage());
        }
    }



    public void sendProbationConfirmationFeedBackToManager(String cflEmail,String managerMail) {

        String subject="Regarding probation confirmation";

        Context context = new Context();
        String cflName= cflEmail.split("_")[0].substring(0,1).toUpperCase()+cflEmail.split("_")[0].substring(1);
        String managerName= managerMail.split("_")[0].substring(0,1).toUpperCase()+managerMail.split("_")[0].substring(1);

        context.setVariable("cflName", cflName);
        context.setVariable("subject", subject);
        context.setVariable("cflEmail", cflEmail);
        context.setVariable("managerName", managerName);

        String process = templateEngine.process("ProbationConfirmationFeedbackAfterManagerConfirmTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(managerMail);
            helper.setSubject(subject);
            helper.setText(process, true);
            javaMailSender().send(mimeMessage);
            System.out.println("Email successfully sent to " + managerMail);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to send email to " + managerMail + ": " + e.getMessage());
        }
    }







    // ==================== GOAL SETTING MAIL ==============================

    // accept goal setting ------------------------------------------------

    public void sendGoalSettingToCflAndHr(String managerName, String managerEmail, String cflName, String cflEmail, String hrEmail, LocalDate date,String quarter) {

        // Set up common variables for both emails
        Context context = new Context();
        context.setVariable("cflName", cflName);
        context.setVariable("cflEmail", cflEmail);
        context.setVariable("managerName", managerName);
        context.setVariable("date", date);
        context.setVariable("managerEmail", managerEmail);
        context.setVariable("quarter",quarter);
        if(quarter.substring(1,2).equals("1")){
            context.setVariable("times","first");
        }
        else if(quarter.substring(1,2).equals("2")){
            context.setVariable("times","second");
        }
        else if(quarter.substring(1,2).equals("3")){
            context.setVariable("times","third");
        }
        else if(quarter.substring(1,2).equals("4")){
            context.setVariable("times","fourth");
        }

        context.setVariable("link", "https://cal.com/startsmart/startsmart");

        try {
            // Send email to CFL using the first template
            String cflEmailContent = templateEngine.process("ManagerToCflGoalSettingEmailTemplate.html", context);
            MimeMessage mimeMessageCfl = javaMailSender().createMimeMessage();
            MimeMessageHelper helperCfl = new MimeMessageHelper(mimeMessageCfl, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helperCfl.setFrom(mailUsername);
            helperCfl.setTo(cflEmail);
            helperCfl.setSubject("Response From Manager");
            helperCfl.setText(cflEmailContent, true);
            javaMailSender().send(mimeMessageCfl);

            System.out.println("send email");

            // Modify the context or add specific variables for HR if needed
            context.setVariable("hrEmail", hrEmail);

            // Send email to HR using the second template
            String hrEmailContent = templateEngine.process("ManagerToHrGoalSettingEmailTemplate.html", context);
            MimeMessage mimeMessageHr = javaMailSender().createMimeMessage();
            MimeMessageHelper helperHr = new MimeMessageHelper(mimeMessageHr, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helperHr.setFrom(mailUsername);
            helperHr.setTo(hrEmail);
            helperHr.setSubject("Response From Manager");
            helperHr.setText(hrEmailContent, true);
           // helperHr.setCc(managerName); // Optional, CC manager on HR's email if required
            javaMailSender().send(mimeMessageHr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    // extend goal setting ----------------------------------------------


    public void sendGoalSettingExtendToCflAndHr(String managerName, String cflName, String cflEmail, String hrEmail, LocalDate date,String quarter) {

        // Set up common variables for both emails
        Context context = new Context();
        context.setVariable("cflName", cflName);
        context.setVariable("cflEmail", cflEmail);
        context.setVariable("managerName", managerName);
        context.setVariable("date", date.plusDays(7));
        context.setVariable("quarter", quarter);
        context.setVariable("hrName", hrEmail.split("_")[0]);
        if(quarter.substring(1,2).equals("1")){
            context.setVariable("times","first");
        }
        else if(quarter.substring(1,2).equals("2")){
            context.setVariable("times","second");
        }
        else if(quarter.substring(1,2).equals("3")){
            context.setVariable("times","third");
        }
        else if(quarter.substring(1,2).equals("4")){
            context.setVariable("times","fourth");
        }

        try {
            // Send email to CFL using the first template
            String cflEmailContent = templateEngine.process("ManagerToCflExtendGoalSettingEmailTemplate.html", context);
            MimeMessage mimeMessageCfl = javaMailSender().createMimeMessage();
            MimeMessageHelper helperCfl = new MimeMessageHelper(mimeMessageCfl, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helperCfl.setFrom(mailUsername);
            helperCfl.setTo(cflEmail);
            helperCfl.setSubject("Response From Manager");
            helperCfl.setText(cflEmailContent, true);
            javaMailSender().send(mimeMessageCfl);



            // Send email to HR using the second template
            String hrEmailContent = templateEngine.process("ManagerToHrExtendGoalSettingEmailTemplate.html", context);
            MimeMessage mimeMessageHr = javaMailSender().createMimeMessage();
            MimeMessageHelper helperHr = new MimeMessageHelper(mimeMessageHr, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helperHr.setFrom(mailUsername);
            helperHr.setTo(hrEmail);
            helperHr.setSubject("Response From Manager");
            helperHr.setText(hrEmailContent, true);
            // helperHr.setCc(managerName); // Optional, CC manager on HR's email if required
            javaMailSender().send(mimeMessageHr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    // ==================== GOAL SETTING REVIEW MAIL ==============================

    // accept goal setting review ------------------------------------------------


    public void sendGoalSettingReviewToCflAndHr(String managerName, String managerEmail, String cflName, String cflEmail, String hrEmail, LocalDate date,String quarter) {

        // Set up common variables for both emails
        Context context = new Context();
        context.setVariable("cflName", cflName);
        context.setVariable("cflEmail", cflEmail);
        context.setVariable("managerName", managerName);
        context.setVariable("date", date);
        context.setVariable("managerEmail", managerEmail);
        context.setVariable("quarter", quarter);
        if(quarter.substring(1,2).equals("1")){
            context.setVariable("times","first");
        }
        else if(quarter.substring(1,2).equals("2")){
            context.setVariable("times","second");
        }
        else if(quarter.substring(1,2).equals("3")){
            context.setVariable("times","third");
        }
        else if(quarter.substring(1,2).equals("4")){
            context.setVariable("times","fourth");
        }


        context.setVariable("link", "https://cal.com/startsmart/startsmart");

        try {
            // Send email to CFL using the first template
            String cflEmailContent = templateEngine.process("ManagerToCflGoalSettingReviewEmailTemplate.html", context);
            MimeMessage mimeMessageCfl = javaMailSender().createMimeMessage();
            MimeMessageHelper helperCfl = new MimeMessageHelper(mimeMessageCfl, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helperCfl.setFrom(mailUsername);
            helperCfl.setTo(cflEmail);
            helperCfl.setSubject("Response From Manager");
            helperCfl.setText(cflEmailContent, true);
            javaMailSender().send(mimeMessageCfl);

            // Modify the context or add specific variables for HR if needed
            context.setVariable("hrEmail", hrEmail);

            // Send email to HR using the second template
            String hrEmailContent = templateEngine.process("ManagerToHrGoalSettingReviewEmailTemplate.html", context);
            MimeMessage mimeMessageHr = javaMailSender().createMimeMessage();
            MimeMessageHelper helperHr = new MimeMessageHelper(mimeMessageHr, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helperHr.setFrom(mailUsername);
            helperHr.setTo(hrEmail);
            helperHr.setSubject("Response From Manager");
            helperHr.setText(hrEmailContent, true);
            // helperHr.setCc(managerName); // Optional, CC manager on HR's email if required
            javaMailSender().send(mimeMessageHr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    // extend goal setting review ----------------------------------------------

    public void sendGoalSettingExtendReviewToCflAndHr(String managerName, String cflName, String cflEmail, String hrEmail, LocalDate date,String quarter) {

        // Set up common variables for both emails
        Context context = new Context();
        context.setVariable("cflName", cflName);
        context.setVariable("cflEmail", cflEmail);
        context.setVariable("managerName", managerName);
        context.setVariable("date", date.plusDays(7));
        context.setVariable("quarter", quarter);
        try {
            // Send email to CFL using the first template
            String cflEmailContent = templateEngine.process("ManagerToCflExtendGoalSettingReviewEmailTemplate.html", context);
            MimeMessage mimeMessageCfl = javaMailSender().createMimeMessage();
            MimeMessageHelper helperCfl = new MimeMessageHelper(mimeMessageCfl, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helperCfl.setFrom(mailUsername);
            helperCfl.setTo(cflEmail);
            helperCfl.setSubject("Response From Manager");
            helperCfl.setText(cflEmailContent, true);
            javaMailSender().send(mimeMessageCfl);



            // Send email to HR using the second template
            String hrEmailContent = templateEngine.process("ManagerToHrExtendGoalSettingReviewEmailTemplate.html", context);
            MimeMessage mimeMessageHr = javaMailSender().createMimeMessage();
            MimeMessageHelper helperHr = new MimeMessageHelper(mimeMessageHr, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            helperHr.setFrom(mailUsername);
            helperHr.setTo(hrEmail);
            helperHr.setSubject("Response From Manager");
            helperHr.setText(hrEmailContent, true);
            // helperHr.setCc(managerName); // Optional, CC manager on HR's email if required
            javaMailSender().send(mimeMessageHr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//     PROBATION REQUEST MAIL =====================================================


    // accept probation request ------------------------------------------------

    public void sendProbationToCflAndHr(String managerName,String cflName, String cflEmail, String hrEmail, LocalDate date) {

        // Set up common variables for both emails
        Context context = new Context();
        context.setVariable("cflName", cflName);
        context.setVariable("cflEmail", cflEmail);
        context.setVariable("managerName", managerName);
        context.setVariable("date", date);
        context.setVariable("hrName", hrEmail.split("_")[0]);


        try {
            // For Manager To Cfl ---------------------------------------
            // Send email to CFL using the first template
            String cflEmailContent = templateEngine.process("ManagerToCflProbationEmailTemplate.html", context);
            MimeMessage mimeMessageCfl = javaMailSender().createMimeMessage();
            MimeMessageHelper helperCfl = new MimeMessageHelper(mimeMessageCfl, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helperCfl.setFrom(mailUsername);
            helperCfl.setTo(cflEmail);
            helperCfl.setSubject("Response From Manager");
            helperCfl.setText(cflEmailContent, true);
            javaMailSender().send(mimeMessageCfl);

            // For Manager To HR ---------------------------------------

            // Modify the context or add specific variables for HR if needed
            context.setVariable("hrEmail", hrEmail);
            context.setVariable("hrName", hrEmail.split("_")[0]);

            // Send email to HR using the second template
            String hrEmailContent = templateEngine.process("ManagerToHrProbationEmailTemplate.html", context);
            MimeMessage mimeMessageHr = javaMailSender().createMimeMessage();
            MimeMessageHelper helperHr = new MimeMessageHelper(mimeMessageHr, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helperHr.setFrom(mailUsername);
            helperHr.setTo(hrEmail);
            helperHr.setSubject("Response From Manager");
            helperHr.setText(hrEmailContent, true);
            // helperHr.setCc(managerName); // Optional, CC manager on HR's email if required
            javaMailSender().send(mimeMessageHr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // extend goal setting ----------------------------------------------

    public void sendProbationExtendToCflAndHr(String managerName, String cflName, String cflEmail, String hrEmail, LocalDate date) {

        // Set up common variables for both emails
        Context context = new Context();
        context.setVariable("cflName", cflName);
        context.setVariable("cflEmail", cflEmail);
        context.setVariable("managerName", managerName);
        context.setVariable("hrName", hrEmail.split("_")[0]);
        context.setVariable("date", date.plusMonths(3));
        try {
            // Send email to CFL using the first template
            String cflEmailContent = templateEngine.process("ManagerToCflProbationExtendEmailTemplate.html", context);
            MimeMessage mimeMessageCfl = javaMailSender().createMimeMessage();
            MimeMessageHelper helperCfl = new MimeMessageHelper(mimeMessageCfl, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helperCfl.setFrom(mailUsername);
            helperCfl.setTo(cflEmail);
            helperCfl.setSubject("Response From Manager");
            helperCfl.setText(cflEmailContent, true);
            javaMailSender().send(mimeMessageCfl);

            // Send email to HR using the second template
            String hrEmailContent = templateEngine.process("ManagerToHrProbationExtendEmailTemplate.html", context);
            MimeMessage mimeMessageHr = javaMailSender().createMimeMessage();
            MimeMessageHelper helperHr = new MimeMessageHelper(mimeMessageHr, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helperHr.setFrom(mailUsername);
            helperHr.setTo(hrEmail);
            helperHr.setSubject("Response From Manager");
            helperHr.setText(hrEmailContent, true);
            // helperHr.setCc(managerName); // Optional, CC manager on HR's email if required
            javaMailSender().send(mimeMessageHr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // =======================  send list of mentoring log book =======================

    public void sendMentoringLogBook(List<String> listOfEmails) {

        // Set up common variables for both emails
        Context context = new Context();
        LocalDate date=LocalDate.now();
        context.setVariable("date", date);
        try {
            // Send email to CFL using the first template
            String cflEmailContent = templateEngine.process("MentoringLogBookToAllCflEmailTemplate.html", context);
            MimeMessage mimeMessageCfl = javaMailSender().createMimeMessage();
            MimeMessageHelper helperCfl = new MimeMessageHelper(mimeMessageCfl, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helperCfl.setFrom(mailUsername);
            helperCfl.setTo(listOfEmails.remove(0));
            helperCfl.setSubject("Response From Manager");
            helperCfl.setText(cflEmailContent, true);

            // cc
            String []ccList=listOfEmails.stream().toArray(String[]::new);
            helperCfl.setCc(ccList);
            javaMailSender().send(mimeMessageCfl);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // ====================== ANNUAL APPRAISAL ============================


//    public void sendEmailRegardingAnnualAppraisal(List<String> listOfEmails) {
//
//        // Check if the email list is not empty
//        if (listOfEmails == null || listOfEmails.isEmpty()) {
//            System.out.println("No email addresses provided.");
//            return;
//        }
//
//        // Set up common variables for both emails
//        Context context = new Context();
//        LocalDate date = LocalDate.now();
//        context.setVariable("year", date.getYear());
//
//        try {
//            // Send email to CFL using the first template
//            String cflEmailContent = templateEngine.process("AnnualAppraisalDueAllManagerEmailTemplate.html", context);
//            MimeMessage mimeMessageCfl = javaMailSender().createMimeMessage();
//            MimeMessageHelper helperCfl = new MimeMessageHelper(mimeMessageCfl, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
//            helperCfl.setFrom(mailUsername);
//            // Safely remove and send to the first email
//            String firstEmail = listOfEmails.remove(0);  // Remove only if the list is non-empty
//            helperCfl.setTo(firstEmail);
//            helperCfl.setSubject("Regarding Annual Appraisal Due");
//            helperCfl.setText(cflEmailContent, true);
//
//            // Set remaining emails as CC, if any
//            if (!listOfEmails.isEmpty()) {
//                String[] ccList = listOfEmails.toArray(new String[0]);
//                helperCfl.setCc(ccList);
//            }
//
//            // Send the email
//            javaMailSender().send(mimeMessageCfl);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//



    public void sendEmailRegardingAnnualAppraisal(List<String> listOfEmails) {

        // Check if the email list is not empty
        if (listOfEmails == null || listOfEmails.isEmpty()) {
            System.out.println("No email addresses provided.");
            return;
        }

        // Set up common variables for both emails
        Context context = new Context();
        LocalDate date = LocalDate.now();
        context.setVariable("year", date.getYear()-1);

        try {
            // Send email to CFL using the first template
            String cflEmailContent = templateEngine.process("CFLAnnualAppraisalDueTemplate.html", context);
            MimeMessage mimeMessageCfl = javaMailSender().createMimeMessage();
            MimeMessageHelper helperCfl = new MimeMessageHelper(mimeMessageCfl, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helperCfl.setFrom(mailUsername);
            // Safely remove and send to the first email
            String firstEmail = listOfEmails.remove(0);  // Remove only if the list is non-empty
            helperCfl.setTo(firstEmail);
            helperCfl.setSubject("Regarding Annual Appraisal Due");
            helperCfl.setText(cflEmailContent, true);

            // Set remaining emails as CC, if any
            if (!listOfEmails.isEmpty()) {
                String[] ccList = listOfEmails.toArray(new String[0]);
                helperCfl.setCc(ccList);
            }

            // Send the email
            javaMailSender().send(mimeMessageCfl);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }







    public void sendMailToManagerRegardingCFLRatingForAnnualAppraisal(String cflEmail, String managerName, String managerMail,String hrMail,String cflKeyAccomplishment) {


        System.out.println("email called");

        // cfl to manager ----------------------------

        // Set up common variables for both emails
        Context context = new Context();
        context.setVariable("cflName", cflEmail.split("_")[0]);
        context.setVariable("cflEmail", cflEmail);
        context.setVariable("managerName", managerName);
        context.setVariable("managerMail", managerMail);
        context.setVariable("key",cflKeyAccomplishment);
        context.setVariable("hrName",hrMail.split("_")[0]);
        context.setVariable("hrMail",hrMail);







        try {
            // For Manager From Cfl ---------------------------------------
            String cflEmailContent = templateEngine.process("AnnualAppraisalDueAllManagerEmailTemplate.html", context);
            MimeMessage mimeMessageCfl = javaMailSender().createMimeMessage();
            MimeMessageHelper helperCfl = new MimeMessageHelper(mimeMessageCfl, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helperCfl.setFrom(mailUsername);
            helperCfl.setTo(managerMail);
            helperCfl.setSubject("Regarding cfl annual appraisal");
            helperCfl.setText(cflEmailContent, true);
            javaMailSender().send(mimeMessageCfl);



            // For HR---------------------------------------
            String hrEmailContent = templateEngine.process("AnnualAppraisalToHR.html", context);
            MimeMessage mimeMessageHR = javaMailSender().createMimeMessage();
            MimeMessageHelper helperHR = new MimeMessageHelper(mimeMessageHR, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helperHR.setFrom(mailUsername);
            helperHR.setTo(hrMail);
            helperHR.setSubject("Regarding cfl annual appraisal");
            helperHR.setText(hrEmailContent, true);
            javaMailSender().send(mimeMessageCfl);


            // For Cfl ---------------------------------------
            String CFLEmailContent = templateEngine.process("AnnualAppraisalToCFL.html", context);
            MimeMessage mimeMessageCFL = javaMailSender().createMimeMessage();
            MimeMessageHelper helperCFL = new MimeMessageHelper(mimeMessageCFL, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helperCFL.setFrom(mailUsername);
            helperCFL.setTo(cflEmail);
            helperCFL.setSubject("Regarding your annual appraisal");
            helperCFL.setText(CFLEmailContent, true);
            javaMailSender().send(mimeMessageCfl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    // send mail back to manager for extend goal setting confirmation

    public void sendExtendMailBackToMgr(String managerName,String cflName,  String managerEmail, LocalDate date) {
        // Set up the email content
        Context context = new Context();
        context.setVariable("cflName", cflName);
        context.setVariable("managerName", managerName);
        context.setVariable("date", date.plusDays(7));

        String process = templateEngine.process("GoalSettingExtensionConfirmationToManager.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(managerEmail);
            helper.setSubject("Regarding Goal Setting Extension");
            helper.setText(process, true);
            javaMailSender().send(mimeMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // send mail back to manager for extend goal setting review confirmation

    public void sendExtendGoalSettingReviewMailBackToMgr(String managerName,String cflName,  String managerEmail, LocalDate date) {
        // Set up the email content
        Context context = new Context();
        context.setVariable("cflName", cflName);
        context.setVariable("managerName", managerName);
        context.setVariable("date", date.plusDays(7));
        String process = templateEngine.process("GoalSettingReviewExtensionConfirmationToManager.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(managerEmail);
            helper.setSubject("Regarding Goal Setting Extension");
            helper.setText(process, true);
            javaMailSender().send(mimeMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // send mail back to manager for extend probation confirmation

    public void  sendExtendProbationMailBackToMgr(String managerName,String cflName,  String managerEmail, LocalDate date) {
        // Set up the email content
        Context context = new Context();
        context.setVariable("cflName", cflName);
        context.setVariable("managerName", managerName);
        context.setVariable("date", date.plusDays(7));
        String process = templateEngine.process("ProbationExtensionConfirmationToManager.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(managerEmail);
            helper.setSubject("Regarding Goal Setting Extension");
            helper.setText(process, true);
            javaMailSender().send(mimeMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // send otp mail
    public void sendOtpEmail(String email,String otp) {
        // Set up the email content
        Context context = new Context();
        context.setVariable("otp", otp);
        context.setVariable("cflName", email.split("_")[0]);
        String process = templateEngine.process("OtpGeneratorEmailTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            helper.setFrom(mailUsername);
            helper.setTo(email);
            helper.setSubject("OTP Regarding Forgot Password");
            helper.setText(process, true);
            javaMailSender().send(mimeMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // ======================== ACCEPT EXIT REQUEST FROM CFL =======================


    // exit request

    // confirm   cfl => manager

    public void sendExitRequestToManager(String managerName,String cflName, String managerEmail) {
        // Set up the email content
        Context context = new Context();
        context.setVariable("cflName", cflName);
        context.setVariable("managerName", managerName);
        LocalDate todayDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = todayDate.format(formatter);
        context.setVariable("date", formattedDate);

        String process = templateEngine.process("CFLToManagerExitMailTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(managerEmail);
            helper.setSubject("Regarding CFL Exit");
            helper.setText(process, true);
            javaMailSender().send(mimeMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // confirm   cfl => hr

    public void sendExitRequestToHR(String hrName,String cflName, String hrEmail) {
        // Set up the email content
        Context context = new Context();
        context.setVariable("cflName", cflName);
        context.setVariable("hrName", hrName);
        LocalDate todayDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = todayDate.format(formatter);
        context.setVariable("date", formattedDate);

        String process = templateEngine.process("CFLToHRExitMailTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(hrEmail);
            helper.setSubject("Regarding CFL Exit");
            helper.setText(process, true);
            javaMailSender().send(mimeMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // ======================== WITHDRAWAL EXIT REQUEST FROM CFL =======================


// withdrawal   cfl => manager

    public void sendExitWithdrawalRequestToManager(String managerName,String cflName, String managerEmail) {
        // Set up the email content
        Context context = new Context();
        context.setVariable("cflName", cflName);
        context.setVariable("managerName", managerName);
        LocalDate todayDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = todayDate.format(formatter);
        context.setVariable("date", formattedDate);

        String process = templateEngine.process("CflToManagerExitWithdrawalMailTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(managerEmail);
            helper.setSubject("Regarding CFL Exit Withdrawal");
            helper.setText(process, true);
            javaMailSender().send(mimeMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // withdrawal   cfl => hr

    public void sendExitWithdrawalRequestToHR(String hrName,String cflName, String hrEmail) {
        // Set up the email content
        Context context = new Context();
        context.setVariable("cflName", cflName);
        context.setVariable("hrName", hrName);
        LocalDate todayDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = todayDate.format(formatter);
        context.setVariable("date", formattedDate);

        String process = templateEngine.process("CflToHRExitWithdrawalMailTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(hrEmail);
            helper.setSubject("Regarding CFL Exit Withdrawal");
            helper.setText(process, true);
            javaMailSender().send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // =========================  FROM MANAGER ===========================

    // ======================== ACCEPT EXIT REQUEST =======================

    // Exit request accepted

    // for hr
    public void sendExitRequestAcceptedMailToHR(String hrName,String cflName, String hrEmail,String mgrName) {
        // Set up the email content
        Context context = new Context();
        context.setVariable("cflName", cflName);
        context.setVariable("hrName", hrName);
        context.setVariable("mgrName", mgrName);
        LocalDate todayDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = todayDate.format(formatter);
        context.setVariable("date", formattedDate);
        LocalDate RelievingDate = todayDate.plusMonths(3);
        DateTimeFormatter relievingFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String relievingFormattedDate = RelievingDate.format(relievingFormatter);
        context.setVariable("relievingFormattedDate", relievingFormattedDate);



        String process = templateEngine.process("HRAcceptCFLExitMailTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(hrEmail);
            helper.setSubject("Regarding CFL Exit Acceptance");
            helper.setText(process, true);
            javaMailSender().send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    // for manager
    public void sendExitRequestAcceptedMailToManager(String mgrName,String cflName, String mgrEmail) {
        // Set up the email content
        Context context = new Context();
        context.setVariable("cflName", cflName);
        context.setVariable("mgrName", mgrName);
        context.setVariable("mgrEmail", mgrEmail);

        LocalDate todayDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = todayDate.format(formatter);
        context.setVariable("date", formattedDate);

        LocalDate RelievingDate = todayDate.plusMonths(3);
        DateTimeFormatter relievingFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String relievingFormattedDate = RelievingDate.format(relievingFormatter);
        context.setVariable("relievingFormattedDate", relievingFormattedDate);


        String process = templateEngine.process("ManagerReceiveCFLExitMailTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(mgrEmail);
            helper.setSubject("Regarding CFL Exit Acceptance");
            helper.setText(process, true);
            javaMailSender().send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // for cfl

    public void sendExitRequestAcceptedMailToCFL(String cflMail,String cflName, String mgrName) {
        // Set up the email content
        Context context = new Context();
        context.setVariable("cflMail", cflMail);
        context.setVariable("cflName", cflName);
        context.setVariable("mgrName", mgrName);
        LocalDate todayDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = todayDate.format(formatter);
        context.setVariable("date", formattedDate);

        LocalDate RelievingDate = todayDate.plusMonths(3);
        DateTimeFormatter relievingFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String relievingFormattedDate = RelievingDate.format(relievingFormatter);
        context.setVariable("relievingFormattedDate", relievingFormattedDate);


        String process = templateEngine.process("ManagerAcceptCFLExitMailTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(cflMail);
            helper.setSubject("Regarding CFL Exit Acceptance");
            helper.setText(process, true);
            javaMailSender().send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // ======================== DECLINE EXIT REQUEST =======================



    // Exit request declined

    // for hr
    public void sendExitRequestDeclinedMailToHR(String hrName,String cflName, String hrEmail,String mgrName,String declineReason) {
        // Set up the email content
        Context context = new Context();
        context.setVariable("cflName", cflName);
        context.setVariable("hrName", hrName);
        context.setVariable("mgrName", mgrName);
        context.setVariable("declineReason", declineReason);

        LocalDate todayDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = todayDate.format(formatter);
        context.setVariable("date", formattedDate);


        String process = templateEngine.process("HRDeclineCFLExitMailTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(hrEmail);
            helper.setSubject("Regarding CFL Exit Declined");
            helper.setText(process, true);
            javaMailSender().send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    // for manager
    public void sendExitRequestDeclinedMailToManager(String mgrName,String cflName, String mgrEmail,String declineReason) {
        // Set up the email content
        Context context = new Context();
        context.setVariable("cflName", cflName);
        context.setVariable("mgrName", mgrName);
        context.setVariable("mgrEmail", mgrEmail);
        context.setVariable("declineReason", declineReason);


        LocalDate todayDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = todayDate.format(formatter);
        context.setVariable("date", formattedDate);


        String process = templateEngine.process("ManagerReceiveCFLExitDeclinedMailTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(mgrEmail);
            helper.setSubject("Regarding CFL Exit Declined");
            helper.setText(process, true);
            javaMailSender().send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // for cfl

    public void sendExitRequestDeclinedMailToCFL(String cflMail,String cflName, String mgrName, String declineReason) {
        // Set up the email content
        Context context = new Context();
        context.setVariable("cflMail", cflMail);
        context.setVariable("cflName", cflName);
        context.setVariable("mgrName", mgrName);
        context.setVariable("declineReason", declineReason);

        LocalDate todayDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = todayDate.format(formatter);
        context.setVariable("date", formattedDate);


        String process = templateEngine.process("ManagerDeclineCFLExitMailTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(cflMail);
            helper.setSubject("Regarding CFL Exit Declined");
            helper.setText(process, true);
            javaMailSender().send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ======================== EXTEND EXIT REQUEST =======================




    // Exit request declined

    // for hr
    public void sendExitRequestExtendedMailToHR(String hrName,String cflName, String hrEmail,String mgrName,String extendReason,String extendDate) {
        // Set up the email content
        Context context = new Context();
        context.setVariable("cflName", cflName);
        context.setVariable("hrName", hrName);
        context.setVariable("mgrName", mgrName);
        context.setVariable("extendReason", extendReason);
        context.setVariable("extendDate", extendDate);


        LocalDate todayDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = todayDate.format(formatter);
        context.setVariable("date", formattedDate);


        String process = templateEngine.process("HRExtendCFLExitMailTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(hrEmail);
            helper.setSubject("Regarding CFL Exit Extended");
            helper.setText(process, true);
            javaMailSender().send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    // for manager
    public void sendExitRequestExtendedMailToManager(String mgrName,String cflName, String mgrEmail,String extendReason,String extendDate) {
        // Set up the email content
        Context context = new Context();
        context.setVariable("cflName", cflName);
        context.setVariable("mgrName", mgrName);
        context.setVariable("mgrEmail", mgrEmail);
        context.setVariable("extendReason", extendReason);
        context.setVariable("extendDate", extendDate);



        LocalDate todayDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = todayDate.format(formatter);
        context.setVariable("date", formattedDate);


        String process = templateEngine.process("ManagerReceiveCFLExitDeclinedMailTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(mgrEmail);
            helper.setSubject("Regarding CFL Exit Extended");
            helper.setText(process, true);
            javaMailSender().send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // for cfl

    public void sendExitRequestExtendedMailToCFL(String cflMail,String cflName, String mgrName, String extendReason,String extendDate) {
        // Set up the email content
        Context context = new Context();
        context.setVariable("cflMail", cflMail);
        context.setVariable("cflName", cflName);
        context.setVariable("mgrName", mgrName);
        context.setVariable("extendReason", extendReason);
        context.setVariable("extendDate", extendDate);


        LocalDate todayDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = todayDate.format(formatter);
        context.setVariable("date", formattedDate);


        String process = templateEngine.process("ManagerDeclineCFLExitMailTemplate.html", context);

        try {
            MimeMessage mimeMessage = javaMailSender().createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            helper.setFrom(mailUsername);
            helper.setTo(cflMail);
            helper.setSubject("Regarding CFL Exit Extended");
            helper.setText(process, true);
            javaMailSender().send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
