package com.company.entity;

import com.company.enums.CarStatus;
import com.company.service.CarService;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

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
    @Column
    private LocalDateTime createdDate;
}
