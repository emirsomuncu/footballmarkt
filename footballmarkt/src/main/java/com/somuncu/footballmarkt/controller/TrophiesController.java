package com.somuncu.footballmarkt.controller;

import com.somuncu.footballmarkt.entities.Trophy;
import com.somuncu.footballmarkt.request.trophy.CreateTrophyRequest;
import com.somuncu.footballmarkt.request.trophy.UpdateTrophyRequest;
import com.somuncu.footballmarkt.response.ApiResponse;
import com.somuncu.footballmarkt.dtos.trophy.TrophyDto;
import com.somuncu.footballmarkt.service.trophy.TrophyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/trophies")
public class TrophiesController {

    private final TrophyService trophyService;

    @GetMapping("/{name}")
    public ResponseEntity<ApiResponse<List<TrophyDto>>> getTrophyByName(@PathVariable String name) {
        List<Trophy> trophies = this.trophyService.getTrophyByName(name);
        List<TrophyDto> trophyDtosList = this.trophyService.convertTrophyListToTrophyDtoList(trophies);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successfull " , trophyDtosList));
    }

    @GetMapping("/trophy")
    public ResponseEntity<ApiResponse<TrophyDto>> getTrophyBySeasonAndLeague(@RequestParam String season , @RequestParam String leagueName) {
        Trophy trophy = this.trophyService.getTrophyBySeasonAndLeague(season,leagueName);
        TrophyDto trophyDto= this.trophyService.convertTrophyToTrophyDto(trophy);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successfull " , trophyDto));
    }

    @GetMapping("/league/{leagueName}")
    public ResponseEntity<ApiResponse<List<TrophyDto>>> getLeagueTrophies(@PathVariable String leagueName) {
        List<Trophy> trophies = this.trophyService.getLeagueTrophies(leagueName);
        List<TrophyDto> trophyDtosList = this.trophyService.convertTrophyListToTrophyDtoList(trophies);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successfull " , trophyDtosList));
    }

    @GetMapping("/club/{clubName}")
    public ResponseEntity<ApiResponse<List<TrophyDto>>> getClubTrophies(@PathVariable String clubName) {
        List<Trophy> trophies = this.trophyService.getClubTrophies(clubName);
        List<TrophyDto> trophyDtosList = this.trophyService.convertTrophyListToTrophyDtoList(trophies);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successfull" , trophyDtosList));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Void>> createTrophy(@RequestBody CreateTrophyRequest createTrophyRequest) {
        this.trophyService.createTrophy(createTrophyRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Trophy created successfully" , null));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Void>> updateTrophy(@RequestBody UpdateTrophyRequest updateTrophyRequest) {
        this.trophyService.updateTrophy(updateTrophyRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Trophy updated successfully" , null));
    }

    @DeleteMapping("/trophy/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTrophy(@PathVariable Long id) {
        this.trophyService.deleteTrophy(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successfully deleted" , null));
    }
}
