package com.somuncu.footballmarkt.service.user;

import com.somuncu.footballmarkt.core.utiliites.mappers.ModelMapperService;
import com.somuncu.footballmarkt.dao.UserRepository;
import com.somuncu.footballmarkt.entities.User;
import com.somuncu.footballmarkt.request.user.CreateUserRequest;
import com.somuncu.footballmarkt.service.rules.UserServiceImplRules;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final ModelMapperService modelMapperService;
    private final UserServiceImplRules userServiceImplRules;

    @Override
    public void createUser(CreateUserRequest createUserRequest) {

        User user = this.modelMapperService.forRequest().map(createUserRequest,User.class);
        this.userServiceImplRules.checkIfUserExists(user);
        user.setRole("USER");
        this.userRepository.save(user);
    }

}
