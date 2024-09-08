package com.cfl.cfl_project.repository;

import com.cfl.cfl_project.model.ManagerRatingQuestionAndAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRatingQuestionAndAnswerRepository extends JpaRepository<ManagerRatingQuestionAndAnswer,Long> {
    ManagerRatingQuestionAndAnswer findByEmpId(Long empId);
}
