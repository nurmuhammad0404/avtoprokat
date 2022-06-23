package com.company.validation;

import com.company.dto.CarDTO;
import com.company.exc.AppBadRequestException;

public class CarValidation {
    public static void isValid(CarDTO dto){
        if (dto.getName() == null){
            throw new AppBadRequestException("Car name invalid");
        }

        if (dto.getNumber() == null || dto.getNumber().trim().length() != 8){
            throw new AppBadRequestException("Car name invalid");
        }
    }
}
