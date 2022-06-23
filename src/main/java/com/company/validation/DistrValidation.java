package com.company.validation;

import com.company.dto.DistrDTO;
import com.company.exc.AppBadRequestException;
import com.company.exc.CharactersNotUpperException;

public class DistrValidation {
    public static void isValid(DistrDTO dto){
        if (dto.getName() == null || dto.getName().trim().length() < 3){
            throw new AppBadRequestException("Ism yaroqsiz");
        }
        if (dto.getSurname() == null || dto.getSurname().trim().length() < 3){
            throw new AppBadRequestException("Familiya yaroqsiz");
        }
        if (dto.getUserName() == null || dto.getUserName().trim().length() < 3 || !dto.getUserName().startsWith("@")){
            throw new AppBadRequestException("Username yaroqsiz");
        }
        if (dto.getPhone() == null || !dto.getPhone().startsWith("+998") ||
                dto.getPhone().trim().length() != 14){
            throw new AppBadRequestException("Telefon raqam yaroqsiz. Namuna: +998911234567");
        }

        for (int i = 0; i < dto.getDistrCode().length(); i++){
            if (!Character.isUpperCase(dto.getDistrCode().charAt(i))){
                throw new CharactersNotUpperException("Distr kodi katta harflardan iborat bo'lishi kerak");
            }
        }
    }
}
