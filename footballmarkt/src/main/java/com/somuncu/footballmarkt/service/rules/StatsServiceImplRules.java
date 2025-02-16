package com.somuncu.footballmarkt.service.rules;

import com.somuncu.footballmarkt.core.utiliites.exceptions.stats.NoStatsFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.stats.StatsAlreadyExistsException;
import com.somuncu.footballmarkt.dao.StatsRepository;
import com.somuncu.footballmarkt.entities.Player;
import com.somuncu.footballmarkt.entities.Stats;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StatsServiceImplRules {

    private final StatsRepository statsRepository;

    public void checkIfListEmpty(List<Stats> statsList) {

        if (statsList.isEmpty()) {
            throw new NoStatsFoundException("No stats found");
        }
    }

    public void checkIfStatsExist(Stats stats , Player player) {

        if (this.statsRepository.existsBySeasonAndPlayer(stats.getSeason(), player)) {
            throw new StatsAlreadyExistsException("Stats record already exists at current season for current player");

        }
    }
}
