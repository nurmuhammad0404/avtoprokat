package com.company.exc;
public class PhoneOrPasswordWrongException extends RuntimeException{
    public PhoneOrPasswordWrongException(String message) {
        super(message);
    }
}
