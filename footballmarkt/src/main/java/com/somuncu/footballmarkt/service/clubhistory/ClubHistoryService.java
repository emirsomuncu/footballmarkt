package com.somuncu.footballmarkt.service.clubhistory;

import com.somuncu.footballmarkt.entities.ClubHistory;
import com.somuncu.footballmarkt.response.dtos.clubhistory.ClubHistoryDto;

import java.util.List;

public interface ClubHistoryService {

    public ClubHistory getClubHistoryOfPlayer(Long playerId);
    public void createClubHistory(List<Long> clubIds);
    public void updateClubHistory(Long clubHistoryId , List<Long> clubIds);
    public void deleteClubHistory(Long clubHistoryId);
    public ClubHistoryDto converClubHistoryToClubHistoryDto(ClubHistory clubHistory);
}
