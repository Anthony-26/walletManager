package com.example.walletmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.WebRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.walletmanager.exception.CustomExceptions.IncorrectBodyInformationsException;
import com.example.walletmanager.exception.CustomExceptions.JwtTokenInvalidException;
import com.example.walletmanager.exception.CustomExceptions.PortfolioNotFoundException;
import com.example.walletmanager.exception.CustomExceptions.ResponseParseException;
import com.example.walletmanager.exception.CustomExceptions.TickerNotFoundException;
import com.example.walletmanager.exception.CustomExceptions.UserMismatchException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(ResponseParseException.class)
    public ResponseEntity<String> handleResponseParseException(ResponseParseException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TickerNotFoundException.class)
    public ResponseEntity<String> handleTickerNotFoundException(TickerNotFoundException ex) {
        System.out.println(ex.getCause());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserMismatchException.class)
    public ResponseEntity<String> handleUserMismatchException(UserMismatchException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(JwtTokenInvalidException.class)
    public ResponseEntity<String> handleJwtTokenInvalidException(JwtTokenInvalidException ex) {
        System.out.println(ex.getCause());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PortfolioNotFoundException.class)
    public ResponseEntity<String> handlePortfolioNotFoundException(PortfolioNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncorrectBodyInformationsException.class)
    public ResponseEntity<String> handleIncorrectBodyInformationsException(IncorrectBodyInformationsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errorMessages = new ArrayList<>();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            errorMessages.add(error.getDefaultMessage());
        }
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }
}