package com.company.entity;

import com.company.enums.DriverStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "driver")
@Data
public class DriverEnitity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String userName;
    @Column
    private String phone;

    @Column(name = "profile_id")
    private String profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", updatable = false, insertable = false)
    private ProfileEntity profile;

    @Column
    private Boolean visible = true;
    @Column
    @Enumerated(EnumType.STRING)
    private DriverStatus status;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
