package com.somuncu.footballmarkt.dao;

import com.somuncu.footballmarkt.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findUserByName(String name);
    public Optional<User> findUserByEmail(String email);
    public Boolean existsUserByEmail(String email);

}
