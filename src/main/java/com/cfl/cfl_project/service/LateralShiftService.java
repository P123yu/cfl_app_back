package com.cfl.cfl_project.service;

import com.cfl.cfl_project.model.LateralShift;
import org.springframework.stereotype.Service;


@Service
public interface LateralShiftService {

    LateralShift create(LateralShift lateralShift);
    LateralShift getByEmpIdAndYear(Long empId,Long year);

}
