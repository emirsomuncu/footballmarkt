package com.somuncu.footballmarkt.controller;

import com.somuncu.footballmarkt.request.team.CreateTeamRequest;
import com.somuncu.footballmarkt.request.team.UpdateTeamRequest;
import com.somuncu.footballmarkt.response.ApiResponse;
import com.somuncu.footballmarkt.response.dtos.team.TeamDto;
import com.somuncu.footballmarkt.service.team.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/teams")
public class TeamsController {

    private final TeamService teamService;

    @GetMapping("/team-by-id")
    public ResponseEntity<ApiResponse> getTeamById(@RequestParam Long id) {
        TeamDto teamDto = this.teamService.getTeamById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successful" , teamDto));
    }

    @GetMapping("/team-by-name")
    public ResponseEntity<ApiResponse> getTeamByTeamName(@RequestParam String name) {
        TeamDto teamDto = this.teamService.getTeamByTeamName(name);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successful" , teamDto));
    }

    @GetMapping("/by-username")
    public ResponseEntity<ApiResponse> getTeamsByUserName(@RequestParam String username) {
        List<TeamDto> teams = this.teamService.getTeamsByUserName(username);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfully listed", teams));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createTeam(@RequestBody CreateTeamRequest createTeamRequest , @AuthenticationPrincipal UserDetails userDetails) {
        TeamDto teamDto = this.teamService.createTeam(createTeamRequest , userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Successfully created" , null));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateTeam(@RequestBody UpdateTeamRequest updateTeamRequest , @AuthenticationPrincipal UserDetails userDetails) {
        TeamDto teamDto = this.teamService.updateTeam(updateTeamRequest, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfully updated" , null));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteTeam(@RequestParam Long id , @AuthenticationPrincipal UserDetails userDetails) {
        this.teamService.deleteTeam(id ,userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfully deleted" , null));
    }
}
