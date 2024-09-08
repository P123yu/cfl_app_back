//package com.cfl.cfl_project.service.impl;
//
//
//import com.cfl.cfl_project.email.CflToMentorMail;
//import com.cfl.cfl_project.jwt.JwtTokenGenerator;
//import com.cfl.cfl_project.model.Login;
//import com.cfl.cfl_project.model.Refresh;
//import com.cfl.cfl_project.model.Register;
//import com.cfl.cfl_project.repository.RegisterRepository;
//import com.cfl.cfl_project.service.AuthenticationAndAuthorizationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AuthenticationAndAuthorizationServiceImpl implements AuthenticationAndAuthorizationService {
//
//    @Autowired
//    private RegisterRepository registerRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//
//    @Autowired
//    private JwtTokenGenerator jwtTokenGenerator;
//
//    @Autowired
//    private RefreshServiceImpl refreshServiceImpl;
////
////    @Override
////    public String register(Register register) {
////        System.out.print(register.getUserPassword());
////        register.setUserPassword(passwordEncoder.encode(register.getUserPassword()));
////
////        Register newRegister=registerRepository.save(register);
////        System.out.println(newRegister.getRole().toString());
////        refreshServiceImpl.createRefreshToken(newRegister.getUsername());
////
////
////        // this line generate JWT Token
////        String jwtToken=jwtTokenGenerator.generateToken(newRegister);
////        return jwtToken;
////    }
//
//
////
////    @Override
////    public String register(Register register) {
////        System.out.print(register.getUserPassword());
////        register.setUserPassword(passwordEncoder.encode(register.getUserPassword()));
////
////        Register newRegister=registerRepository.save(register);
////        System.out.println(newRegister.getRole().toString());
////        refreshServiceImpl.createRefreshToken(newRegister.getUsername());
////
////
////        // this line generate JWT Token
////        String jwtToken=jwtTokenGenerator.generateToken(newRegister);
////        return jwtToken;
////    }
//
//
//
//    @Autowired
//    private CflToMentorMail cflToMentorMail;
//
//    @Override
//    public String register(Register register) {
//        System.out.println(String.valueOf(register));
//        // Generate a random 4-digit number
//        int randomFourDigit = (int)(Math.random() * 9000) + 1000;
//
//        // Create the password by concatenating the username and the 4-digit number
//        String password = register.getUsername().substring(0,5) + randomFourDigit;
//        System.out.println("Generated Password: " + password); // Print the generated password for debugging
//
//        // Encode the generated password
//        register.setUserPassword(passwordEncoder.encode(password));
//
//        // Save the register object to the repository
//        Register newRegister = registerRepository.save(register);
//        System.out.println(newRegister.getRole().toString()); // Print the role for debugging
//
//        // Create a refresh token for the new user
//        refreshServiceImpl.createRefreshToken(newRegister.getUsername());
//
//        // Generate a JWT token for the new user
//        String jwtToken = jwtTokenGenerator.generateToken(newRegister);
//
//        cflToMentorMail.sendPasswordEmail(register.getUsername(),password);
//
//        return jwtToken; // Return the generated JWT token
//    }
//
//
//
//    @Override
//    public String registerMentor(Register register) {
//        System.out.println(String.valueOf(register));
//        // Generate a random 4-digit number
//        int randomFourDigit = (int)(Math.random() * 9000) + 1000;
//
//        // Create the password by concatenating the username and the 4-digit number
//        String password = register.getUsername().substring(0,5)+ randomFourDigit;
//        System.out.println("Generated Password: " + password); // Print the generated password for debugging
//
//        // Encode the generated password
//        register.setUserPassword(passwordEncoder.encode(password));
//
//        // Save the register object to the repository
//        Register newRegister = registerRepository.save(register);
//        System.out.println(newRegister.getRole().toString()); // Print the role for debugging
//
//        // Create a refresh token for the new user
//        refreshServiceImpl.createRefreshToken(newRegister.getUsername());
//
//        // Generate a JWT token for the new user
//        String jwtToken = jwtTokenGenerator.generateToken(newRegister);
//
//        cflToMentorMail.sendMentorPasswordEmail(register.getUsername(),password);
//
//        return jwtToken; // Return the generated JWT token
//    }
//
//
//
//
//
//    @Override
//    public String registerManager(Register register) {
//        System.out.println(String.valueOf(register));
//        // Generate a random 4-digit number
//        int randomFourDigit = (int)(Math.random() * 9000) + 1000;
//
//        // Create the password by concatenating the username and the 4-digit number
//        String password = register.getUsername().substring(0,5) + randomFourDigit;
//        System.out.println("Generated Password: " + password); // Print the generated password for debugging
//
//        // Encode the generated password
//        register.setUserPassword(passwordEncoder.encode(password));
//
//        // Save the register object to the repository
//        Register newRegister = registerRepository.save(register);
//        System.out.println(newRegister.getRole().toString()); // Print the role for debugging
//
//        // Create a refresh token for the new user
//        refreshServiceImpl.createRefreshToken(newRegister.getUsername());
//
//        // Generate a JWT token for the new user
//        String jwtToken = jwtTokenGenerator.generateToken(newRegister);
//
//        cflToMentorMail.sendManagerPasswordEmail(register.getUsername(),password);
//
//        return jwtToken; // Return the generated JWT token
//    }
//
//
//
//
//    @Autowired
//    AuthenticationManager authenticationManager;
//
//
//    //LoginModel
//
//
//    // step 3
//    @Override
//    public Authentication login(Login login) {
//        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(login.getUserName(), login.getUserPassword());
//        System.out.println(authToken+"authToken");
//        Authentication authentication = authenticationManager.authenticate(authToken); // from this line it will throw to step 4
//
//        // step 8
//        return authentication;
//    }
//
//    @Override
//    public Boolean isValidEmail(String username) {
//        return registerRepository.existsByUserName(username);
//    }
//
//
//    @Override
//    public String refreshToken(String userName) {
//        Register register = registerRepository.findByUserName(userName);
//        Refresh refresh = register.getRefresh();
//        System.out.println(refresh.getRefreshToken());
//        return refresh.getRefreshToken();
//    }
//
//    @Override
//    public Boolean getRegisterByUserName(String userName,String userOldPassword,String userNewPassword) {
//        Register registerObj = registerRepository.findByUserName(userName);
//        if (passwordEncoder.matches(userOldPassword, registerObj.getUserPassword())) {
//            String encodedPassword = passwordEncoder.encode(userNewPassword);
//            registerObj.setUserPassword(encodedPassword);
//            Register register = registerRepository.save(registerObj);
//            return true;
//        }
//        else{
//            return false;
//        }
//    }
//
//
//}




package com.cfl.cfl_project.service.impl;


import com.cfl.cfl_project.email.CflToMentorMail;
import com.cfl.cfl_project.jwt.JwtTokenGenerator;
import com.cfl.cfl_project.model.*;
import com.cfl.cfl_project.repository.GeneralRepository;
import com.cfl.cfl_project.repository.RegisterRepository;
import com.cfl.cfl_project.service.AuthenticationAndAuthorizationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AuthenticationAndAuthorizationServiceImpl implements AuthenticationAndAuthorizationService {

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private JwtTokenGenerator jwtTokenGenerator;

    @Autowired
    private RefreshServiceImpl refreshServiceImpl;
//
//    @Override
//    public String register(Register register) {
//        System.out.print(register.getUserPassword());
//        register.setUserPassword(passwordEncoder.encode(register.getUserPassword()));
//
//        Register newRegister=registerRepository.save(register);
//        System.out.println(newRegister.getRole().toString());
//        refreshServiceImpl.createRefreshToken(newRegister.getUsername());
//
//
//        // this line generate JWT Token
//        String jwtToken=jwtTokenGenerator.generateToken(newRegister);
//        return jwtToken;
//    }


//
//    @Override
//    public String register(Register register) {
//        System.out.print(register.getUserPassword());
//        register.setUserPassword(passwordEncoder.encode(register.getUserPassword()));
//
//        Register newRegister=registerRepository.save(register);
//        System.out.println(newRegister.getRole().toString());
//        refreshServiceImpl.createRefreshToken(newRegister.getUsername());
//
//
//        // this line generate JWT Token
//        String jwtToken=jwtTokenGenerator.generateToken(newRegister);
//        return jwtToken;
//    }



    @Autowired
    private CflToMentorMail cflToMentorMail;

    @Override
    public String register(Register register) {

        // Generate a random 4-digit number
        int randomFourDigit = (int)(Math.random() * 9000) + 1000;
        // Create the password by concatenating the username and the 4-digit number
        String password = register.getUsername().substring(0,5) + randomFourDigit;
         // Encode the generated password
        register.setUserPassword(passwordEncoder.encode(password));
      //  register.setUserPassword(password);

        // Save the register object to the repository
        Register newRegister = registerRepository.save(register);
        // Create a refresh token for the new user
//        refreshServiceImpl.createRefreshToken(newRegister.getUsername());
        refreshServiceImpl.createRefreshToken(newRegister.getUsername(), Role.valueOf("CFL"));

        // generate mail notification for the new user password
        cflToMentorMail.sendPasswordEmail(register.getUsername(),password);
        return "Cfl registered";
    }


    @Override
    public String registerAdmin(Register register) {

        // Generate a random 4-digit number
        int randomFourDigit = (int)(Math.random() * 9000) + 1000;
        // Create the password by concatenating the username and the 4-digit number
        String password = register.getUsername().substring(0,5) + randomFourDigit;
        // Encode the generated password
        register.setUserPassword(passwordEncoder.encode(password));
        //  register.setUserPassword(password);

        // Save the register object to the repository
        Register newRegister = registerRepository.save(register);
        // Create a refresh token for the new user
//        refreshServiceImpl.createRefreshToken(newRegister.getUsername());
        refreshServiceImpl.createRefreshToken(newRegister.getUsername(), Role.valueOf("ADMIN"));

        // generate mail notification for the new user password
        cflToMentorMail.sendPasswordEmail(register.getUsername(),password);
        return "Admin registered";
    }




    @Override
    public String registerMentor(Register register) {
        // Generate a random 4-digit number
        int randomFourDigit = (int)(Math.random() * 9000) + 1000;
        // Create the password by concatenating the username and the 4-digit number
        String password = register.getUsername().substring(0,5) + randomFourDigit;
        // Encode the generated password
        register.setUserPassword(passwordEncoder.encode(password));
        // Save the register object to the repository
        Register newRegister = registerRepository.save(register);
        // Create a refresh token for the new user
        refreshServiceImpl.createRefreshToken(newRegister.getUsername(), Role.valueOf("MENTOR"));
        // generate mail notification for the new user password
        cflToMentorMail.sendMentorPasswordEmail(register.getUsername(),password,"Mentor");
        return "Mentor registered";
    }





    @Override
    public String registerManager(Register register) {
        // Generate a random 4-digit number
        int randomFourDigit = (int)(Math.random() * 9000) + 1000;
        // Create the password by concatenating the username and the 4-digit number
        String password = register.getUsername().substring(0,5) + randomFourDigit;
        // Encode the generated password
        register.setUserPassword(passwordEncoder.encode(password));

        // Save the register object to the repository
        Register newRegister = registerRepository.save(register);
        // Create a refresh token for the new user
        refreshServiceImpl.createRefreshToken(newRegister.getUsername(), Role.valueOf("MANAGER"));
        // generate mail notification for the new user password
        cflToMentorMail.sendManagerPasswordEmail(register.getUsername(),password,"Manager");
        return "Manager registered";
    }


    @Override
    public String registerHr(Register register) {
        // Generate a random 4-digit number
        int randomFourDigit = (int)(Math.random() * 9000) + 1000;
        // Create the password by concatenating the username and the 4-digit number
        String password = register.getUsername().substring(0,5) + randomFourDigit;
        // Encode the generated password
        register.setUserPassword(passwordEncoder.encode(password));

        register.setStatus(false);

        // Save the register object to the repository
        Register newRegister = registerRepository.save(register);
        // Create a refresh token for the new user
        refreshServiceImpl.createRefreshToken(newRegister.getUsername(), Role.valueOf("HR"));
        // generate mail notification for the new user password
        cflToMentorMail.sendHrPasswordEmail(register.getUsername(),password,"HR");
        return "HR registered";
    }

    @Override
    public Register createPassword(String userName,String password,String userRole) {
//        Register register=registerRepository.findByUserName(userName);
        Register register=registerRepository.findByUserNameAndRole(userName, Role.valueOf(userRole));
        register.setUserPassword(passwordEncoder.encode(password));
        register.setStatus(true);
        return registerRepository.save(register);
    }


    @Autowired
    AuthenticationManager authenticationManager;


    //LoginModel


    // step 3
    @Override
    public AuthenticationWithToken login(Login login,String userRole) {
        Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + userRole));
        // Pass the correct type to the authentication token
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(login.getUserName(), login.getUserPassword(), authorities);


        SecurityContextHolder.getContext().setAuthentication(authToken);
       // UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(login.getUserName(), login.getUserPassword(),userRole);
        Authentication authentication = authenticationManager.authenticate(authToken);
//        Register register=registerRepository.findByUserName(login.getUserName());
        Register register=registerRepository.findByUserNameAndRole(login.getUserName(),Role.valueOf(userRole));

        // Generate a JWT token for the new user
        String jwtToken = jwtTokenGenerator.generateToken(register);
        // create object for authenticationToken class
        AuthenticationWithToken authenticationWithToken=new AuthenticationWithToken();
        authenticationWithToken.setAuthentication(authentication);
        authenticationWithToken.setToken(jwtToken);
        // step 8
        return authenticationWithToken;
    }

    @Override
    public Boolean isValidEmail(String username,String userRole) {
        return registerRepository.existsByUserNameAndRole(username,Role.valueOf(userRole));
    }

    @Override
    public Register getRegisterStatus(String username,String userRole) {
        return registerRepository.findByUserNameAndRole(username,Role.valueOf(userRole));
    }


    @Override
    public String refreshToken(String userName,String userRole) {
        Role role=Role.valueOf(userRole);
        Register register = registerRepository.findByUserNameAndRole(userName,Role.valueOf(userRole));
        Refresh refresh = register.getRefresh();
        System.out.println(refresh.getRefreshToken());
        return refresh.getRefreshToken();
    }

    @Override
    public Boolean getRegisterByUserName(String userName,String userRole,String userOldPassword,String userNewPassword) {
        Register registerObj = registerRepository.findByUserNameAndRole(userName,Role.valueOf(userRole));
        if (passwordEncoder.matches(userOldPassword, registerObj.getUserPassword())) {
            String encodedPassword = passwordEncoder.encode(userNewPassword);
            registerObj.setUserPassword(encodedPassword);
            registerObj.setStatus(true);
            Register register = registerRepository.save(registerObj);
            return true;
        }
        else{
            return false;
        }
    }

    @Autowired
    private GeneralRepository generalRepository;

    @Override
    public List<Register> findListOfRolesByUserName(String userName) {
        return generalRepository.findByUserNameContaining(userName);
    }


    @Override
    public Register findByUsernameAndRole(String userName,Role role) {
        return registerRepository.findByUserNameAndRole(userName,role);
    }

    @Override
    public Register updatePasswordFromAdmin(Long id, String password) {
        Register register = registerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Register not found with ID: " + id));
        String encodedPassword=passwordEncoder.encode(password);
        register.setUserPassword(encodedPassword);
        return registerRepository.save(register);
    }


}
