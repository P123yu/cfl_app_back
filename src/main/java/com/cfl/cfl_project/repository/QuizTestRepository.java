package com.cfl.cfl_project.repository;

import com.cfl.cfl_project.model.QuizTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizTestRepository extends JpaRepository<QuizTest,Long> {

    List<QuizTest> findByTopic(String topic);

    void deleteByTopic(String topic);
}
