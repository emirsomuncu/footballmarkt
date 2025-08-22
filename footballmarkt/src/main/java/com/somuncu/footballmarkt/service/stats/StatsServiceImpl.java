package com.somuncu.footballmarkt.service.stats;

import com.somuncu.footballmarkt.core.utiliites.exceptions.stats.NoStatsFoundException;
import com.somuncu.footballmarkt.core.utiliites.mappers.ModelMapperService;
import com.somuncu.footballmarkt.dao.StatsRepository;
import com.somuncu.footballmarkt.entities.Stats;
import com.somuncu.footballmarkt.dtos.stats.StatsDto;
import com.somuncu.footballmarkt.request.stats.CreateStatsRequest;
import com.somuncu.footballmarkt.request.stats.UpdateStatsRequest;
import com.somuncu.footballmarkt.service.rules.StatsServiceImplRules;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository ;
    private final StatsServiceImplRules statsServiceImplRules;
    private final ModelMapperService modelMapperService;

    @Override
    public List<Stats> getStatsByPlayerName(String playerFirstName , String playerLastName) {

        List<Stats> statsList = this.statsRepository.findStatsByPlayerFirstNameAndPlayerLastName(playerFirstName , playerLastName);
        this.statsServiceImplRules.checkIfListEmpty(statsList);

        return statsList;
    }

    @Override
    public Stats getStatsByPlayerNameAndSeason(String playerFirstName,String playerLastName, String season) {

        Stats stats = this.statsRepository.findStatsByPlayerFirstNameAndPlayerLastNameAndSeason(playerFirstName,playerLastName,season);
        if(stats.getId() == null) {
            throw new NoStatsFoundException("No stats found");
        }

        return stats;
    }

    @Override
    public void createStats(CreateStatsRequest createStatsRequest) {

        Stats stats = this.modelMapperService.forRequest().map(createStatsRequest , Stats.class);
        this.statsServiceImplRules.checkIfStatsExist(stats , stats.getPlayer());
        stats.evaluatePassingAccuracyRate();
        stats.evaluateSuccessfulShootingRate();
        stats.evaluateSuccessfulDuelRate();
        stats.evaluateSuccessfulDribblesRate();
        stats.evaluateDribblesPerMatch();
        stats.evaluateAirDuelSuccessRate();

        this.statsRepository.save(stats);
    }

    @Override
    public void updateStats(UpdateStatsRequest updateStatsRequest) {

        Stats stats = this.modelMapperService.forRequest().map(updateStatsRequest , Stats.class);
        stats.evaluatePassingAccuracyRate();
        stats.evaluateSuccessfulShootingRate();
        stats.evaluateSuccessfulDuelRate();
        stats.evaluateSuccessfulDribblesRate();
        stats.evaluateDribblesPerMatch();
        stats.evaluateAirDuelSuccessRate();
        this.statsRepository.save(stats);
    }

    @Override
    public void deleteStats(Long id) {

        Stats stats = this.statsRepository.findById(id).orElseThrow(()-> new NoStatsFoundException("No stats found "));
        this.statsRepository.delete(stats);
    }

    @Override
    public StatsDto convertStatsToStatsDto(Stats stats) {

        StatsDto statsDto = this.modelMapperService.forResponse().map(stats , StatsDto.class);
        return statsDto;
    }

    @Override
    public List<StatsDto> convertStatsListToStatsDtoList(List<Stats> statsList) {

        List<StatsDto> statsDtosList = statsList.stream().map(this::convertStatsToStatsDto).collect(Collectors.toList());
        return statsDtosList;
    }
}
