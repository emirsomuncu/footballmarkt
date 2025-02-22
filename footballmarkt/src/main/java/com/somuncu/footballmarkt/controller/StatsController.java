package com.somuncu.footballmarkt.controller;

import com.somuncu.footballmarkt.entities.Stats;
import com.somuncu.footballmarkt.response.dtos.stats.StatsDto;
import com.somuncu.footballmarkt.request.stats.CreateStatsRequest;
import com.somuncu.footballmarkt.request.stats.UpdateStatsRequest;
import com.somuncu.footballmarkt.response.ApiResponse;
import com.somuncu.footballmarkt.service.stats.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/stats")
public class StatsController {

    private final StatsService statsService ;


    @GetMapping("/player-stats")
    public ResponseEntity<ApiResponse> getStatsByPlayerName(@RequestParam String playerFirstName , @RequestParam String playerLastName) {
        List<Stats> statsList = this.statsService.getStatsByPlayerName(playerFirstName,playerLastName);
        List<StatsDto> statsDtosList = this.statsService.convertStatsListToStatsDtoList(statsList);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfull" , statsDtosList));
    }


    @GetMapping("/player-season-stats")
    public ResponseEntity<ApiResponse> getStatsByPlayerNameAndSeason(@RequestParam String playerFirstName , @RequestParam String playerLastName, @RequestParam String season) {
        Stats stats = this.statsService.getStatsByPlayerNameAndSeason(playerFirstName,playerLastName,season);
        StatsDto statsDto = this.statsService.convertStatsToStatsDto(stats);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfull" , statsDto));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createStats(@RequestBody CreateStatsRequest createStatsRequest) {
        this.statsService.createStats(createStatsRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Succesfull" , null));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateStats(@RequestBody UpdateStatsRequest updateStatsRequest) {
        this.statsService.updateStats(updateStatsRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Succesfull" , null));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteStats(@PathVariable Long id) {
        this.statsService.deleteStats(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfull" , null));
    }

}
