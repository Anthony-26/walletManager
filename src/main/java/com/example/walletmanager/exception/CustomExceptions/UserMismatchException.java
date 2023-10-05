package com.example.walletmanager.exception.CustomExceptions;

public class UserMismatchException extends RuntimeException {
    
    public UserMismatchException(String message) {
        super(message);
    }

    public UserMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

}