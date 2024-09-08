package com.cfl.cfl_project.controller;

import com.cfl.cfl_project.jwt.JwtTokenGenerator;
import com.cfl.cfl_project.model.*;
import com.cfl.cfl_project.service.AuthenticationAndAuthorizationService;
import com.cfl.cfl_project.service.impl.RefreshServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")

public class GeneralController {

    @Autowired
    private AuthenticationAndAuthorizationService authenticationAndAuthorizationService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Register register) {
        //System.out.println(register.getUsername()+"controller");
        String registerResponse=authenticationAndAuthorizationService.register(register);
        if(register!=null) {
            return ResponseEntity.ok(registerResponse);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CFL not registered");
        }
    }


    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody Register register) {
        String registerResponse=authenticationAndAuthorizationService.registerAdmin(register);
        if(register!=null) {
            return ResponseEntity.ok(registerResponse);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Admin not registered");
        }
    }




    @PostMapping("/register/mentor")
    public ResponseEntity<?> registerMentor(@RequestBody Register register) {
        String registerResponse=authenticationAndAuthorizationService.registerMentor(register);
        if(register!=null) {
            return ResponseEntity.ok(registerResponse);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("mentor not registered");
        }
    }


    @PostMapping("/register/manager")
    public ResponseEntity<?> registerManager(@RequestBody Register register) {
        String registerResponse=authenticationAndAuthorizationService.registerManager(register);
        if(register!=null) {
            return ResponseEntity.ok(registerResponse);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("manager not registered");
        }
    }


    @PostMapping("/register/hr")
    public ResponseEntity<?> registerHr(@RequestBody Register register) {
        //System.out.println(register.getUsername()+"controller");
        String registerResponse=authenticationAndAuthorizationService.registerHr(register);
        if(register!=null) {
            return ResponseEntity.ok(registerResponse);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("hr not registered");
        }
    }




    @PostMapping("/login/{userRole}")
    public ResponseEntity<?> login(@RequestBody Login login,@PathVariable String userRole) {
        AuthenticationWithToken auth=authenticationAndAuthorizationService.login(login,userRole);
        if(auth!=null) {
            // step 9
            return ResponseEntity.ok(auth);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not login");
        }
    }


    @GetMapping("/getMail/{userName}/{userRole}")
    public ResponseEntity<?>getMail(@PathVariable String userName,@PathVariable String userRole){
        Boolean mail=authenticationAndAuthorizationService.isValidEmail(userName,userRole);
        if(mail) {
            return ResponseEntity.ok(true);
        }
        else {
            return ResponseEntity.ok(false);
        }
    }

    @GetMapping("/getRegisterStatus/{userName}/{userRole}")
    public ResponseEntity<?>getRegisterStatus(@PathVariable String userName,@PathVariable String userRole){
        Register register=authenticationAndAuthorizationService.getRegisterStatus(userName,userRole);
        if(register !=null ) {
            return ResponseEntity.ok(register);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("status not found");
        }
    }


    @GetMapping("/jwtWithRefreshToken/{userName}/{userRole}")
    public ResponseEntity<?> refreshToken(@PathVariable String userName,@PathVariable String userRole) {
        String jwtWithRefreshToken=authenticationAndAuthorizationService.refreshToken(userName,userRole);
        if(jwtWithRefreshToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not refresh token");
        }
        else{
            return ResponseEntity.ok(jwtWithRefreshToken);
        }

    }


    @PostMapping("/createNewPassword/{userRole}")
    public ResponseEntity<?> createPassword(@RequestParam String userName,@RequestParam String password,@PathVariable String userRole) {
       Register register=authenticationAndAuthorizationService.createPassword(userName,password,userRole);
       if(register !=null){
           return ResponseEntity.ok(register);
       }
       else {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("password not updated");
       }
    }



    @Autowired
    private RefreshServiceImpl refreshServiceImpl;

    @Autowired
    private JwtTokenGenerator jwtTokenGenerator;

    // refreshToken
    @PostMapping("/refresh/{refreshToken}")
    public  String generateTokenBasedOnRefreshToken(@PathVariable String refreshToken) {
        Refresh refresh=refreshServiceImpl.verifyRefreshToken(refreshToken);
        Register register=refresh.getRegister();
        return jwtTokenGenerator.generateToken(register);
    }



    @PostMapping("/updatePassword")
    public ResponseEntity<?> getRegisterByUserName(@RequestParam String userName,@RequestParam String userRole,@RequestParam String userOldPassword,@RequestParam String userNewPassword) {
        Boolean result=authenticationAndAuthorizationService.getRegisterByUserName(userName,userRole,userOldPassword,userNewPassword);
        System.out.println(result+"result");
        if(result){
            return ResponseEntity.ok(true);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("password not updated");
        }
    }

    @GetMapping("/findListOfRolesByUserName/{userName}")
    public ResponseEntity<?> findListOfRolesByUserName(@PathVariable String userName) {
        List<Register> registerList=authenticationAndAuthorizationService.findListOfRolesByUserName(userName);

        if(!registerList.isEmpty()){
            return ResponseEntity.ok(registerList);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("roles not found");
        }
    }



    @GetMapping("/findByUsernameAndRole/{userName}/{role}")
    public ResponseEntity<?> findByUsernameAndRole(@PathVariable String userName,@PathVariable Role role) {
        Register register=authenticationAndAuthorizationService.findByUsernameAndRole(userName,role);

        if(register!=null){
            return ResponseEntity.ok(register);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("username and roles row not found");
        }
    }


    @PutMapping("/updatePasswordFromAdmin/{id}/{password}")
    public ResponseEntity<?> updatePasswordFromAdmin(@PathVariable Long id,@PathVariable String password) {
        Register result=authenticationAndAuthorizationService.updatePasswordFromAdmin(id,password);
        if(result !=null){
            return ResponseEntity.ok(true);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("password not updated");
        }
    }








}
