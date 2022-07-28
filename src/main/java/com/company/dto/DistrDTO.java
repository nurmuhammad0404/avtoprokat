package com.company.dto;

import com.company.entity.ProfileEntity;
import com.company.enums.DistrStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DistrDTO {
    private Integer id;
    private String name;
    private String surname;
    private String userName;
    private String distrCode;
    private String phone;
    private Boolean visible;
    private String profileId;
    private ProfileEntity profile;
    private DistrStatus status;
    private LocalDateTime createdDate;
}
