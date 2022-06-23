package com.company.dto;

import com.company.enums.ProfileRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data   
@AllArgsConstructor
@NoArgsConstructor
public class ProfileJwtDTO {
    private String id;
    private ProfileRole role;
}
