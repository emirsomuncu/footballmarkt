package com.somuncu.footballmarkt.service.player;

import com.somuncu.footballmarkt.core.utiliites.exceptions.club.NoClubFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.player.NoPlayerFoundException;
import com.somuncu.footballmarkt.core.utiliites.exceptions.trophy.NoTrophyFoundException;
import com.somuncu.footballmarkt.core.utiliites.mappers.ModelMapperService;;
import com.somuncu.footballmarkt.dao.*;
import com.somuncu.footballmarkt.entities.*;
import com.somuncu.footballmarkt.response.DetermineNumbersForPagingResponse;
import com.somuncu.footballmarkt.response.PageResponse;
import com.somuncu.footballmarkt.response.dtos.player.PlayerDto;
import com.somuncu.footballmarkt.request.player.AddPlayerRequest;
import com.somuncu.footballmarkt.request.player.UpdatePlayerRequest;
import com.somuncu.footballmarkt.service.rules.PlayerServiceImplRules;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class PlayerServiceImpl implements PlayerService{

    private PlayerRepository playerRepository;
    private ClubRepository clubRepository;
    private ClubHistoryRepository clubHistoryRepository;
    private LeagueRepository leagueRepository;
    private TrophyRepository trophyRepository;
    private PlayerServiceImplRules playerServiceImplRules;
    private ModelMapperService modelMapperService ;

    @Override
    public List<Player> listAllPlayers() {
        List<Player> playerList = this.playerRepository.findAll();
        this.playerServiceImplRules.checkIfListEmpty(playerList);
        return playerList;
    }

    @Override
    public Player getPlayerById(Long playerId) {

        Player player = this.playerRepository.findById(playerId).orElseThrow(()-> new NoPlayerFoundException("No player found"));
        player.updateProfileViewCount();
        this.playerRepository.save(player);
        return player;
    }

    @Override
    public Player getPlayerByFullName(String fullName) {

        Player player = this.playerRepository.findPlayerByFullName(fullName);
        if(player != null) {
            player.updateProfileViewCount();
            this.playerRepository.save(player);
            return player;
        }
        else {
            throw new NoPlayerFoundException("No player found with this name");
        }
    }

    @Override
    public PageResponse<PlayerDto> getMostViewedPlayers(int pagingOffset ) {

        DetermineNumbersForPagingResponse determineNumbersForPagingResponse = determineNumbersForPaging(pagingOffset);
        int pageNo = determineNumbersForPagingResponse.getPageNo();
        int pageSize = determineNumbersForPagingResponse.getPageSize();

        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Player> playerList = this.playerRepository.findAllByOrderByProfileViewCountDesc(pageable);

        List<Player> players = playerList.getContent();
        List<PlayerDto> playerDtoList = players.stream().map(player -> this.modelMapperService.forResponse().map(player ,PlayerDto.class)).collect(Collectors.toList());

        PageResponse<PlayerDto> pageResponse = new PageResponse<>();
        pageResponse.setContent(playerDtoList);
        pageResponse.setPageNo(playerList.getNumber());
        pageResponse.setPageSize(playerList.getSize());
        pageResponse.setTotalElements(playerList.getTotalElements());
        pageResponse.setTotalPages(playerList.getTotalPages());
        pageResponse.setLast(playerList.isLast());

        return pageResponse;
    }

    @Override
    public PageResponse<PlayerDto> listPlayersAccordingToNation(String nation , int pagingOffset) {

        DetermineNumbersForPagingResponse determineNumbersForPagingResponse = determineNumbersForPaging(pagingOffset);
        int pageNo = determineNumbersForPagingResponse.getPageNo();
        int pageSize = determineNumbersForPagingResponse.getPageSize();

        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Player> playerList = this.playerRepository.findPlayerByNation(nation,pageable);

        List<Player> players = playerList.getContent();
        List<PlayerDto> playerDtoList = players.stream().map(player -> this.modelMapperService.forResponse().map(player ,PlayerDto.class)).collect(Collectors.toList());
        this.playerServiceImplRules.checkIfListEmpty(players);

        PageResponse<PlayerDto> pageResponse = new PageResponse<>();
        pageResponse.setContent(playerDtoList);
        pageResponse.setPageNo(playerList.getNumber());
        pageResponse.setPageSize(playerList.getSize());
        pageResponse.setTotalElements(playerList.getTotalElements());
        pageResponse.setTotalPages(playerList.getTotalPages());
        pageResponse.setLast(playerList.isLast());

        return pageResponse;
    }

    @Override
    public List<Player> listPlayersAccordingToClub(String clubName) {

        List<Player> playerList = this.playerRepository.findPlayerByClubName(clubName);
        this.playerServiceImplRules.checkIfListEmpty(playerList);
        return playerList;
    }

    @Override
    public PageResponse<PlayerDto> listPlayersAccordingToPosition(String position , int pagingOffset) {

        DetermineNumbersForPagingResponse determineNumbersForPagingResponse = determineNumbersForPaging(pagingOffset);
        int pageNo = determineNumbersForPagingResponse.getPageNo();
        int pageSize = determineNumbersForPagingResponse.getPageSize();

        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Player> playerList = this.playerRepository.findPlayerByPosition(position , pageable);

        List<Player> players = playerList.getContent();
        this.playerServiceImplRules.checkIfListEmpty(players);
        List<PlayerDto> playerDtoList = players.stream().map(player -> this.modelMapperService.forResponse().map(player ,PlayerDto.class)).collect(Collectors.toList());

        PageResponse<PlayerDto> pageResponse = new PageResponse<>();
        pageResponse.setContent(playerDtoList);
        pageResponse.setPageNo(playerList.getNumber());
        pageResponse.setPageSize(playerList.getSize());
        pageResponse.setTotalElements(playerList.getTotalElements());
        pageResponse.setTotalPages(playerList.getTotalPages());
        pageResponse.setLast(playerList.isLast());

        return pageResponse;

    }

    @Override
    public List<Player> listPlayersAccordingToClubAndPosition(String clubName, String position) {

        List<Player> playerList = this.playerRepository.findPlayerByClubNameAndPosition(clubName ,position);
        this.playerServiceImplRules.checkIfListEmpty(playerList);
        return playerList;
    }

    @Override
    public PageResponse<PlayerDto> listAllPlayersAccordingToDescendingMarketValue(int pagingOffset) {

        DetermineNumbersForPagingResponse determineNumbersForPagingResponse = determineNumbersForPaging(pagingOffset);
        int pageNo = determineNumbersForPagingResponse.getPageNo();
        int pageSize = determineNumbersForPagingResponse.getPageSize();

        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Player> playerList = this.playerRepository.findAllByOrderByMarketValueDesc(pageable);

        List<Player> players = playerList.getContent();
        this.playerServiceImplRules.checkIfListEmpty(players);
        List<PlayerDto> playerDtoList = players.stream().map(player -> this.modelMapperService.forResponse().map(player ,PlayerDto.class)).collect(Collectors.toList());

        PageResponse<PlayerDto> pageResponse = new PageResponse<>();
        pageResponse.setContent(playerDtoList);
        pageResponse.setPageNo(playerList.getNumber());
        pageResponse.setPageSize(playerList.getSize());
        pageResponse.setTotalElements(playerList.getTotalElements());
        pageResponse.setTotalPages(playerList.getTotalPages());
        pageResponse.setLast(playerList.isLast());

        return pageResponse;

    }

    @Override
    public PageResponse<PlayerDto> listAllPlayersOfClubAccordingToDescendingMarketValue(String clubName , int pagingOffset) {

        DetermineNumbersForPagingResponse determineNumbersForPagingResponse = determineNumbersForPaging(pagingOffset);
        int pageNo = determineNumbersForPagingResponse.getPageNo();
        int pageSize = determineNumbersForPagingResponse.getPageSize();

        Pageable pageable = PageRequest.of(pageNo,pageSize);
        Page<Player> playerList = this.playerRepository.findAllByClubNameOrderByMarketValueDesc(clubName , pageable);

        List<Player> players = playerList.getContent();
        this.playerServiceImplRules.checkIfListEmpty(players);
        List<PlayerDto> playerDtoList = players.stream().map(player -> this.modelMapperService.forResponse().map(player ,PlayerDto.class)).collect(Collectors.toList());

        PageResponse<PlayerDto> pageResponse = new PageResponse<>();
        pageResponse.setContent(playerDtoList);
        pageResponse.setPageNo(playerList.getNumber());
        pageResponse.setPageSize(playerList.getSize());
        pageResponse.setTotalElements(playerList.getTotalElements());
        pageResponse.setTotalPages(playerList.getTotalPages());
        pageResponse.setLast(playerList.isLast());

        return pageResponse;
    }

    @Transactional
    @Override
    public void addPlayer(AddPlayerRequest addPlayerRequest) {

        Player player = this.modelMapperService.forRequest().map(addPlayerRequest , Player.class);
        Long clubHistoryId = addPlayerRequest.getClubHistoryId();
        ClubHistory clubHistory = this.clubHistoryRepository.findById(clubHistoryId).orElseThrow(()-> new NoClubFoundException("No club found to assing it to a player "));
        player.setClubHistory(clubHistory);

        player.updateFullName();

        if(addPlayerRequest.getTrophyIds() != null) {
            List<Trophy> trophyList = new ArrayList<>();
            List<Long> trophyIds = addPlayerRequest.getTrophyIds();
            for(Long id : trophyIds) {
                Trophy trophy = this.trophyRepository.findById(id).orElseThrow(()-> new NoTrophyFoundException("No trophy found to add player"));
                trophyList.add(trophy);
            }
            player.setTrophies(trophyList);
        }
        else{
            List<Trophy> emptyTrophyList = new ArrayList<>();
            player.setTrophies(emptyTrophyList);
        }
        this.playerServiceImplRules.checkIfPlayerExists(player.getFirstName(), player.getLastName(), player.getAge(), player.getMarketValue());

        // to ensure that when a player added ,updated club value is exists
        Club club = clubRepository.findById(addPlayerRequest.getClubId()).get();
        club.getPlayers().add(player);
        club.updateClubValue();
        clubRepository.save(club);

        // to ensure that when a player added and club value updated , update league value
        League league = club.getLeague();
        league.getClubs().add(club);
        league.updateLeagueValue();
        leagueRepository.save(league);

    }

    @Override
    @Transactional
    public void updatePlayer(UpdatePlayerRequest updatePlayerRequest) {

        Player player = this.modelMapperService.forRequest().map(updatePlayerRequest , Player.class);
        Long id = updatePlayerRequest.getClubId();
        if(updatePlayerRequest.getTrophyIds() != null) {
            Long idToUpdatePlayer = updatePlayerRequest.getId();
            Player playerToUpdate = playerRepository.findById(idToUpdatePlayer).get();
            playerToUpdate.getTrophies().clear();
            List<Trophy> trophyList = new ArrayList<>();
            List<Long> trophyIds = updatePlayerRequest.getTrophyIds();
            for(Long aLong : trophyIds) {
                Trophy trophy = this.trophyRepository.findById(aLong).orElseThrow(()-> new NoTrophyFoundException("No trophy found to add player"));
                trophyList.add(trophy);
            }
            player.setTrophies(trophyList);
        }
        else{
            List<Trophy> emptyTrophyList = new ArrayList<>();
            player.setTrophies(emptyTrophyList);
        }

        Club club= this.clubRepository.findById(id).get();
        Long playerId = updatePlayerRequest.getId();
        Player playerToUpdate = club.getPlayers().stream().filter(clubPlayers-> clubPlayers.getId().equals(playerId)).findFirst().orElseThrow(()-> new NoPlayerFoundException("No player found to update"));
        modelMapperService.forResponse().map(player , playerToUpdate);
        club.updateClubValue();
        clubRepository.save(club);

        League league = club.getLeague();
        league.getClubs().add(club); // We used this .add() method to replace(update) old club instance with the current one
        league.updateLeagueValue();
        leagueRepository.save(league);

    }

    @Override
    @Transactional
    public void transferPlayer(Long playerId, Long newClubId) {

        Player playerToTransfer = this.playerRepository.findById(playerId).orElseThrow(()-> new NoPlayerFoundException("There is no player to transfer"));

        // adjusting old club and league situations
        Club oldClub = playerToTransfer.getClub();
        oldClub.getPlayers().remove(playerToTransfer);
        oldClub.updateClubValue();
        League oldLeague = oldClub.getLeague();
        oldLeague.getClubs().add(oldClub);
        oldLeague.updateLeagueValue();

        //adjusting new club and league situations
        Club newClub = clubRepository.findById(newClubId).orElseThrow(()-> new NoClubFoundException("No club found to transfer player"));
        playerToTransfer.setClub(newClub);
        newClub.getPlayers().add(playerToTransfer);
        Double newValue = newClub.updateClubValue();
        League newLeague = newClub.getLeague();
        Club newClubInClubList = newLeague.getClubs().stream().filter(club -> club.getId().equals(newClub.getId())).findFirst().orElseThrow(()-> new RuntimeException("Unexpected error"));
        newClubInClubList.setClubValue(newValue);
        newLeague.updateLeagueValue();
    }

    @Override
    @Transactional
    public void deletePlayer(Long playerId) {


        Player player = this.playerRepository.findById(playerId).orElseThrow(()-> new NoPlayerFoundException("No player found to delete"));
        Club club = player.getClub();
        club.getPlayers().remove(player);
        club.updateClubValue();
        clubRepository.save(club);
        // special need for deleting objects which contains one-to-one relation
        playerRepository.deletePlayerForOneToOne(player.getId());

        League league = club.getLeague();
        league.getClubs().add(club); // We used this .add() method to replace(update) old club instance with the current one
        league.updateLeagueValue();
        leagueRepository.save(league);
    }


    public PlayerDto convertPlayerToPlayerDto(Player player) {

        PlayerDto playerDto = this.modelMapperService.forResponse().map(player , PlayerDto.class);
        return playerDto ;

    }

    @Override
    public List<PlayerDto> convertPlayerListToPlayerDtoList(List<Player> playerList) {

         List<PlayerDto> playerDtoList  = playerList.stream().map(this::convertPlayerToPlayerDto).collect(Collectors.toList());
         return playerDtoList;
    }

    @Override
    public DetermineNumbersForPagingResponse determineNumbersForPaging(int pagingOffset) {

        int pageSize = 2 ;
        int pageNo = pagingOffset/pageSize;

        return new DetermineNumbersForPagingResponse(pageSize,pageNo);
    }

}
