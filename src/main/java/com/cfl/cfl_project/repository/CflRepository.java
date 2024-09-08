package com.cfl.cfl_project.repository;

import com.cfl.cfl_project.model.Cfl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CflRepository extends JpaRepository<Cfl,Long> {
    List<Cfl>findByYear(String year);
    Cfl findByEmpId(Long empId);
    List<Cfl> findByMentorEmail(String mentorEmail);
    List<Cfl> findByReportingManagerMail(String reportingManagerMail);

//    Optional<Cfl> findByEmpIdOrCflEmail(Long empId, String cflEmail);

    List<Cfl> findByMentorEmailAndYear(String mentorEmail,String year);

    List<Cfl> findByReportingManagerMailAndYear(String reportingManagerMail,String year);


    Cfl findByCflEmail(String cflEmail);
    List<Cfl>findByGoalSettingStatusHrToMgr(Boolean status);
    List<Cfl>findByGoalSettingReviewStatusHrToMgr(Boolean reviewStatus);
    List<Cfl>findByProbationStatus(Boolean probationStatus);

    List<Cfl>findByProbationStatusAndYear(Boolean probationStatus,String Year);

    List<Cfl> findByHrMail(String hrMail);
    List<Cfl> findAllByYear(String year);
//List<Cfl> findAllByCflCountStatus(Boolean status);
    Cfl findByCflFirstNameAndCflLastName(String cflFirstName,String cflLastName);
    Boolean existsByCflEmail(String cflEmail);
    Boolean existsByMentorEmail(String cflEmail);
    Boolean existsByReportingManagerMail(String cflEmail);
}
