package com.example.walletmanager.security.old;

public class SecurityConstants {
    public static final String SECRET_KEY = "";
    public static final int TOKEN_EXPIRATION = 7200000; // 7200000 milliseconds = 7200 seconds = 2 hours.
    public static final String BEARER = "Bearer "; // Authorization : "Bearer " + Token 
    public static final String AUTHORIZATION = "Authorization"; // "Authorization" : Bearer Token
    public static final String REGISTER_PATH = "/api/users/register"; // Public path that clients can use to register.
}