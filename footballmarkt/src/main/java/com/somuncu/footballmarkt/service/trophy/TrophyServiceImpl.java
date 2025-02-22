package com.somuncu.footballmarkt.service.trophy;

import com.somuncu.footballmarkt.core.utiliites.exceptions.trophy.NoTrophyFoundException;
import com.somuncu.footballmarkt.core.utiliites.mappers.ModelMapperService;
import com.somuncu.footballmarkt.dao.TrophyRepository;
import com.somuncu.footballmarkt.entities.Trophy;
import com.somuncu.footballmarkt.request.trophy.CreateTrophyRequest;
import com.somuncu.footballmarkt.request.trophy.UpdateTrophyRequest;
import com.somuncu.footballmarkt.response.dtos.trophy.TrophyDto;
import com.somuncu.footballmarkt.service.rules.TrophyServiceImplRules;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TrophyServiceImpl implements TrophyService {

    private final TrophyRepository trophyRepository;
    private final TrophyServiceImplRules trophyServiceImplRules;
    private final ModelMapperService modelMapperService;


    @Override
    public List<Trophy> getTrophyByName(String name) {

        List<Trophy> trophies = this.trophyRepository.findAllByName(name);
        trophyServiceImplRules.checkIfTrophyListEmpty(trophies);
        return trophies;

    }

    @Override
    public Trophy getTrophyBySeasonAndLeague(String season, String leagueName) {

        Trophy trophy = this.trophyRepository.findTrophyBySeasonAndLeagueName(season , leagueName);
        if(trophy.getId()==null) {
            throw new NoTrophyFoundException("No trophy found with theese criterias");
        }
        return trophy;
    }

    @Override
    public List<Trophy> getLeagueTrophies(String leagueName) {

        List<Trophy> trophies = this.trophyRepository.findAllByLeagueName(leagueName);
        this.trophyServiceImplRules.checkIfTrophyListEmpty(trophies);
        return trophies;
    }

    @Override
    public List<Trophy> getClubTrophies(String clubName) {

        List<Trophy> trophies = this.trophyRepository.findAllByClubName(clubName);
        this.trophyServiceImplRules.checkIfTrophyListEmpty(trophies);
        return trophies;
    }

    @Override
    public void createTrophy(CreateTrophyRequest createTrophyRequest) {

        Trophy trophy = this.modelMapperService.forRequest().map(createTrophyRequest , Trophy.class);
        this.trophyRepository.save(trophy);
    }

    @Override
    public void updateTrophy(UpdateTrophyRequest updateTrophyRequest) {

        Trophy trophy = this.modelMapperService.forRequest().map(updateTrophyRequest , Trophy.class);
        this.trophyRepository.save(trophy);
    }

    @Override
    public void deleteTrophy(Long trophyId) {

        Trophy trophy = this.trophyRepository.findById(trophyId).orElseThrow(()-> new NoTrophyFoundException("No trophy found to delete"));
        this.trophyRepository.delete(trophy);
    }

    @Override
    public TrophyDto convertTrophyToTrophyDto(Trophy trophy) {

        TrophyDto trophyDto= this.modelMapperService.forResponse().map(trophy , TrophyDto.class);
        return trophyDto;
    }

    @Override
    public List<TrophyDto> convertTrophyListToTrophyDtoList(List<Trophy> trophies) {

        List<TrophyDto> trophyDtosList  = trophies.stream().map(this::convertTrophyToTrophyDto).collect(Collectors.toList());

        return trophyDtosList;
    }
}
