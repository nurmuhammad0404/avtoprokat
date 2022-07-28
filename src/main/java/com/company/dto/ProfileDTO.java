package com.company.dto;

import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private String id;
    private String name;
    private String surname;
    private String userName;
    private String phone;
    private String password;
    private ProfileStatus status;
    private ProfileRole role;
    private String jwt;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
