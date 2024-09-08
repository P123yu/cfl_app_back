////////package com.cfl.cfl_project.model;
////////
////////
////////import jakarta.persistence.*;
////////import lombok.AllArgsConstructor;
////////import lombok.Getter;
////////import lombok.NoArgsConstructor;
////////import lombok.Setter;
////////import org.springframework.security.core.GrantedAuthority;
////////import org.springframework.security.core.authority.SimpleGrantedAuthority;
////////import org.springframework.security.core.userdetails.UserDetails;
////////
////////import java.util.Collection;
////////import java.util.List;
////////
////////@Entity
////////@Table(name="register_user")
////////@Getter
////////@Setter
////////@NoArgsConstructor
////////@AllArgsConstructor
////////
////////public class Register implements UserDetails {
////////
////////    @Id
////////    @GeneratedValue(strategy= GenerationType.IDENTITY)
////////    private Long userId;
////////
////////    private String userName;
////////    private String userPassword;
////////
////////    @Enumerated(EnumType.STRING)
////////    Role role;
////////
////////
////////    @Override
////////    public Collection<? extends GrantedAuthority> getAuthorities() {
////////        return List.of(new SimpleGrantedAuthority("ROLE_"+role.name()));
////////    }
////////    @Override
////////    public String getPassword() {
////////        return userPassword;
////////    }
////////
////////    @Override
////////    public String getUsername() {
////////        return userName;
////////    }
////////
////////}
//////
//////package com.cfl.cfl_project.model;
//////import com.fasterxml.jackson.annotation.JsonIgnore;
//////import jakarta.persistence.*;
//////import lombok.AllArgsConstructor;
//////import lombok.Getter;
//////import lombok.NoArgsConstructor;
//////import lombok.Setter;
//////import org.springframework.security.core.GrantedAuthority;
//////import org.springframework.security.core.authority.SimpleGrantedAuthority;
//////import org.springframework.security.core.userdetails.UserDetails;
//////
//////import java.util.Collection;
//////import java.util.List;
//////
//////@Entity
//////@Table(name="register_user")
//////@Getter
//////@Setter
//////@NoArgsConstructor
//////@AllArgsConstructor
//////
//////public class Register implements UserDetails {
//////
//////    @Id
//////    @GeneratedValue(strategy= GenerationType.IDENTITY)
//////    private Long userId;
//////
//////    private String userName;
//////    private String userPassword;
//////
//////    @Enumerated(EnumType.STRING)
//////    Role role;
//////
//////    @JsonIgnore
//////    @OneToOne(cascade = CascadeType.ALL, mappedBy = "register", fetch = FetchType.LAZY)
//////    private Refresh refresh;
//////
//////
//////    @Override
//////    public Collection<? extends GrantedAuthority> getAuthorities() {
//////        return List.of(new SimpleGrantedAuthority("ROLE_"+role.name()));
//////    }
//////    @Override
//////    public String getPassword() {
//////        return userPassword;
//////    }
//////
//////    @Override
//////    public String getUsername() {
//////        return userName;
//////    }
//////
//////
//////}
////
////
////
//////package com.cfl.cfl_project.model;
//////
//////
//////import jakarta.persistence.*;
//////import lombok.AllArgsConstructor;
//////import lombok.Getter;
//////import lombok.NoArgsConstructor;
//////import lombok.Setter;
//////import org.springframework.security.core.GrantedAuthority;
//////import org.springframework.security.core.authority.SimpleGrantedAuthority;
//////import org.springframework.security.core.userdetails.UserDetails;
//////
//////import java.util.Collection;
//////import java.util.List;
//////
//////@Entity
//////@Table(name="register_user")
//////@Getter
//////@Setter
//////@NoArgsConstructor
//////@AllArgsConstructor
//////
//////public class Register implements UserDetails {
//////
//////    @Id
//////    @GeneratedValue(strategy= GenerationType.IDENTITY)
//////    private Long userId;
//////
//////    private String userName;
//////    private String userPassword;
//////
//////    @Enumerated(EnumType.STRING)
//////    Role role;
//////
//////
//////    @Override
//////    public Collection<? extends GrantedAuthority> getAuthorities() {
//////        return List.of(new SimpleGrantedAuthority("ROLE_"+role.name()));
//////    }
//////    @Override
//////    public String getPassword() {
//////        return userPassword;
//////    }
//////
//////    @Override
//////    public String getUsername() {
//////        return userName;
//////    }
//////
//////}
////
////package com.cfl.cfl_project.model;
////import com.fasterxml.jackson.annotation.JsonIgnore;
////import jakarta.persistence.*;
////import lombok.AllArgsConstructor;
////import lombok.Getter;
////import lombok.NoArgsConstructor;
////import lombok.Setter;
////import org.springframework.security.core.GrantedAuthority;
////import org.springframework.security.core.authority.SimpleGrantedAuthority;
////import org.springframework.security.core.userdetails.UserDetails;
////
////import java.util.Collection;
////import java.util.List;
////
////@Entity
////@Table(name="register_user")
////@Getter
////@Setter
////@NoArgsConstructor
////@AllArgsConstructor
////
//////implements UserDetails
////
////public class Register  {
////
////    @Id
////    @GeneratedValue(strategy= GenerationType.IDENTITY)
////    private Long userId;
////
////    private String userName;
////    private String userPassword;
////
////    @Enumerated(EnumType.STRING)
////    Role role;
////
////    @JsonIgnore
////    @OneToOne(cascade = CascadeType.ALL, mappedBy = "register", fetch = FetchType.LAZY)
////    private Refresh refresh;
//////
//////
//////    @Override
//////    public Collection<? extends GrantedAuthority> getAuthorities() {
//////        return List.of(new SimpleGrantedAuthority("ROLE_"+role.name()));
//////    }
//////
//////    @Override
//////    public String getPassword() {
//////        return userPassword;
//////    }
//////
//////    @Override
//////    public String getUsername() {
//////        return userName;
//////    }
//////
////
////}
//
//package com.cfl.cfl_project.model;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
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
//@Entity
//@Table(name="register_user")
////@Getter
////@Setter
////@NoArgsConstructor
////@AllArgsConstructor
//public class Register implements UserDetails {
//
//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    private Long userId;
//
//    private String userName;
//    private String userPassword;
//
//    @Enumerated(EnumType.STRING)
//    private Role role;
//
//    @JsonIgnore
//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "register", fetch = FetchType.LAZY)
//    private Refresh refresh;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
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
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//
//    public Refresh getRefresh() {
//        return refresh;
//    }
//
//    public void setRefresh(Refresh refresh) {
//        this.refresh = refresh;
//    }
//
//    public Role getRole() {
//        return role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }
//
//    public String getUserPassword() {
//        return userPassword;
//    }
//
//    public void setUserPassword(String userPassword) {
//        this.userPassword = userPassword;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public Long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }
//
//    public Register() {
//
//    }
//
//    public Register(Long userId, String userName, String userPassword, Role role, Refresh refresh) {
//        this.userId = userId;
//        this.userName = userName;
//        this.userPassword = userPassword;
//        this.role = role;
//        this.refresh = refresh;
//    }
//}
//



package com.cfl.cfl_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="register_user")

@ToString
public class Register implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String userName;

    @JsonIgnore
    private String userPassword;

    private boolean status;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "register", fetch = FetchType.LAZY)
    private Refresh refresh;



    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Refresh getRefresh() {
        return refresh;
    }

    public void setRefresh(Refresh refresh) {
        this.refresh = refresh;
    }

    // Implementing methods from UserDetails interface
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userName;
    }

}

