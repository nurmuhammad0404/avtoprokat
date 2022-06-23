package com.company.exc;

public class CharactersNotUpperException extends RuntimeException{
    public CharactersNotUpperException(String message) {
        super(message);
    }
}
