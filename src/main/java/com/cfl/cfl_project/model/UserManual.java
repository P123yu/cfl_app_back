package com.cfl.cfl_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="user_manual_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserManual {
    @Id
    private Long Id;
    @Column(name = "file_name")
    private String userManualFileName;

    @Column(name = "file_data", columnDefinition = "BYTEA")
    private byte[] userManualFileData;
}
