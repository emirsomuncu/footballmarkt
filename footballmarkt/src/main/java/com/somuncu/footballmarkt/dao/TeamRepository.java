package com.somuncu.footballmarkt.dao;

import com.somuncu.footballmarkt.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team , Long> {

    public Optional<Team> findTeamByName(String name);
    public List<Team> findAllByUserName(String username);

}
