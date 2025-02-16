package com.somuncu.footballmarkt.dao;

import com.somuncu.footballmarkt.entities.Player;
import com.somuncu.footballmarkt.entities.Stats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatsRepository extends JpaRepository<Stats , Long> {

    public List<Stats> findStatsByPlayerFirstNameAndPlayerLastName(String firstName , String lastName);
    public Stats findStatsByPlayerFirstNameAndPlayerLastNameAndSeason(String firstName , String lastName , Long season);
    public Boolean existsBySeasonAndPlayer(Long season , Player player);
}
