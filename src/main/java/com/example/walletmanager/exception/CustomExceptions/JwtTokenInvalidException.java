package com.example.walletmanager.exception.CustomExceptions;

public class JwtTokenInvalidException extends RuntimeException {
    
    public JwtTokenInvalidException(String message){
        super(message);
    }

    public JwtTokenInvalidException(String message, Throwable cause){
        super(message, cause);
    }

}
