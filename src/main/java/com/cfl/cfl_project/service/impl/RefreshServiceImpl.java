package com.cfl.cfl_project.service.impl;


import com.cfl.cfl_project.model.Refresh;
import com.cfl.cfl_project.model.Register;
import com.cfl.cfl_project.model.Role;
import com.cfl.cfl_project.repository.RefreshRepository;
import com.cfl.cfl_project.repository.RegisterRepository;
import com.cfl.cfl_project.service.RefreshService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class RefreshServiceImpl implements RefreshService {

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private RefreshRepository refreshRepository;

    @Override
    public Refresh createRefreshToken(String userName, Role role) {
        System.out.println("Refresh token created for user: " + userName+role+"role");

        Register register = registerRepository.findByUserNameAndRole(userName,role);

        System.out.println(register+"register refresh ");
        Refresh refresh = register.getRefresh();

        if (refresh == null) {
            System.out.println("start here");
            System.out.println(registerRepository.findByUserNameAndRole(userName,role));

            refresh = new Refresh();
            refresh.setRefreshToken(UUID.randomUUID().toString());
//            refresh.setExpiry(Instant.now().plusMillis(3*30*24*60*60*1000));
            refresh.setExpiry(Instant.now().plus(720, ChronoUnit.DAYS));

            refresh.setRegister(register);
        } else {
//            refresh.setExpiry(Instant.now().plusMillis(3*30*24*60*60*1000));
            refresh.setExpiry(Instant.now().plus(180, ChronoUnit.DAYS));

        }

        return refreshRepository.save(refresh);
    }

    @Override
    public Refresh verifyRefreshToken(String token) {
        Refresh refresh_token = refreshRepository.findByRefreshToken(token).orElse(null);

        if (refresh_token != null && refresh_token.getExpiry().isAfter(Instant.now())) {
            return refresh_token;
        } else {
            refreshRepository.delete(refresh_token);
            throw new RuntimeException("refresh token expired");
        }
    }
}
