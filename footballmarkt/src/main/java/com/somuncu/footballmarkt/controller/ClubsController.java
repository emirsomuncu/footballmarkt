package com.somuncu.footballmarkt.controller;

import com.somuncu.footballmarkt.entities.Club;
import com.somuncu.footballmarkt.request.club.CreateClubRequest;
import com.somuncu.footballmarkt.request.club.UpdateClubRequest;
import com.somuncu.footballmarkt.dtos.club.ClubDto;
import com.somuncu.footballmarkt.response.ApiResponse;
import com.somuncu.footballmarkt.service.club.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/clubs")
public class ClubsController {

    private final ClubService clubService;


    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ClubDto>>> getAllClubs() {

        List<Club> clubList = this.clubService.getAllClubs();
        List<ClubDto> clubDtosList = this.clubService.convertClubListToClubDtoList(clubList);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Success" , clubDtosList ));
    }

    @GetMapping("/club/{clubName}")
    public ResponseEntity<ApiResponse<ClubDto>> getClubByName(@PathVariable String clubName) {

        Club club = this.clubService.getClubByName(clubName);
        ClubDto clubDto = this.clubService.convertClubToClubDto(club);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Success" , clubDto));
    }

    @GetMapping("/{leagueName}")
    public ResponseEntity<ApiResponse<List<ClubDto>>> getClubAccordingToLeagueName(@PathVariable String leagueName) {
        List<Club> clubList = this.clubService.getAllClubsByAccordingToLeagueName(leagueName);
        List<ClubDto> clubDtosList = this.clubService.convertClubListToClubDtoList(clubList);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Successfull" , clubDtosList ));
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Void>> createClub(@RequestBody CreateClubRequest createClubRequest) {

         this.clubService.createClub(createClubRequest);
         return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Club created" , null ));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Void>> updateClub(@RequestBody UpdateClubRequest updateClubRequest) {

        this.clubService.updateClub(updateClubRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Club updated" , null ));
    }

    @PutMapping("/change-league")
    public ResponseEntity<ApiResponse<Void>> changeClubLeague(@RequestParam Long clubId , @RequestParam Long newLeagueId) {

        this.clubService.changeClubLeague(clubId,newLeagueId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Club's league changed" , null));
    }

    /* It causes some errors and should be deactivated until resolved
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteClub(@RequestParam Long clubId) {

        this.clubService.deleteClub(clubId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Club deleted" , null ));
    }
     */

}
