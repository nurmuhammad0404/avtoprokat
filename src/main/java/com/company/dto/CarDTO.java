package com.company.dto;

import com.company.entity.ProfileEntity;
import com.company.enums.CarStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO {
    private Integer id;
    private String name;
    private String number;
    private Boolean visible;
    private CarStatus status;
    private String profileId;
    private ProfileEntity profile;
    private LocalDateTime createdDate;

}
