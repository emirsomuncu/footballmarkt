package com.somuncu.footballmarkt.controller;

import com.somuncu.footballmarkt.entities.Player;
import com.somuncu.footballmarkt.request.dtos.player.PlayerDto;
import com.somuncu.footballmarkt.request.player.AddPlayerRequest;
import com.somuncu.footballmarkt.request.player.UpdatePlayerRequest;
import com.somuncu.footballmarkt.response.ApiResponse;
import com.somuncu.footballmarkt.service.player.PlayerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<ApiResponse> listAllPlayers() {

        List<Player> playerList = playerService.listAllPlayers();
        List<PlayerDto> playerDtosList = playerService.convertPlayerListToPlayerDtoList(playerList);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfull" , playerDtosList ));
    }

    @GetMapping("/nation/{nation}")
    public ResponseEntity<ApiResponse> listPlayersAccordingToNation(@PathVariable String nation) {

        List<Player> playerList = playerService.listPlayersAccordingToNation(nation);
        List<PlayerDto> playerDtosList = playerService.convertPlayerListToPlayerDtoList(playerList);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfull" , playerDtosList));
    }

    @GetMapping("/club/{clubName}")
    public ResponseEntity<ApiResponse> listPlayersAccordingToClub(@PathVariable String clubName) {

        List<Player> playerList = playerService.listPlayersAccordingToClub(clubName);
        List<PlayerDto> playerDtosList = playerService.convertPlayerListToPlayerDtoList(playerList);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfull" , playerDtosList));
    }

    @GetMapping("/player")
    public ResponseEntity<ApiResponse> listPlayersAccordingToFirstAndLastName(@RequestParam String firstName , @RequestParam String lastName) {

        List<Player> playerList = playerService.listPlayersAccordingToFirstAndLastName(firstName, lastName);
        List<PlayerDto> playerDtosList = playerService.convertPlayerListToPlayerDtoList(playerList);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfull" , playerDtosList));
    }

    @GetMapping("/player/{position}")
    public ResponseEntity<ApiResponse> listPlayersAccordingToPosition(@PathVariable String position) {

        List<Player> playerList = playerService.listPlayersAccordingToPosition(position);
        List<PlayerDto> playerDtoList = playerService.convertPlayerListToPlayerDtoList(playerList);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfull" , playerDtoList));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> listPlayersAccordingToClubAndPosition(@RequestParam String clubName , @RequestParam String position) {

        List<Player> playerList = playerService.listPlayersAccordingToClubAndPosition(clubName , position);
        List<PlayerDto> playerDtosList = this.playerService.convertPlayerListToPlayerDtoList(playerList);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfull" , playerDtosList));
    }

    @GetMapping("/descending")
    public ResponseEntity<ApiResponse> listAllPlayersAccordingToDescendingMarketValue() {

        List<Player> playerList = playerService.listAllPlayersAccordingToDescendingMarketValue();
        List<PlayerDto> playerDtosList = this.playerService.convertPlayerListToPlayerDtoList(playerList);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfull" , playerDtosList));
    }

    @GetMapping("/club/descending")
    public ResponseEntity<ApiResponse> listAllPlayersOfClubAccordingToDescendingMarketValue(@RequestParam String clubName) {

        List<Player> playerList = playerService.listAllPlayersOfClubAccordingToDescendingMarketValue(clubName);
        List<PlayerDto> playerDtosList = this.playerService.convertPlayerListToPlayerDtoList(playerList);
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
