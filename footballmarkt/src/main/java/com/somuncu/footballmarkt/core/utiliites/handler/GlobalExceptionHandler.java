package com.somuncu.footballmarkt.core.utiliites.handler;

import com.somuncu.footballmarkt.core.utiliites.exceptions.club.ClubAlreadyExistsException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.club.NoClubFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.clubhistory.NoClubHistoryFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.images.NoImageFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.league.NoLeaguesFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.player.NoPlayerFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.player.PlayerAlreadyExistsException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.stats.NoStatsFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.stats.StatsAlreadyExistsException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.trophy.NoTrophyFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.user.UserAlreadyExistsException;
import com.somuncu.footballmarkt.response.HandlerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoPlayerFoundException.class)
    public ResponseEntity<HandlerResponse> handleNoPlayerFoundException(NoPlayerFoundException noPlayerFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HandlerResponse(noPlayerFoundException.getMessage()));
    }

    @ExceptionHandler(PlayerAlreadyExistsException.class)
    public ResponseEntity<HandlerResponse> handlePlayerAlreadyExistsException(PlayerAlreadyExistsException playerAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HandlerResponse(playerAlreadyExistsException.getMessage()));
    }

    @ExceptionHandler(NoClubFoundException.class)
    public ResponseEntity<HandlerResponse> handleNoClubFoundException(NoClubFoundException noClubFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HandlerResponse(noClubFoundException.getMessage()));
    }

    @ExceptionHandler(ClubAlreadyExistsException.class)
    public ResponseEntity<HandlerResponse> handleClubAlreadyExistsException(ClubAlreadyExistsException clubAlreadyExistsException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HandlerResponse(clubAlreadyExistsException.getMessage()));
    }

    @ExceptionHandler(NoClubHistoryFoundException.class)
    public ResponseEntity<HandlerResponse> handleNoClubHistoryFoundException(NoClubHistoryFoundException noClubHistoryFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HandlerResponse(noClubHistoryFoundException.getMessage()));
    }

    @ExceptionHandler(NoStatsFoundException.class)
    public ResponseEntity<HandlerResponse> handleNoStatsFoundException(NoStatsFoundException noStatsFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HandlerResponse(noStatsFoundException.getMessage()));
    }

    @ExceptionHandler(StatsAlreadyExistsException.class)
    public ResponseEntity<HandlerResponse> handleStatsAlreadyExistsException(StatsAlreadyExistsException statsAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HandlerResponse(statsAlreadyExistsException.getMessage()));
    }

    @ExceptionHandler(NoImageFoundException.class)
    public ResponseEntity<HandlerResponse> handleNoImageFoundException(NoImageFoundException noImageFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HandlerResponse(noImageFoundException.getMessage()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<HandlerResponse> handleUserAlreadyExistsException(UserAlreadyExistsException userAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HandlerResponse(userAlreadyExistsException.getMessage()));
    }

    @ExceptionHandler(NoLeaguesFoundException.class)
    public ResponseEntity<HandlerResponse> handleNoLeaguesFoundException(NoLeaguesFoundException noLeaguesFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HandlerResponse(noLeaguesFoundException.getMessage()));
    }

    @ExceptionHandler(NoTrophyFoundException.class)
    public ResponseEntity<HandlerResponse> handleNoTrophyFoundException(NoTrophyFoundException noTrophyFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HandlerResponse(noTrophyFoundException.getMessage()));
    }
}
