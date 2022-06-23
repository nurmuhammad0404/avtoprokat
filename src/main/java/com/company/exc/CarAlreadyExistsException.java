package com.company.exc;

public class CarAlreadyExistsException extends RuntimeException{
    public CarAlreadyExistsException(String message) {
        super(message);
    }
}
