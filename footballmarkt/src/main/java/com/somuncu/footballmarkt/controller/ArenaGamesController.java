package com.somuncu.footballmarkt.controller;

import com.somuncu.footballmarkt.entities.ArenaGame;
import com.somuncu.footballmarkt.response.ApiResponse;
import com.somuncu.footballmarkt.response.PlayArenaGameResponse;
import com.somuncu.footballmarkt.dtos.arenagame.ArenaGameDto;
import com.somuncu.footballmarkt.service.arena.ArenaGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/arena-games")
public class ArenaGamesController {

    private final ArenaGameService arenaGameService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ArenaGameDto>> createArenaGame(@RequestParam(required = false) Long clubId , @RequestParam(required = false) Long leagueId) {
        ArenaGame arenaGame = this.arenaGameService.createArenaGame(clubId, leagueId);
        ArenaGameDto arenaGameDto = this.arenaGameService.convertArenaGameToArenaGameDto(arenaGame);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Successfully created" , arenaGameDto));
    }

    @PostMapping("/play")
    public ResponseEntity<ApiResponse<PlayArenaGameResponse>> playArenaGame(@RequestParam Long arenaGameId , @RequestParam Long playerId) {
        PlayArenaGameResponse playArenaGameResponse = this.arenaGameService.playArenaGame(arenaGameId,playerId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Player picked successfully", playArenaGameResponse));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse<Void>> deleteArenaGame(@RequestParam Long arenaGameId) {
        this.arenaGameService.deleteArenaGameRecord(arenaGameId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Arena game deleted successfully", null));
    }
}
