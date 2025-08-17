package com.somuncu.footballmarkt.service.league;

import com.somuncu.footballmarkt.entities.League;
import com.somuncu.footballmarkt.dtos.league.LeagueDto;
import com.somuncu.footballmarkt.request.league.CreateLeagueRequest;
import com.somuncu.footballmarkt.request.league.UpdateLeagueRequest;

import java.util.List;

public interface LeagueService {

    public List<League> getAllLeagues();
    public List<League> getLeaguesWithAscendingMarketValue();
    public List<League> getLeaguesWithDescendingMarketValue();
    public void createLeague(CreateLeagueRequest createLeagueRequest);
    public void updateLeague(UpdateLeagueRequest updateLeagueRequest);
    public void deleteLeague(Long leagueId);
    public LeagueDto convertLeagueToLeagueDto(League league);
    public List<LeagueDto> convertLeagueListToLeagueListDtoList(List<League> leagues);



}
