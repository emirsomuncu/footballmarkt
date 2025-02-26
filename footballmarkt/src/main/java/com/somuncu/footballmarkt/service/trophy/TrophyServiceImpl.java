package com.somuncu.footballmarkt.service.trophy;

import com.somuncu.footballmarkt.core.utiliites.exceptions.club.NoClubFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.trophy.NoTrophyFoundException;
import com.somuncu.footballmarkt.core.utiliites.mappers.ModelMapperService;
import com.somuncu.footballmarkt.dao.ClubRepository;
import com.somuncu.footballmarkt.dao.PlayerRepository;
import com.somuncu.footballmarkt.dao.TrophyRepository;
import com.somuncu.footballmarkt.entities.Club;
import com.somuncu.footballmarkt.entities.Player;
import com.somuncu.footballmarkt.entities.Trophy;
import com.somuncu.footballmarkt.request.trophy.CreateTrophyRequest;
import com.somuncu.footballmarkt.request.trophy.UpdateTrophyRequest;
import com.somuncu.footballmarkt.response.dtos.trophy.TrophyDto;
import com.somuncu.footballmarkt.service.rules.TrophyServiceImplRules;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TrophyServiceImpl implements TrophyService {

    private final TrophyRepository trophyRepository;
    private final ClubRepository clubRepository;
    private final PlayerRepository playerRepository;
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

    @Transactional
    @Override
    public void createTrophy(CreateTrophyRequest createTrophyRequest) {

        Trophy trophy = this.modelMapperService.forRequest().map(createTrophyRequest , Trophy.class);
        this.trophyRepository.save(trophy);

        Long clubId = createTrophyRequest.getClubId();
        Club club = this.clubRepository.findById(clubId).orElseThrow(()-> new NoClubFoundException("There is no club to assign as a trophy winner"));
        List<Player> clubPlayers = club.getPlayers();

        // when a trophy created and assigned a team the trophy also must assigned players who are in the club
        for (Player player : clubPlayers) {
            List<Trophy> trophyList = player.getTrophies();
            trophyList.add(trophy);
            player.setTrophies(trophyList);
            playerRepository.save(player);
        }
    }

    @Transactional
    @Override
    public void updateTrophy(UpdateTrophyRequest updateTrophyRequest) {

        Long trophyIdToUpdate= updateTrophyRequest.getId();
        Trophy trophyToUpdate = this.trophyRepository.findById(trophyIdToUpdate).orElseThrow(()-> new NoTrophyFoundException("There is no trophy to update"));


        // if upcoming request containing new club for current trophy this part must handle player state
        if(trophyToUpdate.getClub().getId() != updateTrophyRequest.getClubId()) {

            // remove trophy from old club's players
            Club oldClub= trophyToUpdate.getClub();
            List<Player> oldClubPlayers = oldClub.getPlayers();

            for(Player oldPlayer : oldClubPlayers ) {
                List<Trophy> trophies = oldPlayer.getTrophies();
                trophies.remove(trophyToUpdate);
                oldPlayer.setTrophies(trophies);
                playerRepository.save(oldPlayer);
            }

            Long newClubId = updateTrophyRequest.getClubId();
            Club newClub = this.clubRepository.findById(newClubId).orElseThrow(()-> new NoClubFoundException("There is no club to assign as a trophy winner"));
            List<Player> newClubPlayers = newClub.getPlayers();

            // add trophy to new club's players
            for (Player newPlayer : newClubPlayers) {
                List<Trophy> trophyList = newPlayer.getTrophies();
                Trophy trophy = this.modelMapperService.forRequest().map(updateTrophyRequest , Trophy.class);
                this.trophyRepository.save(trophy);
                trophyList.add(trophy);
                newPlayer.setTrophies(trophyList);
                playerRepository.save(newPlayer);
            }
        }
        else {
            Trophy trophy = this.modelMapperService.forRequest().map(updateTrophyRequest , Trophy.class);
            this.trophyRepository.save(trophy);
        }

    }

    @Transactional
    @Override
    public void deleteTrophy(Long trophyId) {

        Trophy trophy = this.trophyRepository.findById(trophyId).orElseThrow(()-> new NoTrophyFoundException("No trophy found to delete"));

        List<Player> playerList = trophy.getClub().getPlayers();
        for (Player player :playerList) {
            List<Trophy> trophies = player.getTrophies();
            trophies.remove(trophy);
            player.setTrophies(trophies);
            playerRepository.save(player);
        }

        trophyRepository.delete(trophy);

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
