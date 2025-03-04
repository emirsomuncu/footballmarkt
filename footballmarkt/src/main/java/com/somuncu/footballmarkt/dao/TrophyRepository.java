package com.somuncu.footballmarkt.dao;

import com.somuncu.footballmarkt.entities.Trophy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrophyRepository extends JpaRepository<Trophy,Long> {

    public List<Trophy> findAllByName(String name);
    public Trophy findTrophyBySeasonAndLeagueName(String season , String leagueName);
    public List<Trophy> findAllByLeagueName(String leagueName);
    public List<Trophy> findAllByClubName(String clubName);
    public Boolean existsBySeasonAndName(String season , String name);

}
