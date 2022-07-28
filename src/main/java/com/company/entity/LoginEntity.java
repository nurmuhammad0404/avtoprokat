package com.company.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "login")
public class LoginEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String phone;
    @Column
    private String pswd;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
