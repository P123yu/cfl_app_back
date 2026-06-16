package com.cfl.cfl_project.controller;

import com.cfl.cfl_project.dto.CompleteCflTableDTO;
import com.cfl.cfl_project.model.Cfl;
import com.cfl.cfl_project.model.MailHistory;
import com.cfl.cfl_project.model.Register;
import com.cfl.cfl_project.service.CflService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/cfl")
@CrossOrigin("*")
public class CflController {

    @Autowired
    private CflService cflService;

    @PostMapping("/create")
    public ResponseEntity<?> createCfl(@RequestBody Cfl cfl) {
       Cfl cflObj=cflService.createCfl(cfl);
       if(cflObj !=null){
           return ResponseEntity.ok(cflObj);
       }
       else{
           return ResponseEntity.status(400).body("Failed to create Cfl");
       }
    }

    @PostMapping("/createAll")
    public ResponseEntity<?> createListOfCfl(@RequestBody List<Cfl> cfl) {
        System.out.println(cfl);
        List<Cfl> cflList=cflService.createListOfCfl(cfl);
        if(!cflList.isEmpty()){
            return ResponseEntity.ok(cflList);
        }
        else{
            return ResponseEntity.status(400).body("Failed to create list of Cfl");
        }
    }


    @GetMapping("/getCflInfoDuringLogin/{cflEmail}")
    public ResponseEntity<?>getCflInfoDuringLogin(@PathVariable String cflEmail){
        Cfl cfl=cflService.getCflByEmailDuringLogin(cflEmail);
        if(cfl!=null){
            return ResponseEntity.ok(cfl);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Cfl found with this login");
        }
    }


    @GetMapping("/getMentorEmail/{mentorEmail}")
    public ResponseEntity<?>getMentorNameByMentorEmail(@PathVariable String mentorEmail){
        String mentorName=cflService.getMentorNameByMentorEmail(mentorEmail);
        if(!mentorName.isEmpty()){
            return ResponseEntity.ok(mentorName);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No mentorName found");
        }
    }



    @GetMapping("/getHrEmail/{hrEmail}")
    public ResponseEntity<?>getAllByHrEmail(@PathVariable String hrEmail){
        List<Cfl>cflList=cflService.getAllByHrEmail(hrEmail);
        if(!cflList.isEmpty()){
            return ResponseEntity.ok(cflList);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No cflList found");
        }
    }



    @GetMapping("/getManagerEmail/{managerEmail}")
    public ResponseEntity<?>getManagerNameByManagerEmail(@PathVariable String managerEmail){
        String managerName=cflService.getManagerNameByManagerEmail(managerEmail);
        if(!managerName.isEmpty()){
            return ResponseEntity.ok(managerName);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No managerName found");
        }
    }



    @GetMapping("/getAllCflByProbationStatusAndYear/{probationStatus}/{year}")
    public ResponseEntity<?> getAllCflByProbationStatusAndYear(@PathVariable Boolean probationStatus,@PathVariable String year) {
        List<Cfl> cflList=cflService.getAllCflByProbationStatusAndYear(probationStatus,year);
        if(!cflList.isEmpty()){
            return ResponseEntity.ok(cflList);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }




    @GetMapping("/getAllByYear/{year}")
    public ResponseEntity<?> getAllByYear(@PathVariable String year) {
        List<Cfl> allCfl = cflService.getAllByYear(year);
        if (!allCfl.isEmpty()) {
            return ResponseEntity.ok(allCfl);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Cfl found");
        }
    }


    @PostMapping("/sendMail")
    public ResponseEntity<?> sentMailToSenior(
            @RequestParam Long empId,
            @RequestParam String email,
            @RequestParam String ccEmail,
            @RequestParam String subject,
            @RequestParam String message,
            @RequestParam String type,
            @RequestParam(required = false) String meetingDate,
            @RequestParam(required = false) String meetingTime) {
        Boolean result=cflService.sentMailToSenior(empId,email,ccEmail,subject, message, type, meetingDate, meetingTime);

        if(result){
            return ResponseEntity.ok("Email sent successfully !!!");
        }
        else{
            return ResponseEntity.badRequest().body("Email Not sent !!!");
        }
    }


    @GetMapping("/getByMailHistoryByEmpId/{empId}")
    public ResponseEntity<?> getByMailHistoryByEmpId(@PathVariable Long empId) {
        List<MailHistory> mailHistoryList=cflService.getByMailHistoryByEmpId(empId);
        if(mailHistoryList !=null){
            return ResponseEntity.ok(mailHistoryList);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Mail History found for this Employee");
        }
    }


    @GetMapping("/getAllCflByManagerEmail/{managerEmail}")
    public ResponseEntity<?> getAllCflByManagerEmail(@PathVariable String managerEmail) {
        List<Cfl> allCfl = cflService.getAllByManagerEmail(managerEmail);
        if (!allCfl.isEmpty()) {
            return ResponseEntity.ok(allCfl);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Cfl found");
        }
    }


    @GetMapping("/getAllCflByMentorEmail/{mentorEmail}")
    public ResponseEntity<?> getAllCflByMentorEmail(@PathVariable String mentorEmail) {
        List<Cfl> allCfl = cflService.getAllByMentorEmail(mentorEmail);
        if (!allCfl.isEmpty()) {
            return ResponseEntity.ok(allCfl);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Cfl found");
        }
    }


    @GetMapping("/getAllCflByCflId/{empId}")
    public ResponseEntity<?> getCflByEmpId(@PathVariable Long empId) {
        Cfl perticularCfl = cflService.getCflByEmpId(empId);
        if (perticularCfl!=null) {
            return ResponseEntity.ok(perticularCfl);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Cfl found");
        }
    }

    //  Mentor Mentee Request ----------------------------------------------------

    // for sending mentor-mentee accept response By mentor
    @GetMapping("/accept/{cflEmail}/{type}")
    public ResponseEntity<?> acceptRequest(
            @PathVariable String cflEmail,
            @PathVariable String type,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String time,
            @RequestParam(required = false) String seniorEmail) {

        boolean result=cflService.getByCflEmail(cflEmail, type, date, time, seniorEmail);
        if(result){
            return ResponseEntity.ok("<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "    <title>Meeting Confirmed</title>\n" +
                    "    <style>\n" +
                    "        body { font-family: Arial, sans-serif; text-align: center; padding: 50px; background-color: #f4f4f9; }\n" +
                    "        .card { background: white; padding: 40px; border-radius: 8px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); display: inline-block; max-width: 500px; }\n" +
                    "        h1 { color: #2e7d32; margin-top: 0; }\n" +
                    "        p { font-size: 16px; color: #555; line-height: 1.5; }\n" +
                    "        .info { background-color: #e8f5e9; padding: 15px; border-radius: 5px; margin-top: 20px; font-weight: bold; color: #1b5e20; }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"card\">\n" +
                    "        <h1>Meeting Confirmed Successfully!</h1>\n" +
                    "        <p>You have successfully accepted the mentoring session request.</p>\n" +
                    "        <p>A Google Meet calendar invitation has been generated and sent to both you and the student.</p>\n" +
                    "        <div class=\"info\">\n" +
                    "            Scheduled Date: " + (date != null ? date : "N/A") + "<br/>\n" +
                    "            Scheduled Time: " + (time != null ? time : "N/A") + "\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Cfl found");
        }
    }


    // for sending mentor-mentee accept response By mentor
    @GetMapping("/{cflEmail}")
    public ResponseEntity<?> getByCflInfoByCflEmail(@PathVariable String cflEmail) {
        Cfl cfl=cflService.getByCflInfoByCflEmail(cflEmail);
        if(cfl!=null){
            return ResponseEntity.ok(cfl);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Cfl found");
        }

    }



    // for sending mentor-mentee extend response By mentor
    @GetMapping("/extend/{cflEmail}/{type}")
    public ResponseEntity<?>  declineRequest(@PathVariable String cflEmail,@PathVariable String type) {
        boolean result=cflService.getByCflDeclinedEmail(cflEmail,type);
        if(result){
            return ResponseEntity.ok("E-Mail Request Is Extended Successfully");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Cfl found");
        }
    }


    // Goal Setting Request -----------------------------------------------

    @GetMapping("/manager/accept/{cflEmail}")
    public String acceptGoalSettingRequest(@PathVariable String cflEmail) {
        cflService.sendMailFromManagerToCFLAndHr(cflEmail);
        return "<html><body>" +
                "<script type=\"text/javascript\">" +
                "window.open('https://cal.com/startsmart/startsmart', '_blank');" +
               // "window.close();" +  // Close the current window (optional)
                "</script>" +
                "</body></html>";
    }


    // for sending goal setting extend response By manager
    @GetMapping("/manager/extend/{cflEmail}")
    public String extendGoalSettingRequest(@PathVariable String cflEmail) {
        cflService.sendExtendMailFromManagerToCFLAndHr(cflEmail);
        return "Goal Setting Request Extended Successfully";
    }



    // Goal Setting Review Request -----------------------------------------------

    // for sending goal setting accept response By manager
    @GetMapping("/managerReview/accept/{cflEmail}")
    public String acceptGoalSettingReviewRequest(@PathVariable String cflEmail) {
        cflService.sendReviewMailFromManagerToCFLAndHr(cflEmail);
//        return "Goal Setting Review Request Accepted Successfully";
        return "<html><body>" +
                "<script type=\"text/javascript\">" +
                "window.open('https://cal.com/startsmart/startsmart', '_blank');" +
                "</script>" +
                "</body></html>";
    }


    // for sending goal setting extend response By manager
    @GetMapping("/managerReview/extend/{cflEmail}")
    public String extendGoalSettingReviewRequest(@PathVariable String cflEmail) {
        cflService.sendExtendReviewMailFromManagerToCFLAndHr(cflEmail);
        return "Goal Setting Review Request Extended Successfully";
    }




    // Probation Request -----------------------------------------------

    // for sending probation accept response By manager
    @GetMapping("/manager/probation/accept/{cflEmail}")
    public String acceptProbationRequest(@PathVariable String cflEmail) {
        System.out.println(cflEmail+"cflEmail1111111111");
        cflService.sendProbationMailFromManagerToCFLAndHr(cflEmail);
//        return "Probation End Request Accepted Successfully";
        return "<html><body>" +
                "<script type=\"text/javascript\">" +
                "window.open('https://cal.com/startsmart/startsmart', '_blank');" +
                "</script>" +
                "</body></html>";
    }


    // for sending probation extend response to manager email
    @GetMapping("/manager/probation/extend/{cflEmail}")
    public String extendProbationRequest(@PathVariable String cflEmail) {
        cflService.sendProbationExtendMailFromManagerToCFLAndHr(cflEmail);
        return "Probation End Request Extended Successfully";
    }


    // -----------------------------------------------------

    @GetMapping("/getParticularCflByEmpId/{empId}")
    public ResponseEntity<?> getParticularCflByEmpId(@PathVariable Long empId) {
       Cfl cfl=cflService.getParticularCflByEmpId(empId);
       if(cfl!=null){
          return ResponseEntity.ok(cfl);
       }
       else{
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Cfl found");
       }
    }

    @PostMapping("/changeRequest/{empId}/{newValueForChange}/{changeType}")
    public ResponseEntity<?> changeRequest(@PathVariable Long empId,@PathVariable String newValueForChange,@PathVariable String changeType) {
        Cfl cfl=cflService.changeRequest(empId,newValueForChange,changeType);
        if(cfl!=null){
            return ResponseEntity.ok("Changed successfully");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not changed ");
        }
    }




    // ----------- upload file



    @PostMapping("/uploadCflFile")
    public ResponseEntity<?> uploadCflImage(@RequestParam Long empId, @RequestPart MultipartFile file) throws IOException {
        Cfl cfl=cflService.uploadCflImage(empId,file);
       if(cfl!=null){
           return ResponseEntity.ok(cfl);
       }
       else{
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Cfl found");
       }
    }


    // get All Cfl
    @GetMapping("/getAllCfl")
    public ResponseEntity<?>getAllCfl(){
        List<Cfl> cflList=cflService.getAllCfl();
        if(!cflList.isEmpty()){
            return ResponseEntity.ok(cflList);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Cfl found");
        }
    }

    // approve mail

    @PostMapping("/approveMail/{quarter}")
    public ResponseEntity<?>approveMail(@PathVariable String quarter,@RequestParam Long empId, @RequestParam String mgrEmail){
        Boolean result=cflService.approveMail(quarter,empId,mgrEmail);
        if(result){
            return ResponseEntity.ok("Mail Approved Successfully");
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to approve mail");
        }
    }


    @GetMapping("/isAnnualAppraisalFilled/{empId}")
    public boolean isAnnualAppraisalFilledByCfl(@PathVariable Long empId) {
        return cflService.isAnnualAppraisalFilledByCfl(empId);
    }

    // =================== REWARDS AND RECOGNITION =================
    // is CflEmail Exists

    @GetMapping("/isCflExists/{cflEmail}")
    public ResponseEntity<?> isCflExists(@PathVariable String cflEmail){
        Boolean isCflExists=cflService.isCflEmailExists(cflEmail);
        if(isCflExists){
            return ResponseEntity.ok("true");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("false");
        }
    }

    @GetMapping("/isMentorExists/{mentorEmail}")
    public ResponseEntity<?> isMentorExists(@PathVariable String mentorEmail){
        Boolean isMentorEmailExists=cflService.isMentorEmailExists(mentorEmail);
        if(isMentorEmailExists){
            return ResponseEntity.ok("true");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("false");
        }
    }



    @GetMapping("/isManagerExists/{managerEmail}")
    public ResponseEntity<?> isManagerEmailExists(@PathVariable String managerEmail){
        Boolean isManagerEmailExists=cflService.isManagerEmailExists(managerEmail);
        if(isManagerEmailExists){
            return ResponseEntity.ok("true");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("false");
        }
    }



    @GetMapping("/rewards/{mail}")
    public ResponseEntity<?>  findCflMentorOrManagerEmail(@PathVariable String mail){
        String name=cflService.findCflMentorOrManagerEmail(mail);
        if(!name.isEmpty()){
            return ResponseEntity.ok(name);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("name not found");
        }
    }



    // otp process
    @PostMapping("/generate-otp/{email}/{userRole}")
    public ResponseEntity<?> generateOtp(@PathVariable String email,@PathVariable String userRole) {
        try {
            cflService.generateOtp(email,userRole);
            return ResponseEntity.ok("OTP sent successfully");
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate OTP");
        }
    }


    // verify otp
    @PostMapping("/verify-otp/{email}/{otp}")
    public ResponseEntity<?> verifyOtp(@PathVariable String email, @PathVariable String otp) {
        Boolean result=cflService.verifyOtp(email, otp);
        if(result){
            return ResponseEntity.ok("verified");
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to verify OTP");
        }
    }

    // update password
    @PostMapping("/update-password/{email}/{newPassword}/{userRole}")
    public ResponseEntity<String> enterNewPassword(@PathVariable String email, @PathVariable String newPassword, @PathVariable String userRole) {
        Register register=cflService.enterNewPassword(email,newPassword,userRole);
       if(register==null){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to update password");
       }
       else{
           return ResponseEntity.ok("Successfully updated password");
       }
    }


    @PostMapping("/update-cfl/{id}")
    public ResponseEntity<?> updateCfl(@PathVariable Long id, @RequestBody Cfl cfl) {
         Cfl cflObj=cflService.updateCfl(id,cfl);
         if(cflObj!=null){
             return ResponseEntity.ok(cflObj);
         }
         else{
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update Cfl");
         }
    }




    @GetMapping("/getAllDetailedReports")
    public ResponseEntity<?> getAllDetailedReports(){
        List<CompleteCflTableDTO>completeCflTableDTOS =cflService.getAllDetailedReports();
        if(!completeCflTableDTOS.isEmpty()){
            return ResponseEntity.ok(completeCflTableDTOS);
        }
        else{
            return ResponseEntity.status(400).body("Failed to get all completeCflTableDTOS ");
        }
    }


    @PostMapping("/countScreenTime/{userMail}/{timeInMinute}")
    public ResponseEntity<String> countCflScreenTime(@PathVariable String userMail,@PathVariable String timeInMinute) {
        String result=cflService.countCflScreenTime(userMail,timeInMinute);
        if(result !=null){
            return ResponseEntity.ok(result);
        }
        else{
            return ResponseEntity.status(400).body("Failed to get cfl screen-time");
        }
    }


    // get all cfl by mentor-email based on year

    @GetMapping("/getAllCflByMentorEmailBasedOnYear/{mentorEmail}/{year}")
    public ResponseEntity<?> getAllCflByMentorEmailBasedOnYear(@PathVariable String mentorEmail,@PathVariable String year) {
        List<Cfl> cflList=cflService.getAllCflByMentorEmailBasedOnYear(mentorEmail,year);
        return !cflList.isEmpty()?ResponseEntity.ok(cflList):ResponseEntity.status(404).build();
    }


    // get all cfl by mentor-email based on year

    @GetMapping("/getAllCflByManagerEmailBasedOnYear/{managerEmail}/{year}")
    public ResponseEntity<?> getAllCflByManagerEmailBasedOnYear(@PathVariable String managerEmail,@PathVariable String year) {
        List<Cfl> cflList=cflService.getAllCflByManagerEmailBasedOnYear(managerEmail,year);
        return !cflList.isEmpty()?ResponseEntity.ok(cflList):ResponseEntity.status(404).build();
    }

    // PROBATION EXTENSION FOR THREE MONTH MAIL TO CFL
    @PostMapping("/generateMailForCFLRegardingProbationExtension/{cflMail}")
    public void generateMailForCFLRegardingProbationExtension(@PathVariable String cflMail) {
        try {
            cflService.generateMailForCFLRegardingProbationExtension(cflMail);
        }
        catch(Exception e){
            System.out.println(e.getMessage()+"generateMailForCFLRegardingProbationExtension failed");
        }
    }


    // PROBATION EXTENSION FOR THREE MONTH MAIL TO HR
    @PostMapping("/generateMailForHRRegardingProbationExtension/{hrMail}")
    public void generateMailForHRRegardingProbationExtension(@PathVariable String hrMail) {
        try {
            cflService.generateMailForHRRegardingProbationExtension(hrMail);
        }
        catch(Exception e){
            System.out.println(e.getMessage()+"generateMailForHRRegardingProbationExtension failed");
        }
    }


    // PROBATION EXTENSION FOR THREE MONTH MAIL TO MANAGER
    @PostMapping("/generateMailForManagerRegardingProbationExtension/{cflMail}/{managerMail}")
    public void generateMailForManagerRegardingProbationExtension(@PathVariable String cflMail,@PathVariable String managerMail) {
        try {
            cflService.generateMailForManagerRegardingProbationExtension(cflMail, managerMail);
        }
        catch(Exception e){
            System.out.println(e.getMessage()+"generateMailForManagerRegardingProbationExtension failed");
        }
    }

    // PROBATION CONFIRMATION FEEDBACK MAIL TO MANAGER
    @PostMapping("/generateMailForProbationConfirmationFeedBackToManager/{cflMail}/{managerMail}")
    public void generateMailForProbationConfirmationFeedBackToManager(@PathVariable String cflMail, @PathVariable String managerMail) {
        try {
            cflService.sendProbationConfirmationFeedBackToManager(cflMail, managerMail);
        }
        catch(Exception e){
            System.out.println(e.getMessage()+"generateMailForProbationConfirmationFeedBackToManager failed");
        }
    }

    // CHANGE REQUEST MAIL FOR CFL
    @PostMapping("/sendChangeRequestMailToCFL/{cflMail}/{changeType}")
    public void sendChangeRequestMailToCFL(@PathVariable String cflMail, @PathVariable String changeType) {
        try {
            cflService.sendChangeRequestMailToCFL(cflMail, changeType);
        }
        catch(Exception e){
            System.out.println(e.getMessage()+"sendChangeRequestMailToCFL failed");
        }
    }



    // ANNUAL-APPRAISAL =======================================================

    @PostMapping("/sendMailToManagerRegardingCflRatingForAnnualAppraisal/{cflEmail}")
    public ResponseEntity<String> sendMailToManagerRegardingCFLRatingForAnnualAppraisal(@PathVariable String cflEmail,@RequestParam String cflKeyAccomplishment){
        try {
            cflService.sendMailToManagerRegardingCFLRatingForAnnualAppraisal(cflEmail,cflKeyAccomplishment);
            return ResponseEntity.ok("mail sent to manager");
        }
        catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

}
