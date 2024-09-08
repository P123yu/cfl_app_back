package com.cfl.cfl_project.service.impl;

import com.cfl.cfl_project.model.Cfl;
import com.cfl.cfl_project.model.LateralShift;
import com.cfl.cfl_project.repository.LateralShiftRepository;
import com.cfl.cfl_project.repository.CflRepository;
import com.cfl.cfl_project.service.LateralShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class LateralShiftServiceImpl implements LateralShiftService {
    @Autowired
    private LateralShiftRepository lateralShiftRepository;

    @Autowired
    private CflRepository cflRepository;


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
    @Transactional
    public LateralShift create(LateralShift lateralShift) {
//        int year=LocalDate.now().getYear();
//        if(getQuarter().equalsIgnoreCase("Q4")){
//            year-=1;
//        }

        Cfl cfl=cflRepository.findByEmpId(lateralShift.getEmpId());
        lateralShift.setYear(Long.valueOf(cfl.getYear()));
        return  lateralShiftRepository.save(lateralShift);
    }

    @Override
    public LateralShift getByEmpIdAndYear(Long empId, Long year) {
        return lateralShiftRepository.findByEmpIdAndYear(empId, year);
    }
}
