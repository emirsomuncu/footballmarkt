package com.somuncu.footballmarkt.dao;

import com.somuncu.footballmarkt.entities.League;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeagueRepository extends JpaRepository<League , Long> {

    public List<League> findAllByOrderByLeagueValueAsc();
    public List<League> findAllByOrderByLeagueValueDesc();
}
