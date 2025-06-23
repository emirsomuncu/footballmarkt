package com.somuncu.footballmarkt.dao;

import com.somuncu.footballmarkt.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player , Long> {


    @Query(value = "SELECT p.* FROM player p JOIN club c ON p.club_id = c.id JOIN league l ON c.league_id = l.id WHERE l.id = :leagueId ORDER BY RAND() LIMIT 1" , nativeQuery = true)
    public Player findOnePlayerByUsingLeagueId(Long leagueId);

    @Query(value = "SELECT p.* FROM player p JOIN club c ON p.club_id = c.id JOIN league l ON c.league_id = l.id WHERE l.id = :leagueId AND p.id != :excludedPlayerId ORDER BY RAND() LIMIT 1", nativeQuery = true)
    public Player findOneRandomPlayerByLeagueIdNativeExcludingPlayer(Long leagueId, Long excludedPlayerId);

    @Query(value = "SELECT p.* FROM player p WHERE p.club_id = :clubId ORDER BY RAND() LIMIT 1", nativeQuery = true)
    public Player findOnePlayerByClubId(Long clubId);
    @Query(value = "SELECT p.* FROM player p WHERE p.club_id = :clubId AND p.id != :excludedPlayerId ORDER BY RAND() LIMIT 1", nativeQuery = true)
    public Player findOneRandomPlayerByClubIdNativeExcludingPlayer(Long clubId, Long excludedPlayerId);

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
