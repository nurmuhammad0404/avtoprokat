package com.company.exc;

public class DateFormatWrongException extends RuntimeException{
    public DateFormatWrongException(String message) {
        super(message);
    }
}
