package com.company.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class AuthDTO {
    private String phone;
    private String pswd;
}
