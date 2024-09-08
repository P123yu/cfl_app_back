package com.cfl.cfl_project.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="cfl_memories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Memories {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "file_name", nullable = true)
    private String fileName;
//    @Lob
//    @Column(name = "file_data", columnDefinition = "LONGBLOB", nullable = true)
//    private byte[] fileData;


    @Column(name = "file_data", columnDefinition = "BYTEA", nullable = true)
    private byte[] fileData;
    private Long year;

}
