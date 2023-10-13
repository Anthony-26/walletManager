package com.example.walletmanager.exception.CustomExceptions;

public class StockQuantityNotFoundException extends RuntimeException {
    
    public StockQuantityNotFoundException(String message){
        super(message);
    }

}
