package com.company.dto;

import com.company.enums.TeritoryStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeritoryDTO {
    private Integer id;
    private String name;
    private Boolean visible;
    private TeritoryStatus status;
    private LocalDateTime createdDate;
}
