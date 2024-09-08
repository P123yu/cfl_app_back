package com.cfl.cfl_project.service.impl;

import com.cfl.cfl_project.model.Register;
import com.cfl.cfl_project.model.Role;
import com.cfl.cfl_project.repository.RegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private RegisterRepository registerRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       // System.out.println(registerRepository.findByUserName(username).getUsername()+"username>>>>");

        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getAuthorities()); //[ROLE_ADMIN]
        String authoritiesString = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());

        System.out.println("Authorities: " + authoritiesString); //ROLE_ADMIN

        System.out.println(authentication+"authentication*************");
        Register register=registerRepository.findByUserNameAndRole(username, Role.valueOf(authoritiesString.split("_")[1]));
        System.out.println(register.getUserName()+"username uuu");
        System.out.println(register+"register12");
        return register;
    }
}


