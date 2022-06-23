package com.company.dto;

import com.company.enums.DriverStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DriverDTO {
    private Integer id;
    private String name;
    private String surname;
    private String userName;
    private String phone;
    private Boolean visible;
    private DriverStatus status;
    private LocalDateTime createdDate;

}
