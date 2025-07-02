package com.somuncu.footballmarkt.service.rules;

import com.somuncu.footballmarkt.core.utiliites.exceptions.community.NoCommunityFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.user.UserAlreadyExistsException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.user.UserAlreadyInCommunityException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.user.UserNotFoundException;
import com.somuncu.footballmarkt.dao.CommunityRepository;
import com.somuncu.footballmarkt.dao.UserRepository;
import com.somuncu.footballmarkt.entities.Community;
import com.somuncu.footballmarkt.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImplRules {

    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;

    public void checkIfUserExists(User user) {

        if(this.userRepository.existsUserByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("There is an account that using this email");
        }

    }

    public void checkIfUserAlreadyExistInCommunity(Long communityId , UserDetails userDetails) {

        Community community = this.communityRepository.findById(communityId).orElseThrow(()-> new NoCommunityFoundException("No community found"));
        String userMail = userDetails.getUsername();
        User currentUser = this.userRepository.findUserByEmail(userMail).orElseThrow(()-> new UserNotFoundException("User not found to join community"));

        List<User> users = community.getUsers();
        if(users.contains(currentUser)) {
            throw new UserAlreadyInCommunityException("User already is in the community");
        }

    }


}
