package com.somuncu.footballmarkt.service.player;

import com.somuncu.footballmarkt.entities.Player;
import com.somuncu.footballmarkt.response.DetermineNumbersForPagingResponse;
import com.somuncu.footballmarkt.response.PageResponse;
import com.somuncu.footballmarkt.dtos.player.PlayerDto;
import com.somuncu.footballmarkt.request.player.AddPlayerRequest;
import com.somuncu.footballmarkt.request.player.UpdatePlayerRequest;

import java.util.List;

public interface PlayerService {

    public PageResponse<PlayerDto> listAllPlayers(int pagingOffset);
    public Player getPlayerById(Long playerId);
    public Player getPlayerByFullName(String fullName);
    public PageResponse<PlayerDto> getMostViewedPlayers(int pagingOffset);
    public PageResponse<PlayerDto> listPlayersAccordingToNation(String nation , int pagingOffset);
    public List<Player> listPlayersAccordingToClub(String clubName);
    public PageResponse<PlayerDto> listPlayersAccordingToPosition(String position , int pagingOffset);
    public List<Player> listPlayersAccordingToClubAndPosition(String clubName , String position);
    public PageResponse<PlayerDto> listAllPlayersAccordingToDescendingMarketValue(int pagingOffset);
    public PageResponse<PlayerDto> listAllPlayersOfClubAccordingToDescendingMarketValue(String clubName , int pagingOffset);
    public List<PlayerDto> suggestSimilarPlayers(Long playerId);
    public void addPlayer(AddPlayerRequest addPlayerRequest);
    public void updatePlayer(UpdatePlayerRequest updatePlayerRequest);
    public void transferPlayer(Long playerId , Long newClubId);

    // public void deletePlayer(Long playerId); It causes some errors and should be deactivated until resolved
    public PlayerDto convertPlayerToPlayerDto(Player player);
    public List<PlayerDto> convertPlayerListToPlayerDtoList(List<Player> playerList);
    public DetermineNumbersForPagingResponse determineNumbersForPaging(int pagingOffset);
}
