package com.somuncu.footballmarkt.service.club;

import com.somuncu.footballmarkt.core.utiliites.exceptions.club.NoClubFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.images.NoImageFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.league.NoLeaguesFoundException;
import com.somuncu.footballmarkt.core.utiliites.mappers.ModelMapperService;
import com.somuncu.footballmarkt.dao.*;
import com.somuncu.footballmarkt.entities.*;
import com.somuncu.footballmarkt.request.club.CreateClubRequest;
import com.somuncu.footballmarkt.request.club.UpdateClubRequest;
import com.somuncu.footballmarkt.response.dtos.club.ClubDto;
import com.somuncu.footballmarkt.service.rules.ClubServiceImplRules;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ClubServiceImpl implements ClubService{

    private final ClubRepository clubRepository;
    private final PlayerRepository playerRepository;
    private final LeagueRepository leagueRepository;
    private final ImageRepository imageRepository;
    private final ClubHistoryRepository clubHistoryRepository;
    private final ClubServiceImplRules clubServiceImplRules;
    private final ModelMapperService modelMapperService;

    @Override
    public List<Club> getAllClubs() {

        List<Club> clubs = this.clubRepository.findAll();
        this.clubServiceImplRules.checkIfListEmpty(clubs);
        return clubs;

    }

    @Override
    public List<Club> getAllClubsByAccordingToLeagueName(String leagueName) {

        List<Club> clubList = this.clubRepository.findClubByLeagueName(leagueName);
        this.clubServiceImplRules.checkIfListEmpty(clubList);

        return clubList;
    }

    @Override
    public Club getClubByName(String clubName) {
        Club club= this.clubRepository.findClubByName(clubName);
        if(club.getId() == null) {
            throw new NoClubFoundException("No club found with this name");
        }
        return club;
    }

    @Override
    public void createClub(CreateClubRequest createClubRequest) {

        Club club = this.modelMapperService.forRequest().map(createClubRequest , Club.class);
        this.clubServiceImplRules.checkIfClubExists(club);
        club.setClubValue(0.0);
        List<Long> ids = createClubRequest.getImagesIds();
        for(Long id : ids) {
            Image image = this.imageRepository.findById(id).orElseThrow(()-> new NoImageFoundException("No image found to assign to club"));
            image.setClub(club);
            club.getImages().add(image);
        }
        this.clubRepository.save(club);
    }

    @Override
    public void updateClub(UpdateClubRequest updateClubRequest) {

        Long clubId = updateClubRequest.getId();
        Club club = this.clubRepository.findById(clubId).orElseThrow(()-> new NoClubFoundException("No club found to update"));
        this.modelMapperService.forRequest().map(updateClubRequest , club);
        this.clubRepository.save(club);
    }

    @Override
    public void changeClubLeague(Long clubId, Long newLeagueId) {

        Club clubToUpdate = this.clubRepository.findById(clubId).orElseThrow(()-> new NoClubFoundException("No club found to change league"));
        League oldLeague = clubToUpdate.getLeague();
        oldLeague.getClubs().remove(clubToUpdate);
        oldLeague.updateLeagueValue();

        League newLeague = this.leagueRepository.findById(newLeagueId).orElseThrow(()-> new NoLeaguesFoundException("No league found to change club's league"));
        clubToUpdate.setLeague(newLeague);
        newLeague.getClubs().add(clubToUpdate);
        newLeague.updateLeagueValue();
        this.leagueRepository.save(newLeague);

    }

    @Transactional
    @Override
    public void deleteClub(Long clubId) {

        Club club = this.clubRepository.findById(clubId).orElseThrow(()-> new NoClubFoundException("No club found to delete "));

        List<Player> players = this.playerRepository.findAll();

        for(Player player : players) {

            ClubHistory clubHistory = player.getClubHistory();
            List<Club> playerClubList = clubHistory.getClubs();
            if(playerClubList.contains(club)) {
                playerClubList.remove(club);
                clubHistory.setClubs(playerClubList);
                clubHistoryRepository.save(clubHistory);
            }
        }

        League league = club.getLeague();
        league.getClubs().remove(club);
        league.updateLeagueValue();
        leagueRepository.save(league);
    }

    @Override
    public ClubDto convertClubToClubDto(Club club) {

        ClubDto clubDto = this.modelMapperService.forResponse().map(club , ClubDto.class);
        return clubDto;
    }

    @Override
    public List<ClubDto> convertClubListToClubDtoList(List<Club> clubList) {

        List<ClubDto> clubDtosList = clubList.stream().map(this::convertClubToClubDto).collect(Collectors.toList());
        return clubDtosList;
    }


}
