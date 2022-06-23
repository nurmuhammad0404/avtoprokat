package com.company.exc;

public class UserNameAlreadyExistsException extends RuntimeException{
    public UserNameAlreadyExistsException(String message) {
        super(message);
    }
}
