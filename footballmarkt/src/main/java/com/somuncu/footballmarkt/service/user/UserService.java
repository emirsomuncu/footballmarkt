package com.somuncu.footballmarkt.service.user;

import com.somuncu.footballmarkt.entities.User;
import com.somuncu.footballmarkt.request.user.CreateUserRequest;
import com.somuncu.footballmarkt.dtos.user.UserDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    public User getUserById(Long id);
    public User getUserByName(String userName);
    public void createUser(CreateUserRequest createUserRequest);
    public void joinToCommunity(Long communityId , UserDetails userDetails);
    public UserDto convertUserToUserDto(User user);
}
