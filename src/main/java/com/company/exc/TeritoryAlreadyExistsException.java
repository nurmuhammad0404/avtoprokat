package com.company.exc;

public class TeritoryAlreadyExistsException extends RuntimeException{
    public TeritoryAlreadyExistsException(String message) {
        super(message);
    }
}
