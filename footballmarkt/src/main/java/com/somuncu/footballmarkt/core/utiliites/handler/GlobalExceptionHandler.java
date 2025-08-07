package com.somuncu.footballmarkt.core.utiliites.handler;

import com.somuncu.footballmarkt.core.utiliites.exceptions.arenagame.InvalidPlayerIdException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.arenagame.NoArenaGameFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.club.ClubAlreadyExistsException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.club.NoClubFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.clubhistory.NoClubHistoryFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.clubvalueestimationgame.NoClubValueEstimationGameFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.community.NoCommunityFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.community.NotAbleToDoThisOperationException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.images.NoImageFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.images.WrongSaveImageRequestException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.league.NoLeaguesFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.news.NoNewsFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.player.NoPlayerFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.player.PlayerAlreadyExistsException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.playervalueestimationgame.NoPlayerValueEstimationGameFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.post.NoPostFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.question.NoQuestionFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.quiz.NoQuizFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.stats.NoStatsFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.stats.StatsAlreadyExistsException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.team.NoTeamFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.transferrumor.NoTransferRumorFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.trophy.NoTrophyFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.trophy.TrophyAlreadyExistsException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.user.UserAlreadyExistsException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.user.UserAlreadyInCommunityException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.user.UserNotFoundException;
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

    @ExceptionHandler(TrophyAlreadyExistsException.class)
    public ResponseEntity<HandlerResponse> handleTrophyAlreadyExistsException(TrophyAlreadyExistsException trophyAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HandlerResponse(trophyAlreadyExistsException.getMessage()));
    }

    @ExceptionHandler(WrongSaveImageRequestException.class)
    public ResponseEntity<HandlerResponse> handleWrongSaveImageRequestException(WrongSaveImageRequestException wrongSaveImageRequestException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HandlerResponse(wrongSaveImageRequestException.getMessage()));
    }

    @ExceptionHandler(NoArenaGameFoundException.class)
    public ResponseEntity<HandlerResponse> handleNoArenaGameFoundException(NoArenaGameFoundException noArenaGameFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HandlerResponse(noArenaGameFoundException.getMessage()));
    }

    @ExceptionHandler(InvalidPlayerIdException.class)
    public ResponseEntity<HandlerResponse> handleInvalidPlayerIdException(InvalidPlayerIdException invalidPlayerIdException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HandlerResponse(invalidPlayerIdException.getMessage()));
    }

    @ExceptionHandler(NoCommunityFoundException.class)
    public ResponseEntity<HandlerResponse> handleNoCommunityFoundException(NoCommunityFoundException noCommunityFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HandlerResponse(noCommunityFoundException.getMessage()));
    }

    @ExceptionHandler(NotAbleToDoThisOperationException.class)
    public ResponseEntity<HandlerResponse> handleNotAbleToDoThisOperationException(NotAbleToDoThisOperationException notAbleToDoThisOperationException){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new HandlerResponse(notAbleToDoThisOperationException.getMessage()));
    }

    @ExceptionHandler(NoPostFoundException.class)
    public ResponseEntity<HandlerResponse> handleNoPostFoundException(NoPostFoundException noPostFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HandlerResponse(noPostFoundException.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<HandlerResponse> handleNoPostFoundException(UserNotFoundException userNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HandlerResponse(userNotFoundException.getMessage()));
    }

    @ExceptionHandler(UserAlreadyInCommunityException.class)
    public ResponseEntity<HandlerResponse> handleUserAlreadyInCommunityException(UserAlreadyInCommunityException userAlreadyInCommunityException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HandlerResponse(userAlreadyInCommunityException.getMessage()));
    }

    @ExceptionHandler(NoTransferRumorFoundException.class)
    public ResponseEntity<HandlerResponse> handleNoTransferRumorFoundException(NoTransferRumorFoundException noTransferRumorFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HandlerResponse(noTransferRumorFoundException.getMessage()));
    }

    @ExceptionHandler(NoPlayerValueEstimationGameFoundException.class)
    public ResponseEntity<HandlerResponse> handleNoPlayerValueEstimationGameFoundException(NoPlayerValueEstimationGameFoundException noPlayerValueEstimationGameFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HandlerResponse(noPlayerValueEstimationGameFoundException.getMessage()));
    }

    @ExceptionHandler(NoClubValueEstimationGameFoundException.class)
    public ResponseEntity<HandlerResponse> handleNoClubValueEstimationGameFoundException(NoClubValueEstimationGameFoundException noClubValueEstimationGameFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HandlerResponse(noClubValueEstimationGameFoundException.getMessage()));
    }

    @ExceptionHandler(NoNewsFoundException.class)
    public ResponseEntity<HandlerResponse> handleNoNewsFoundException(NoNewsFoundException noNewsFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HandlerResponse(noNewsFoundException.getMessage()));
    }

    @ExceptionHandler(NoQuestionFoundException.class)
    public ResponseEntity<HandlerResponse> handleNoQuestionFoundException(NoQuestionFoundException noQuestionFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HandlerResponse(noQuestionFoundException.getMessage()));
    }

    @ExceptionHandler(NoQuizFoundException.class)
    public ResponseEntity<HandlerResponse> handleNoQuizFoundException(NoQuizFoundException noQuizFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HandlerResponse(noQuizFoundException.getMessage()));
    }
    @ExceptionHandler(NoTeamFoundException.class)
    public ResponseEntity<HandlerResponse> handleNoTeamFoundException(NoTeamFoundException noTeamFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HandlerResponse(noTeamFoundException.getMessage()));
    }

}
