package com.company.validation;

import com.company.dto.DistrDTO;
import com.company.exc.AppBadRequestException;
import com.company.exc.CharactersNotUpperException;

public class DistrValidation {
    public static void isValid(DistrDTO dto) {
        if (dto.getName() == null || dto.getName().trim().length() < 3) {
            throw new AppBadRequestException("Ism yaroqsiz");
        }
        if (dto.getSurname() == null || dto.getSurname().trim().length() < 3) {
            throw new AppBadRequestException("Familiya yaroqsiz");
        }
        if (dto.getUserName() == null || dto.getUserName().trim().length() < 3 || !dto.getUserName().startsWith("@")) {
            throw new AppBadRequestException("Username yaroqsiz");
        }
        if (dto.getPhone() == null || !dto.getPhone().startsWith("+998") ||
                dto.getPhone().trim().length() != 13) {
            throw new AppBadRequestException("Telefon raqam yaroqsiz. Namuna: +998911234567");
        }
        if (dto.getDistrCode() != null) {
            char[] chars = dto.getDistrCode().toCharArray();
            int upperCase = 0;
            int isDigit = 0;
            for (char aChar : chars) {
                if (Character.isUpperCase(aChar)) {
                    upperCase++;
                } else if (Character.isDigit(aChar)) {
                    isDigit++;
                }
            }
            if (upperCase == 0){
                throw new AppBadRequestException("Distr code katta harflardan va raqamlardan iborat bo'lishi kerak");
            }
            if (isDigit == 0){
                throw new AppBadRequestException("Distr code katta harflardan va raqamlardan iborat bo'lishi kerak");
            }
        }
    }
}
