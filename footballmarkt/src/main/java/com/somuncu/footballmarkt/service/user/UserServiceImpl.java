package com.somuncu.footballmarkt.service.user;

import com.somuncu.footballmarkt.core.utiliites.exceptions.community.NoCommunityFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.user.UserNotFoundException;
import com.somuncu.footballmarkt.core.utiliites.mappers.ModelMapperService;
import com.somuncu.footballmarkt.dao.CommunityRepository;
import com.somuncu.footballmarkt.dao.UserRepository;
import com.somuncu.footballmarkt.entities.Community;
import com.somuncu.footballmarkt.entities.User;
import com.somuncu.footballmarkt.request.user.CreateUserRequest;
import com.somuncu.footballmarkt.dtos.user.UserDto;
import com.somuncu.footballmarkt.service.rules.UserServiceImplRules;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;
    private final ModelMapperService modelMapperService;
    private final UserServiceImplRules userServiceImplRules;

    @Override
    public User getUserById(Long id) {

       return this.userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User not found"));
    }

    @Override
    public User getUserByName(String userName) {

        return this.userRepository.findUserByName(userName);
    }

    @Override
    public void createUser(CreateUserRequest createUserRequest) {

        User user = this.modelMapperService.forRequest().map(createUserRequest,User.class);
        this.userServiceImplRules.checkIfUserExists(user);
        user.setRole("USER");
        this.userRepository.save(user);
    }

    @Transactional
    @Override
    public void joinToCommunity(Long communityId, UserDetails userDetails) {

        this.userServiceImplRules.checkIfUserAlreadyExistInCommunity(communityId , userDetails );

        // To set the number of communities in which the user is registered in the transaction
        String userMail = userDetails.getUsername();
        User currentUser = userRepository.findUserByEmail(userMail).orElseThrow(()-> new UserNotFoundException("User not found to register community"));
        currentUser.updateNoOfCommunityRegistered();
        userRepository.save(currentUser);

        Community community = this.communityRepository.findById(communityId).orElseThrow(()-> new NoCommunityFoundException("No community found to join"));
        List<User> users = community.getUsers();
        users.add(currentUser);
        community.updateNumOfMembers();
        communityRepository.save(community);
    }

    @Override
    public UserDto convertUserToUserDto(User user) {
        UserDto userDto = this.modelMapperService.forResponse().map(user , UserDto.class);
        return userDto;
    }

}
