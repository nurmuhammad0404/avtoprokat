package com.company.entity;

import com.company.enums.DistrStatus;
import com.company.enums.ProfileStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "distr")
public class DistrEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(nullable = false)
    private String userName;
    @Column
    private String distrCode;
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;
    @Column
    private Boolean visible = true;
    @Column
    @Enumerated(EnumType.STRING)
    private DistrStatus status = DistrStatus.NOTACTIVE;
    @Column(name = "created_date")
    private LocalDateTime createdDate;

}
