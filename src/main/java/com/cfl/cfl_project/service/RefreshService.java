package com.cfl.cfl_project.service;

import com.cfl.cfl_project.model.Refresh;
import com.cfl.cfl_project.model.Role;
import org.springframework.stereotype.Service;

@Service
public interface RefreshService {
    // generate refresh token
//    public Refresh createRefreshToken(String userName);

    public Refresh createRefreshToken(String userName, Role role);


    // verify refresh token
    public Refresh verifyRefreshToken(String token);
}
