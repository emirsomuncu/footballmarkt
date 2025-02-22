package com.somuncu.footballmarkt.service.stats;

import com.somuncu.footballmarkt.entities.Stats;
import com.somuncu.footballmarkt.response.dtos.stats.StatsDto;
import com.somuncu.footballmarkt.request.stats.CreateStatsRequest;
import com.somuncu.footballmarkt.request.stats.UpdateStatsRequest;

import java.util.List;

public interface StatsService {

    public List<Stats> getStatsByPlayerName(String playerFirstName , String playerLastName);
    public Stats getStatsByPlayerNameAndSeason(String playerFirstName , String playerLastName , String season);

    public void createStats(CreateStatsRequest createStatsRequest);
    public void updateStats(UpdateStatsRequest updateStatsRequest);
    public void deleteStats(Long id);

    public StatsDto convertStatsToStatsDto(Stats stats);
    public List<StatsDto> convertStatsListToStatsDtoList(List<Stats> statsList);

}
