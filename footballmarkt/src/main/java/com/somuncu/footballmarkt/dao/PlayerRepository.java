package com.somuncu.footballmarkt.dao;

import com.somuncu.footballmarkt.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player , Long> {

    public Player findPlayerByFullName(String fullName);

    @Query(value = "SELECT * FROM player ORDER BY profile_view_count DESC LIMIT :playerNumber" , nativeQuery = true)
    public List<Player> getMostViewedPlayer(Long playerNumber);

    public List<Player> findPlayerByClubName(String name);
    public List<Player> findPlayerByNation(String nation);
    public List<Player> findPlayerByPosition(String position);
    public List<Player> findPlayerByClubNameAndPosition(String clubName , String position);
    public List<Player> findAllByOrderByMarketValueDesc();
    public List<Player> findAllByClubNameOrderByMarketValueDesc(String clubName);
    @Modifying
    @Query(value = "DELETE FROM player WHERE id = :playerId", nativeQuery = true)
    public void deletePlayerForOneToOne(Long playerId);
    public Boolean existsPlayerByFirstNameAndLastNameAndAgeAndMarketValue(String firstName , String lastName , int age , Double marketValue);
}
