package com.example.walletmanager.exception.CustomExceptions;

public class TickerNotFoundException extends RuntimeException {

    public TickerNotFoundException(String message){
        super(message);
    }
    
}
