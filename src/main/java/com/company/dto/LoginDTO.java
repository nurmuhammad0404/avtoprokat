package com.company.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class LoginDTO {
    private Integer id;
    private String phone;
    private String pswd;
    private LocalDateTime createdDate;
}
