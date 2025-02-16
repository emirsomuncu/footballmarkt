package com.somuncu.footballmarkt.service.league;

import com.somuncu.footballmarkt.core.utiliites.mappers.ModelMapperService;
import com.somuncu.footballmarkt.dao.LeagueRepository;
import com.somuncu.footballmarkt.entities.League;
import com.somuncu.footballmarkt.request.dtos.league.LeagueDto;
import com.somuncu.footballmarkt.request.league.CreateLeagueRequest;
import com.somuncu.footballmarkt.request.league.UpdateLeagueRequest;
import com.somuncu.footballmarkt.service.rules.LeagueServiceImplRules;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LeagueServiceImpl implements LeagueService {

    private final LeagueRepository leagueRepository;
    private final ModelMapperService modelMapperService;
    private final LeagueServiceImplRules leagueServiceImplRules;

    @Override
    public List<League> getAllLeagues() {

        List<League> leagues = this.leagueRepository.findAll();
        this.leagueServiceImplRules.checkIfListIsEmpty(leagues);
        return leagues;
    }

    @Override
    public List<League> getLeaguesWithAscendingMarketValue() {

        List<League> leagues = this.leagueRepository.findAllByOrderByLeagueValueAsc();
        this.leagueServiceImplRules.checkIfListIsEmpty(leagues);
        return leagues;
    }

    @Override
    public List<League> getLeaguesWithDescendingMarketValue() {

        List<League> leagues = this.leagueRepository.findAllByOrderByLeagueValueDesc();
        this.leagueServiceImplRules.checkIfListIsEmpty(leagues);
        return leagues;
    }

    @Override
    public void createLeague(CreateLeagueRequest createLeagueRequest) {

        League league = this.modelMapperService.forRequest().map(createLeagueRequest , League.class);
        league.setLeagueValue(0.0);
        this.leagueRepository.save(league);

    }

    @Override
    public void updateLeague(UpdateLeagueRequest updateLeagueRequest) {

        Long leagueId = updateLeagueRequest.getLeagueId();
        League league = this.leagueRepository.findById(leagueId).orElseThrow();
        this.modelMapperService.forRequest().map(updateLeagueRequest , league);
        this.leagueRepository.save(league);
    }

    @Override
    public void deleteLeague(Long leagueId) {

        this.leagueRepository.deleteById(leagueId);
    }

    @Override
    public LeagueDto convertLeagueToLeagueDto(League league) {

        LeagueDto leagueDto = this.modelMapperService.forResponse().map(league , LeagueDto.class);
        return leagueDto;
    }

    @Override
    public List<LeagueDto> convertLeagueListToLeagueListDtoList(List<League> leagues) {

        List<LeagueDto> leagueDtoList = leagues.stream().map(this::convertLeagueToLeagueDto).collect(Collectors.toList());
        return leagueDtoList;
    }

}
