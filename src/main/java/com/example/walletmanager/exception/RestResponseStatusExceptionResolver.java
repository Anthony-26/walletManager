package com.example.walletmanager.exception;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.example.walletmanager.exception.CustomExceptions.JwtTokenInvalidException;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RestResponseStatusExceptionResolver implements HandlerExceptionResolver {

    @Override
    @Nullable
    public ModelAndView resolveException(
            HttpServletRequest request, HttpServletResponse response, @Nullable Object handler, Exception ex) {

        Logger log = LoggerFactory.getLogger(RestResponseStatusExceptionResolver.class);

        if (ex instanceof JwtTokenInvalidException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            try {
                response.getWriter().write("{\"message\": \"" + ex.getMessage() + "\"}");
                log.error(ex.getMessage());
            } catch (IOException e) {
                log.error("Error while writing");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
            return new ModelAndView();
        }
        return null;
    }
}