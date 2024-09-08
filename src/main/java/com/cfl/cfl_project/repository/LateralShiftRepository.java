package com.cfl.cfl_project.repository;

import com.cfl.cfl_project.model.LateralShift;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LateralShiftRepository extends JpaRepository<LateralShift,Long> {
    LateralShift findByEmpIdAndYear(Long empId,Long year);
}
