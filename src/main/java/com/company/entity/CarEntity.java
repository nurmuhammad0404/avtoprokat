package com.company.entity;

import com.company.enums.CarStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "car")
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String number;
    @Column
    private Boolean visible = true;
    @Column
    @Enumerated(EnumType.STRING)
    private CarStatus status;
    @Column(name = "profile_id")
    private String profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", updatable = false, insertable = false)
    private ProfileEntity profile;
    @Column
    private LocalDateTime createdDate;
}
