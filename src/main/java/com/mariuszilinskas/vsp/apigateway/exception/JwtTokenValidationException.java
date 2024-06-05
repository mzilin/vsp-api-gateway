package com.mariuszilinskas.vsp.apigateway.exception;

/**
 * This class represents a custom exception to be thrown when
 * Access Token validation fails
 *
 * @author Marius Zilinskas
 */
public class JwtTokenValidationException extends RuntimeException {

    public JwtTokenValidationException() {
        super("Invalid JWT Token");
    }
}
