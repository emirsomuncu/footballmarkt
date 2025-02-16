package com.somuncu.footballmarkt.core.utiliites.exceptions.player;

public class PlayerAlreadyExistsException extends RuntimeException{

    public PlayerAlreadyExistsException(String message) {
        super(message);
    }
}
