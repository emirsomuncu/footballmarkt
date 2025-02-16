package com.somuncu.footballmarkt.service.rules;

import com.somuncu.footballmarkt.core.utiliites.exceptions.user.UserAlreadyExistsException;
import com.somuncu.footballmarkt.dao.UserRepository;
import com.somuncu.footballmarkt.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImplRules {

    private final UserRepository userRepository;

    public void checkIfUserExists(User user) {

        if(this.userRepository.existsUserByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("There is an account that using this email");
        }

    }


}
