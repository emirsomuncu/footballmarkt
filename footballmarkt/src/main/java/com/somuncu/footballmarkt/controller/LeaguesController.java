package com.somuncu.footballmarkt.controller;

import com.somuncu.footballmarkt.entities.League;
import com.somuncu.footballmarkt.request.dtos.league.LeagueDto;
import com.somuncu.footballmarkt.request.league.CreateLeagueRequest;
import com.somuncu.footballmarkt.request.league.UpdateLeagueRequest;
import com.somuncu.footballmarkt.response.ApiResponse;
import com.somuncu.footballmarkt.service.league.LeagueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("${api.prefix}/leagues")
@RestController
public class LeaguesController {

    private final LeagueService leagueService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllLeagues() {
        List<League> leagues = this.leagueService.getAllLeagues();
        List<LeagueDto> leagueDtoList = this.leagueService.convertLeagueListToLeagueListDtoList(leagues);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfull" , leagueDtoList));
    }

    @GetMapping("/ascending")
    public ResponseEntity<ApiResponse> getLeaguesWithAscendingMarketValue() {
        List<League> leagues = this.leagueService.getLeaguesWithAscendingMarketValue();
        List<LeagueDto> leagueDtoList = this.leagueService.convertLeagueListToLeagueListDtoList(leagues);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfull" , leagueDtoList));
    }

    @GetMapping("/descending")
    public ResponseEntity<ApiResponse> getLeaguesWithDescendingMarketValue() {
        List<League> leagues = this.leagueService.getLeaguesWithDescendingMarketValue();
        List<LeagueDto> leagueDtoList = this.leagueService.convertLeagueListToLeagueListDtoList(leagues);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfull" , leagueDtoList));
    }

    @PostMapping("/league/add")
    public ResponseEntity<ApiResponse> createLeague(@RequestBody CreateLeagueRequest createLeagueRequest) {
        this.leagueService.createLeague(createLeagueRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Successfull" , null));
    }

    @PutMapping("/league/update")
    public ResponseEntity<ApiResponse> updateLeague(@RequestBody UpdateLeagueRequest updateLeagueRequest) {
        this.leagueService.updateLeague(updateLeagueRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfull" , null));
    }

    @DeleteMapping("/{leagueId}/delete")
    public ResponseEntity<ApiResponse> deleteLeague(@PathVariable Long leagueId) {
        this.leagueService.deleteLeague(leagueId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfull" , null));
    }
}
