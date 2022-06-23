package com.company.exc;

public class ItemActiveException extends RuntimeException{
    public ItemActiveException(String message) {
        super(message);
    }
}
