package com.somuncu.footballmarkt.service.trophy;

import com.somuncu.footballmarkt.entities.Trophy;
import com.somuncu.footballmarkt.request.trophy.CreateTrophyRequest;
import com.somuncu.footballmarkt.request.trophy.UpdateTrophyRequest;
import com.somuncu.footballmarkt.dtos.trophy.TrophyDto;

import java.util.List;

public interface TrophyService {

    public List<Trophy> getTrophyByName(String name);
    public Trophy getTrophyBySeasonAndLeague(String season , String leagueName);
    public List<Trophy> getLeagueTrophies(String leagueName);
    public List<Trophy> getClubTrophies(String clubName);
    public void createTrophy(CreateTrophyRequest createTrophyRequest);
    public void updateTrophy(UpdateTrophyRequest updateTrophyRequest);
    public void deleteTrophy(Long trophyId);
    public TrophyDto convertTrophyToTrophyDto(Trophy trophy);
    public List<TrophyDto> convertTrophyListToTrophyDtoList(List<Trophy> trophies);

}
