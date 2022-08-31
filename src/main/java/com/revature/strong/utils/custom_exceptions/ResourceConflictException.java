package com.revature.strong.utils.custom_exceptions;

public class ResourceConflictException extends RuntimeException{
    public ResourceConflictException() {
    }

    public ResourceConflictException(String s) {
        super(s);
    }

    public ResourceConflictException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ResourceConflictException(Throwable throwable) {
        super(throwable);
    }

    public ResourceConflictException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
