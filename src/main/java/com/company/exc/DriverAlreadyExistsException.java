package com.company.exc;

public class DriverAlreadyExistsException extends RuntimeException{
    public DriverAlreadyExistsException(String message) {
        super(message);
    }
}
