package com.company.exc;

public class DistrCodeAlreadyExistsException extends RuntimeException{
    public DistrCodeAlreadyExistsException(String message) {
        super(message);
    }
}
