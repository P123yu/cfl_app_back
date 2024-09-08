package com.cfl.cfl_project.service.impl;

import com.cfl.cfl_project.model.CflRoles;
import com.cfl.cfl_project.repository.CflRolesRepository;
import com.cfl.cfl_project.service.CflRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CflRolesServiceImpl implements CflRolesService {

    @Autowired
    private CflRolesRepository cflRolesRepository;

    public static String getQuarter() {
        LocalDate date=LocalDate.now();
        int month = date.getMonthValue();

        if (month >= 1 && month <= 3) {
            return "Q4";
        } else if (month >= 4 && month <= 6) {
            return "Q1";
        } else if (month >= 7 && month <= 9) {
            return "Q2";
        } else if (month >= 10 && month <= 12) {
            return "Q3";
        } else {
            throw new IllegalArgumentException("Invalid month: " + month);
        }
    }

    @Override
    public CflRoles create(CflRoles cflRoles) {
        CflRoles cflRolesObj=new CflRoles();
        cflRolesObj.setRoleName(cflRoles.getRoleName());
        Long year= (long) LocalDate.now().getYear();
        if(getQuarter().equalsIgnoreCase("Q4")){
            year-=1;
        }
        cflRolesObj.setYear(year);
        return cflRolesRepository.save(cflRolesObj);
    }

    @Override
    //@Cacheable(value = "cfl", key="'byYear_' + #id")
    public CflRoles getCflRoleByYear(Long year) {
        return cflRolesRepository.findByYear(year);
    }
}
