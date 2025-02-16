package com.somuncu.footballmarkt.service.rules;

import com.somuncu.footballmarkt.core.utiliites.exceptions.league.NoLeaguesFoundException;
import com.somuncu.footballmarkt.dao.LeagueRepository;
import com.somuncu.footballmarkt.entities.League;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LeagueServiceImplRules {

    private final LeagueRepository leagueRepository;


    public void checkIfListIsEmpty(List<League> leagues) {

        if(leagues.isEmpty()) {
            throw new NoLeaguesFoundException("Opss! No Leagues found");
        }
    }

}
