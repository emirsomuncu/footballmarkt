package com.somuncu.footballmarkt.dao;

import com.somuncu.footballmarkt.entities.ClubHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubHistoryRepository extends JpaRepository<ClubHistory , Long> {

    public ClubHistory findClubHistoryByPlayerId(Long playerId);

}
