package com.company.entity;

import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "profile")
public class ProfileEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    protected String id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String userName;
    @Column
    private String phone;
    @Column
    private String password;
    @Column
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;
    @Column
    @Enumerated(EnumType.STRING)
    private ProfileRole role;
    @Column
    private Boolean visible = true;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    @Column(name = "update_date")
    private LocalDateTime updateDate;
}
