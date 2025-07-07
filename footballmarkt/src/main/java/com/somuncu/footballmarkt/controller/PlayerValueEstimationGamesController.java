package com.somuncu.footballmarkt.controller;

import com.somuncu.footballmarkt.entities.PlayerValueEstimationGame;
import com.somuncu.footballmarkt.response.ApiResponse;
import com.somuncu.footballmarkt.response.PlayPlayerValueEstimationGameResponse;
import com.somuncu.footballmarkt.response.dtos.playervalueestimationgame.PlayerValueEstimationGameDto;
import com.somuncu.footballmarkt.service.playervalueestimationgame.PlayerValueEstimationGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/player-estimation-game")
public class PlayerValueEstimationGamesController {

    private final PlayerValueEstimationGameService playerValueEstimationGameService;

    @PostMapping("/create-game")
    public ResponseEntity<ApiResponse> createPlayerValueEstimationGame(@AuthenticationPrincipal UserDetails userDetails) {

        PlayerValueEstimationGame playerValueEstimationGame = this.playerValueEstimationGameService.createPlayerValueEstimationGame(userDetails);
        PlayerValueEstimationGameDto playerValueEstimationGameDto = this.playerValueEstimationGameService.convert(playerValueEstimationGame);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Successfully" , playerValueEstimationGameDto));
    }

    @PostMapping("/play-game")
    public ResponseEntity<ApiResponse> playPlayerValueEstimationGame(@RequestParam Long gameId , @RequestParam Long playerId , @AuthenticationPrincipal UserDetails userDetails) {

        PlayPlayerValueEstimationGameResponse playPlayerValueEstimationGameResponse = this.playerValueEstimationGameService.playPlayerValueEstimationGame(gameId , playerId , userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfully" , playPlayerValueEstimationGameResponse));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteById(@RequestParam Long id) {
        this.playerValueEstimationGameService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfully" , null));
    }
}
