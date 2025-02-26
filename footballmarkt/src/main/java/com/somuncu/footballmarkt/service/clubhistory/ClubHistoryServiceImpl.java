package com.somuncu.footballmarkt.service.clubhistory;

import com.somuncu.footballmarkt.core.utiliites.exceptions.club.NoClubFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.clubhistory.NoClubHistoryFoundException;
import com.somuncu.footballmarkt.core.utiliites.mappers.ModelMapperService;
import com.somuncu.footballmarkt.dao.ClubHistoryRepository;
import com.somuncu.footballmarkt.dao.ClubRepository;
import com.somuncu.footballmarkt.entities.Club;
import com.somuncu.footballmarkt.entities.ClubHistory;
import com.somuncu.footballmarkt.response.dtos.clubhistory.ClubHistoryDto;
import com.somuncu.footballmarkt.service.player.PlayerService;
import com.somuncu.footballmarkt.service.player.PlayerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ClubHistoryServiceImpl implements ClubHistoryService{

    private final ClubHistoryRepository clubHistoryRepository;
    private final ClubRepository clubRepository;
    private final ModelMapperService modelMapperService;

    @Override
    public ClubHistory getClubHistoryOfPlayer(Long playerId) {

        ClubHistory clubHistory= this.clubHistoryRepository.findClubHistoryByPlayerId(playerId);
        if(clubHistory.getId() == null) {
            throw new NoClubHistoryFoundException("No club history found for current player");
        }
        return clubHistory;
    }

    @Override
    public void createClubHistory(List<Long> clubIds) {

        List<Club> clubList = clubIds.stream().map(id -> {
            Club club= this.clubRepository.findById(id).orElseThrow(()-> new NoClubFoundException("There is no club with id = " +id) );
            return club;
        }).collect(Collectors.toList());

        ClubHistory clubHistory = new ClubHistory();
        clubHistory.setClubs(clubList);
        clubHistoryRepository.save(clubHistory);

    }

    @Override
    public void updateClubHistory(Long clubHistoryId , List<Long> clubIds) {

        ClubHistory clubHistory = this.clubHistoryRepository.findById(clubHistoryId).orElseThrow(()-> new NoClubHistoryFoundException("There is no club history to update"));

        List<Club> clubList = clubIds.stream().map(id -> {
            Club club= this.clubRepository.findById(id).orElseThrow(()-> new NoClubFoundException("There is no club with id = " +id) );
            return club;
        }).collect(Collectors.toList());

        clubHistory.setClubs(clubList);
        clubHistoryRepository.save(clubHistory);
    }

    @Override
    public void deleteClubHistory(Long clubHistoryId) {

        ClubHistory clubHistory = this.clubHistoryRepository.findById(clubHistoryId).orElseThrow(()-> new NoClubHistoryFoundException("No club history found to remove"));
        this.clubHistoryRepository.delete(clubHistory);
    }

    @Override
    public ClubHistoryDto converClubHistoryToClubHistoryDto(ClubHistory clubHistory) {

        ClubHistoryDto clubHistoryDto = this.modelMapperService.forResponse().map(clubHistory , ClubHistoryDto.class);
        return clubHistoryDto;
    }
}
