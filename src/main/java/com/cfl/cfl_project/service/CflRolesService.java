package com.cfl.cfl_project.service;

import com.cfl.cfl_project.model.CflRoles;
import org.springframework.stereotype.Service;

@Service
public interface CflRolesService {
    CflRoles create(CflRoles cflRoles);
    CflRoles getCflRoleByYear(Long year);
}
