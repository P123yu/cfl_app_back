package com.cfl.cfl_project.model;


import jakarta.persistence.*;
import lombok.*;

@Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@ToString
public class QuizResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cflId;

    private String name;

    private Long score;

    @Column(columnDefinition = "TEXT")
    private String topic;


}
