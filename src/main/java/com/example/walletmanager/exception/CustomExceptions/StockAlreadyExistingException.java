package com.example.walletmanager.exception.CustomExceptions;

public class StockAlreadyExistingException extends RuntimeException{
    
    public StockAlreadyExistingException(String message){
        super(message);
    }

}
