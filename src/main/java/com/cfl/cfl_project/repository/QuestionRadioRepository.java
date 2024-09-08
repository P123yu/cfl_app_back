package com.cfl.cfl_project.repository;

import com.cfl.cfl_project.model.QuestionRadio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRadioRepository extends JpaRepository<QuestionRadio,Long> {
//    QuestionRadio findByEmpIdAndQuarter(Long empId,String Quarter);

    QuestionRadio findByEmpId(Long empId);

}
