package com.cfl.cfl_project.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="hr_table")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long hrId;
    private String hrName;
    private String hrEmail;
    private String hrLocation;
    private String hrFileName;
    @Column(name = "hr_file_data", columnDefinition = "BYTEA")
    private byte[] hrFileData;

    @Column(name = "otp")
    private String otp;

    private String hrScreenTime;


}
