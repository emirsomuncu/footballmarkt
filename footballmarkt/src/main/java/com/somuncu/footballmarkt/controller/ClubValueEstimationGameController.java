package com.somuncu.footballmarkt.controller;

import com.somuncu.footballmarkt.entities.ClubValueEstimationGame;
import com.somuncu.footballmarkt.response.ApiResponse;
import com.somuncu.footballmarkt.response.PlayClubValueEstimationGameResponse;
import com.somuncu.footballmarkt.dtos.clubvalueestimationgame.ClubValueEstimationGameDto;
import com.somuncu.footballmarkt.service.clubvalueestimationgame.ClubValueEstimationGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/club-estimation-game")
public class ClubValueEstimationGameController {

    private final ClubValueEstimationGameService clubValueEstimationGameService;

    @PostMapping("/create-game")
    public ResponseEntity<ApiResponse<ClubValueEstimationGameDto>> createClubValueEstimationGame(@AuthenticationPrincipal UserDetails userDetails) {

        ClubValueEstimationGame clubValueEstimationGame = this.clubValueEstimationGameService.createClubValueEstimationGame(userDetails);
        ClubValueEstimationGameDto clubValueEstimationGameDto = this.clubValueEstimationGameService.convertToDto(clubValueEstimationGame);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Successfully" , clubValueEstimationGameDto));
    }

    @PostMapping("/play-game")
    public ResponseEntity<ApiResponse<PlayClubValueEstimationGameResponse>> playClubValueEstimationGame(@RequestParam Long gameId , @RequestParam Long clubId , @AuthenticationPrincipal UserDetails userDetails) {

        PlayClubValueEstimationGameResponse playClubValueEstimationGameResponse = this.clubValueEstimationGameService.playClubValueEstimationGame(gameId , clubId , userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successfully" , playClubValueEstimationGameResponse));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> deleteById(@RequestParam Long id) {
        this.clubValueEstimationGameService.deleteClubValueEstimationGameById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successfully" , null));
    }

}