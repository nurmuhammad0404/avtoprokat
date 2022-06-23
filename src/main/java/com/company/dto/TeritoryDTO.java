package com.company.dto;

import com.company.enums.TeritoryStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TeritoryDTO {
    private Integer id;
    private String name;
    private Boolean visible;
    private TeritoryStatus status;
    private LocalDateTime createdDate;
}
