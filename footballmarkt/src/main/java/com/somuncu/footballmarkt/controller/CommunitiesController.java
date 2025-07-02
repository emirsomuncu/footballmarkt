package com.somuncu.footballmarkt.controller;

import com.somuncu.footballmarkt.entities.Community;
import com.somuncu.footballmarkt.request.community.CreateCommunityRequest;
import com.somuncu.footballmarkt.request.community.UpdateCommunityRequest;
import com.somuncu.footballmarkt.response.ApiResponse;
import com.somuncu.footballmarkt.response.PageResponse;
import com.somuncu.footballmarkt.response.dtos.community.CommunityDto;
import com.somuncu.footballmarkt.service.community.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/communities")
public class CommunitiesController {

    private final CommunityService communityService;

    @GetMapping("/community")
    public ResponseEntity<ApiResponse> getCommunityById(@RequestParam Long communityId) {

        Community community = this.communityService.getCommunityById(communityId);
        CommunityDto communityDto = this.communityService.convertCommunityToCommunityDto(community);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfully" , communityDto));
    }

    @GetMapping("/community/{communityName}")
    public ResponseEntity<ApiResponse> getCommunityByName(@PathVariable String communityName) {

        Community community = this.communityService.getCommunityByName(communityName);
        CommunityDto communityDto = this.communityService.convertCommunityToCommunityDto(community);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfully" , communityDto));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCommunities(@RequestParam int pagingOffset) {
        PageResponse<CommunityDto> communities = this.communityService.getAllCommunities(pagingOffset);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfully" , communities));
    }

    @GetMapping("/community/{communityId}/members")
    public ResponseEntity<ApiResponse> getNameListOfCommunityUsers(@PathVariable Long communityId) {
        List<String> users = this.communityService.getNameListOfCommunityUsers(communityId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfully" , users));
    }

    @PostMapping("/community/create")
    public ResponseEntity<ApiResponse> createCommunity(@RequestBody CreateCommunityRequest createCommunityRequest , @AuthenticationPrincipal UserDetails userDetails) {
        this.communityService.createCommunity(createCommunityRequest , userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Successfully created" , null));
    }

    @PutMapping("/community/update")
    public ResponseEntity<ApiResponse> updateCommunity(@RequestBody UpdateCommunityRequest updateCommunityRequest , @AuthenticationPrincipal UserDetails userDetails ) {
        this.communityService.updateCommunity(updateCommunityRequest , userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfully" , null));
    }

    @DeleteMapping("/community/delete")
    public ResponseEntity<ApiResponse> deleteCommunity(@RequestParam Long communityId , @AuthenticationPrincipal UserDetails userDetails) {
        this.communityService.deleteCommunityById(communityId,userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Successfully" , null));
    }
}
