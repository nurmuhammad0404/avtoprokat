package com.company.validation;

import com.company.dto.DistrDTO;
import com.company.dto.DriverDTO;
import com.company.exc.AppBadRequestException;

public class DriverValidation {
    public static void isValid(DriverDTO dto){
        if (dto.getName() == null || dto.getName().trim().length() < 3){
            throw new AppBadRequestException("Haydovchi ismi yaroqsiz");
        }
        if (dto.getSurname() == null || dto.getSurname().trim().length() < 3){
            throw new AppBadRequestException("Haydovchi familyasi yaroqsiz");
        }
        if (dto.getUserName() == null || dto.getUserName().trim().length() < 3 || !dto.getUserName().startsWith("@")){
            throw new AppBadRequestException("Haydovchi username yaroqsiz");
        }
        if (dto.getPhone() == null || !dto.getPhone().startsWith("+998") ||
                dto.getPhone().trim().length() != 13){
            throw new AppBadRequestException("Haydovchi telefon nomeri yaroqsiz");
        }

    }
}
