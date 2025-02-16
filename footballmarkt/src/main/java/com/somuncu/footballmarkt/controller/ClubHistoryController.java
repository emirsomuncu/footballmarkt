package com.somuncu.footballmarkt.controller;

import com.somuncu.footballmarkt.entities.ClubHistory;
import com.somuncu.footballmarkt.request.dtos.clubhistory.ClubHistoryDto;
import com.somuncu.footballmarkt.response.ApiResponse;
import com.somuncu.footballmarkt.service.clubhistory.ClubHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/clubhistories")
public class ClubHistoryController {

    private final ClubHistoryService clubHistoryService;

    @GetMapping("/clubhistory/{playerId}")
    public ResponseEntity<ApiResponse> getClubHistoryOfPlayer(@PathVariable Long playerId) {
        ClubHistory clubHistory = this.clubHistoryService.getClubHistoryOfPlayer(playerId);
        ClubHistoryDto clubHistoryDto = this.clubHistoryService.converClubHistoryToClubHistoryDto(clubHistory);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfull" , clubHistoryDto));
    }

    @PostMapping("/clubhistory/add")
    public ResponseEntity<ApiResponse> createClubHistory(@RequestParam List<Long> clubIds) {
        this.clubHistoryService.createClubHistory(clubIds);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfull", null));
    }

    @PutMapping("/clubhistory/update")
    public ResponseEntity<ApiResponse> updateClubHistory(@RequestParam Long clubHistoryId , @RequestParam List<Long> clubIds) {
        this.clubHistoryService.updateClubHistory(clubHistoryId,clubIds);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfull", null));
    }

    @DeleteMapping("/clubhistory/delete")
    public ResponseEntity<ApiResponse> deleteClubHistory(@RequestParam Long clubHistoryId) {
        this.clubHistoryService.deleteClubHistory(clubHistoryId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfull", null));
    }


}
