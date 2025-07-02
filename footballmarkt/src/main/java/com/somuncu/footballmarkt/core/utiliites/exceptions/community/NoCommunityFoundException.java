package com.somuncu.footballmarkt.core.utiliites.exceptions.community;

import com.somuncu.footballmarkt.core.utiliites.exceptions.arenagame.NoArenaGameFoundException;

public class NoCommunityFoundException extends RuntimeException {

    public NoCommunityFoundException(String message) {
        super(message);
    }
}
