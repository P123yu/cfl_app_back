////package com.cfl.cfl_project.model;
////
////import lombok.AllArgsConstructor;
////import lombok.Getter;
////import lombok.NoArgsConstructor;
////import lombok.Setter;
////
////@Setter
////@Getter
////@NoArgsConstructor
////@AllArgsConstructor
////public class Login {
////    private String userName;
////    private String userPassword;
////}
//
//
//
//
//package com.cfl.cfl_project.model;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.List;
//
//@Setter
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//public class Login implements UserDetails {
//    private String userName;
//    private String userPassword;
//
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority("ROLE_"+role.name()));
//    }
//
//    @Override
//    public String getPassword() {
//        return userPassword;
//    }
//
//    @Override
//    public String getUsername() {
//        return userName;
//    }
//
//
//}



package com.cfl.cfl_project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;

//@Setter
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
public class Login implements UserDetails {

    private String userName;
    private String userPassword;


    private Register register;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + register.getRole().name()));
    }

    @Override
    public String getPassword() {
        return register.getUserPassword();
    }

    @Override
    public String getUsername() {
        return register.getUserName();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Register getRegister() {
        return register;
    }

    public void setRegister(Register register) {
        this.register = register;
    }

    public Login() {

    }

    public Login(Register register, String userPassword, String userName) {
        this.register = register;
        this.userPassword = userPassword;
        this.userName = userName;
    }
}
