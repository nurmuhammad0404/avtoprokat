package com.company.dto;

import com.company.enums.DistrStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DistrDTO {
    private Integer id;
    private String name;
    private String surname;
    private String userName;
    private String distrCode;
    private String phone;
    private Boolean visible;
//    private Integer vehicleId;
//    private LocalDateTime workDate;
//    private Integer teritoryId;
//    private Integer driverId;
    private DistrStatus status;
    private LocalDateTime createdDate;
}
