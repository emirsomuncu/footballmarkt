package com.somuncu.footballmarkt.service.user;

import com.somuncu.footballmarkt.entities.User;
import com.somuncu.footballmarkt.request.user.CreateUserRequest;

public interface UserService {

    public void createUser(CreateUserRequest createUserRequest);
}
