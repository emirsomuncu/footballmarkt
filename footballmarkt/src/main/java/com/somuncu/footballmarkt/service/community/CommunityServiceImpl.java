package com.somuncu.footballmarkt.service.community;

import com.somuncu.footballmarkt.core.utiliites.exceptions.community.NoCommunityFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.community.NotAbleToDoThisOperationException;
import com.somuncu.footballmarkt.core.utiliites.mappers.ModelMapperService;
import com.somuncu.footballmarkt.dao.CommunityRepository;
import com.somuncu.footballmarkt.dao.UserRepository;
import com.somuncu.footballmarkt.entities.Community;
import com.somuncu.footballmarkt.entities.User;
import com.somuncu.footballmarkt.request.community.CreateCommunityRequest;
import com.somuncu.footballmarkt.request.community.UpdateCommunityRequest;
import com.somuncu.footballmarkt.response.DetermineNumbersForPagingResponse;
import com.somuncu.footballmarkt.response.PageResponse;
import com.somuncu.footballmarkt.response.dtos.community.CommunityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService{

    private final CommunityRepository communityRepository;
    private final ModelMapperService modelMapperService;
    private final UserRepository userRepository;
    @Override
    public Community getCommunityById(Long communityId) {
        return this.communityRepository.findById(communityId).orElseThrow(()-> new NoCommunityFoundException("There is no community"));
    }

    @Override
    public Community getCommunityByName(String communityName) {
        return this.communityRepository.findCommunityByCommunityName(communityName).orElseThrow(()-> new NoCommunityFoundException("No community found with this name"));
    }

    @Override
    public PageResponse<CommunityDto> getAllCommunities(int pagingOffset) {

        DetermineNumbersForPagingResponse determineNumbersForPagingResponse = determineNumbersForPagingResponse(pagingOffset);
        int pageNo = determineNumbersForPagingResponse.getPageNo();
        int pageSize = determineNumbersForPagingResponse.getPageSize();

        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Community> communities = this.communityRepository.findAll(pageable);

        List<Community> allCommunities = communities.getContent();
        List<CommunityDto> communityDtos = allCommunities.stream().map(community -> this.modelMapperService.forResponse().map(community , CommunityDto.class)).collect(Collectors.toList());

        PageResponse<CommunityDto> pageResponse = new PageResponse<>();
        pageResponse.setContent(communityDtos);
        pageResponse.setPageNo(communities.getNumber());
        pageResponse.setPageSize(communities.getSize());
        pageResponse.setTotalPages(communities.getTotalPages());
        pageResponse.setTotalElements(communities.getTotalElements());
        pageResponse.setLast(communities.isLast());

        return pageResponse;
    }

    @Override
    public List<String> getNameListOfCommunityUsers(Long communityId) {

        Community community = this.communityRepository.findById(communityId).orElseThrow(()-> new NoCommunityFoundException("No community found to see members"));
        List<User> communityUsers = community.getUsers();
        List<String> communityUsersNames = new ArrayList<>();
        for(User runner : communityUsers) {
           String userName = runner.getName();
           communityUsersNames.add(userName);
        }

        return communityUsersNames;
    }

    @Transactional
    @Override
    public void createCommunity(CreateCommunityRequest createCommunityRequest , UserDetails userDetails) {

        Community community = this.modelMapperService.forRequest().map(createCommunityRequest , Community.class);

        User creatorOfCommunity = this.getCurrentUser(userDetails);
        community.setCreatorOfCommunity(creatorOfCommunity);
        creatorOfCommunity.getCommunityOwnership().add(community);
        creatorOfCommunity.updateNoOfCommunityOwnership();

        List<User> users = new ArrayList<>();
        users.add(creatorOfCommunity);
        community.setUsers(users);
        this.communityRepository.save(community);
        this.userRepository.save(creatorOfCommunity);
    }

    @Override
    public void updateCommunity(UpdateCommunityRequest updateCommunityRequest , UserDetails userDetails) {

        Long communityIdToUpdate = updateCommunityRequest.getId();
        Community communityToUpdate = this.communityRepository.findById(communityIdToUpdate).orElseThrow(()-> new NoCommunityFoundException("No community found to update"));
        communityToUpdate.setCommunityName(updateCommunityRequest.getCommunityName());

        User currentUser = this.getCurrentUser(userDetails);
        User creatorOfCommunity = communityToUpdate.getCreatorOfCommunity();
        if(!Objects.equals(currentUser.getId(), creatorOfCommunity.getId())) {
            throw new NotAbleToDoThisOperationException("You are not able to do this operation");
        }
        this.communityRepository.save(communityToUpdate);
    }

    @Transactional
    @Override
    public void deleteCommunityById(Long communityId , UserDetails userDetails) {

        Community community = this.communityRepository.findById(communityId).orElseThrow(()-> new NoCommunityFoundException("No community found to delete"));

        User currentUser = this.getCurrentUser(userDetails);
        if(!Objects.equals(currentUser.getId(), community.getCreatorOfCommunity().getId())) {
            throw new NotAbleToDoThisOperationException("You are not able to do this operation");
        }

        currentUser.getCommunityOwnership().remove(community);
        currentUser.updateNoOfCommunityOwnership();
        this.userRepository.save(currentUser);

        List<User> users = community.getUsers();
        for (User user : users) {
            user.setNoOfCommunityRegistered(user.getNoOfCommunityRegistered() - 1L );
            userRepository.save(user);
        }

    }

    @Override
    public CommunityDto convertCommunityToCommunityDto(Community community) {
        CommunityDto communityDto= this.modelMapperService.forResponse().map(community , CommunityDto.class);
        return communityDto;
    }

    @Override
    public User getCurrentUser(UserDetails userDetails) {

        String email = userDetails.getUsername();
        User user = this.userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No user found") );
        return user;
    }

    @Override
    public DetermineNumbersForPagingResponse determineNumbersForPagingResponse(int pagingOffset) {

        int pageSize=20;
        int pageNo = pagingOffset/pageSize;

        return new DetermineNumbersForPagingResponse(pageNo,pageSize);
    }
}
