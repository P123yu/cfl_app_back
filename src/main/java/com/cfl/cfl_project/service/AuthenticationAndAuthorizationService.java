package com.cfl.cfl_project.service;

import com.cfl.cfl_project.model.AuthenticationWithToken;
import com.cfl.cfl_project.model.Login;
import com.cfl.cfl_project.model.Register;
import com.cfl.cfl_project.model.Role;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthenticationAndAuthorizationService {

    // register CFL
    String register(Register register);

    // register admin
    String registerAdmin(Register register);

    // register mentor
    String registerMentor(Register register);


    // register manager
    String registerManager(Register register);


    // register hr
    String registerHr(Register register);

    // create new password
    Register createPassword(String userName,String password,String userRole);

//    // check status
//    boolean checkStatus(String userName);


    // Login
    AuthenticationWithToken login(Login login,String userRole);

    // find info By username
//    Boolean isValidEmail(String username);
    Boolean isValidEmail(String username, String userRole);



    // check status for setting new password by default
    Register getRegisterStatus(String username,String userRole);

    // generate jwt By UserName
    String refreshToken(String userName,String userRole);

    // fetch register data
    Boolean getRegisterByUserName(String userName,String userRole,String userOldPassword,String userNewPassword);

    List<Register> findListOfRolesByUserName(String userName);

    Register findByUsernameAndRole(String userName,Role role);

    Register updatePasswordFromAdmin(Long id,String password);

}
