package com.example.walletmanager.exception.CustomExceptions;

public class PortfolioNotFoundException extends RuntimeException {
    
    public PortfolioNotFoundException(String message){
        super(message);
    }

}
