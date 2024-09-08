package com.cfl.cfl_project.repository;

import com.cfl.cfl_project.model.QuizResult;
import jakarta.persistence.Column;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult,Long> {
    boolean existsByCflIdAndTopic(Long cflId,String topic);

    List<QuizResult>findByTopic(String topic);

}
