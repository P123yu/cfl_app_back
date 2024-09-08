package com.cfl.cfl_project.repository;

import com.cfl.cfl_project.model.Memories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoriesRepository extends JpaRepository<Memories,Long> {
    List<Memories>findByYear(Long year);
}
