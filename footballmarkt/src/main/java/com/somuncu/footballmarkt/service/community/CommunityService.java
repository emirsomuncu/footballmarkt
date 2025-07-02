package com.somuncu.footballmarkt.service.community;

import com.somuncu.footballmarkt.entities.Community;
import com.somuncu.footballmarkt.entities.User;
import com.somuncu.footballmarkt.request.community.CreateCommunityRequest;
import com.somuncu.footballmarkt.request.community.UpdateCommunityRequest;
import com.somuncu.footballmarkt.response.DetermineNumbersForPagingResponse;
import com.somuncu.footballmarkt.response.PageResponse;
import com.somuncu.footballmarkt.response.dtos.community.CommunityDto;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface CommunityService {

    public Community getCommunityById(Long communityId);
    public Community getCommunityByName(String communityName);
    public PageResponse<CommunityDto> getAllCommunities(int pagingOffset);
    public List<String> getNameListOfCommunityUsers(Long communityId);
    public void createCommunity(CreateCommunityRequest createCommunityRequest , UserDetails userDetails);
    public void updateCommunity(UpdateCommunityRequest updateCommunityRequest, UserDetails userDetails);
    public void deleteCommunityById(Long communityId , UserDetails userDetails);
    public CommunityDto convertCommunityToCommunityDto(Community community);
    public User getCurrentUser(UserDetails userDetails);
    public DetermineNumbersForPagingResponse determineNumbersForPagingResponse(int pagingOffset);

}
