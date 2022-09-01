package com.revature.strong.utils.custom_exceptions;

public class AuthenticationException extends RuntimeException{
    public AuthenticationException() {
    }

    public AuthenticationException(String s) {
        super(s);
    }

    public AuthenticationException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public AuthenticationException(Throwable throwable) {
        super(throwable);
    }

    public AuthenticationException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
