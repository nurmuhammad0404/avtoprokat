package com.company.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

@Data
@Entity
@Table(name = "avtoprokat")
public class AvtoprokatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String distrCode;

    @Column
    private LocalDate workDate;

    @Column
    private String carNumber;
    @Column
    private String distrName;
    @Column
    private String driverName;
    @Column
    private String teritory;
    @Column
    private String distrUserName;
    @Column
    private String driverUserName;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
