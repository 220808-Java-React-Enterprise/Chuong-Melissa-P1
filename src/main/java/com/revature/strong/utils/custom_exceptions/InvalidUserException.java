package com.revature.strong.utils.custom_exceptions;

public class InvalidUserException extends RuntimeException{
    public InvalidUserException(String s) {
        super(s);
    }
}
