package com.somuncu.footballmarkt.dao;

import com.somuncu.footballmarkt.entities.Club;
import com.somuncu.footballmarkt.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club,Long> {

    @Query(value = "SELECT * FROM club ORDER BY RAND() LIMIT 1", nativeQuery = true)
    public Club findRandomClub();
    public Club findClubByName(String clubName);
    public List<Club> findClubByLeagueName(String leagueName);
    public Boolean existsClubByName(String clubName);

}
