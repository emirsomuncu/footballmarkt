package com.somuncu.footballmarkt.controller;

import com.somuncu.footballmarkt.entities.Player;
import com.somuncu.footballmarkt.response.PageResponse;
import com.somuncu.footballmarkt.response.dtos.player.PlayerDto;
import com.somuncu.footballmarkt.request.player.AddPlayerRequest;
import com.somuncu.footballmarkt.request.player.UpdatePlayerRequest;
import com.somuncu.footballmarkt.response.ApiResponse;
import com.somuncu.footballmarkt.service.player.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("${api.prefix}/players")
public class PlayersController {

    private PlayerService playerService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> listAllPlayers(@RequestParam(defaultValue = "0") int pagingOffset) {

        PageResponse<PlayerDto> pageResponse = playerService.listAllPlayers(pagingOffset);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfull" , pageResponse ));
    }

    @GetMapping("/player")
    public ResponseEntity<ApiResponse> getPlayerById(@RequestParam Long playerId) {
        Player player = this.playerService.getPlayerById(playerId);
        PlayerDto playerDto = this.playerService.convertPlayerToPlayerDto(player);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfully" , playerDto));
    }

    @GetMapping("/player/name/{fullName}")
    public ResponseEntity<ApiResponse> getPlayerByFullName(@PathVariable String fullName) {
        Player player = this.playerService.getPlayerByFullName(fullName);
        PlayerDto playerDto = this.playerService.convertPlayerToPlayerDto(player);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfully" , playerDto));
    }

    @GetMapping("/most-viewed")
    public ResponseEntity<ApiResponse> getMostViewedPlayers(@RequestParam(defaultValue = "0") int pagingOffset) {
        PageResponse<PlayerDto> pageResponse = this.playerService.getMostViewedPlayers(pagingOffset);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfull" , pageResponse));
    }

    @GetMapping("/nation/{nation}")
    public ResponseEntity<ApiResponse> listPlayersAccordingToNation(@PathVariable String nation ,
                                                                    @RequestParam(defaultValue = "0") int pagingOffset) {

        PageResponse<PlayerDto> playerDtosList = this.playerService.listPlayersAccordingToNation(nation , pagingOffset);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfull" , playerDtosList));
    }

    @GetMapping("/club/{clubName}")
    public ResponseEntity<ApiResponse> listPlayersAccordingToClub(@PathVariable String clubName) {

        List<Player> playerList = playerService.listPlayersAccordingToClub(clubName);
        List<PlayerDto> playerDtosList = playerService.convertPlayerListToPlayerDtoList(playerList);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfull" , playerDtosList));
    }

    @GetMapping("/player/{position}")
    public ResponseEntity<ApiResponse> listPlayersAccordingToPosition(@PathVariable String position ,
                                                                      @RequestParam(defaultValue = "0") int pagingOffset) {

        PageResponse<PlayerDto> playerDtoList = this.playerService.listPlayersAccordingToPosition(position,pagingOffset);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfull" , playerDtoList));

    }

    @GetMapping("/club/{clubName}/{position}")
    public ResponseEntity<ApiResponse> listPlayersAccordingToClubAndPosition(@PathVariable String clubName , @PathVariable String position) {

        List<Player> playerList = playerService.listPlayersAccordingToClubAndPosition(clubName , position);
        List<PlayerDto> playerDtosList = this.playerService.convertPlayerListToPlayerDtoList(playerList);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfull" , playerDtosList));
    }

    @GetMapping("/descending")
    public ResponseEntity<ApiResponse> listAllPlayersAccordingToDescendingMarketValue(@RequestParam(defaultValue = "0") int pagingOffset) {

        PageResponse<PlayerDto> playerDtosList = this.playerService.listAllPlayersAccordingToDescendingMarketValue(pagingOffset);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfull" , playerDtosList));
    }

    @GetMapping("/club/descending")
    public ResponseEntity<ApiResponse> listAllPlayersOfClubAccordingToDescendingMarketValue(@RequestParam String clubName ,
                                                                                            @RequestParam(defaultValue = "0") int pagingOffset) {   // pagination olmalı

        PageResponse<PlayerDto> playerDtosList = playerService.listAllPlayersOfClubAccordingToDescendingMarketValue(clubName , pagingOffset);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfull" , playerDtosList));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> addPlayer( @RequestBody AddPlayerRequest addPlayerRequest ) {

        this.playerService.addPlayer(addPlayerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Player created" ,null));

    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updatePlayer(@RequestBody UpdatePlayerRequest updatePlayerRequest) {

        this.playerService.updatePlayer(updatePlayerRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Player updated" , null));
    }

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse> transferPlayer(@RequestParam Long playerId, @RequestParam Long newClubId) {

        this.playerService.transferPlayer(playerId,newClubId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Player transferred the new club" , null));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deletePlayer(@RequestParam Long playerId) {

        this.playerService.deletePlayer(playerId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Player deleted" , null));
    }
}
