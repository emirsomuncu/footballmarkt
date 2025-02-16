package com.somuncu.footballmarkt.dao;

import com.somuncu.footballmarkt.entities.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club,Long> {

    public Club findClubByName(String clubName);
    public List<Club> findClubByLeagueName(String leagueName);
    public Boolean existsClubByName(String clubName);

}
