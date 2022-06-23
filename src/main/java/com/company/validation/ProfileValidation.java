package com.company.validation;

import com.company.dto.ProfileDTO;
import com.company.exc.AppBadRequestException;

public class ProfileValidation {
    public static void isValid(ProfileDTO dto){
        if (dto.getName() == null || dto.getName().trim().length() < 3){
            throw new AppBadRequestException("Name invalid");
        }
        if (dto.getSurname() == null || dto.getSurname().trim().length() < 3){
            throw new AppBadRequestException("Surname invalid");
        }
        if (dto.getUserName() == null || dto.getUserName().trim().length() < 3 || !dto.getUserName().startsWith("@")){
            throw new AppBadRequestException("Username invalid");
        }
    }
}
