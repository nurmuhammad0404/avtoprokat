package com.company.controller;

import com.company.exc.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler({AppBadRequestException.class, PhoneNumberAlreadyExistsException.class,
            ItemNotFoundException.class, UserNameAlreadyExistsException.class, DistrCodeAlreadyExistsException.class,
            TeritoryAlreadyExistsException.class, CarAlreadyExistsException.class, CharactersNotUpperException.class,
            DateFormatWrongException.class, DriverAlreadyExistsException.class, ItemActiveException.class,
            PhoneNumberAlreadyExistsException.class, TokenNotValidException.class})
    public ResponseEntity<?> handleBadRequestException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }


    @ExceptionHandler({AppForbiddenException.class})
    public ResponseEntity<?> handleForbiddenException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

}
