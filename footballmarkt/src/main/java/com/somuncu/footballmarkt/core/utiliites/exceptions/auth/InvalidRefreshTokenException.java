package com.somuncu.footballmarkt.core.utiliites.exceptions.auth;

public class InvalidRefreshTokenException extends RuntimeException{
    public InvalidRefreshTokenException(String message) {
        super(message);
    }
}
