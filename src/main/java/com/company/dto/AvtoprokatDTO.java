package com.company.dto;

import com.company.entity.ProfileEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvtoprokatDTO {
    private Integer id;

    @NotNull(message = "Distr codi kiritilmadi")
    private String distrCode;

    @NotNull(message = "Kun kiritilmadi")
    private String workDate;

    @NotNull(message = "Mashina raqami kiritilmadi")
    private String carNumber;
    @NotNull(message = "Distr ismi kiritilmadi")
    private String distrName;
    @NotNull(message = "Haydovchi ismi kiritilmadi")
    private String driverName;
    @NotNull(message = "Hudud kiritilmadi")
    private String teritory;
    @NotNull(message = "Distr username kiritilmadi")
    private String distrUserName;
    @NotNull(message = "Haydovchi username kiritilmadi")
    private String driverUserName;

    private String profileId;
    private ProfileEntity profile;
    private LocalDateTime createdDate;

}
