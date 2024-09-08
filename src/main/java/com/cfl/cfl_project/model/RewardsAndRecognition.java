package com.cfl.cfl_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="rewards_and_recognition")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RewardsAndRecognition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rewardsPersonName;
    private String messagedPersonName;
    private String message;

    private String rewardImageName;

//    @Lob
//    @Column(columnDefinition = "LONGBLOB")
//    private byte[] rewardImage;


    @Column(columnDefinition = "BYTEA")
    private byte[] rewardImage;

    private LocalDate date;

    private String rewardRecognitionType;

    @Transient
    @Column(name = "file_data", columnDefinition = "BYTEA")
    private byte[] fileData;
}
