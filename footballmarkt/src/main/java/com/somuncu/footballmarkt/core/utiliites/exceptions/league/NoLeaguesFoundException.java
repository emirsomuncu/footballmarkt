package com.somuncu.footballmarkt.core.utiliites.exceptions.league;

public class NoLeaguesFoundException extends RuntimeException{
    public NoLeaguesFoundException(String message) {
        super(message);
    }
}
