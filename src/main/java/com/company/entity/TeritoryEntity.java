package com.company.entity;

import com.company.enums.TeritoryStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "teritory")
public class TeritoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private Boolean visible = true;
    @Column
    @Enumerated(EnumType.STRING)
    private TeritoryStatus status;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}

