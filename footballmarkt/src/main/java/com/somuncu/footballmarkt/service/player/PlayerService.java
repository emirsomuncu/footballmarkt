package com.somuncu.footballmarkt.service.player;

import com.somuncu.footballmarkt.entities.Player;
import com.somuncu.footballmarkt.response.dtos.player.PlayerDto;
import com.somuncu.footballmarkt.request.player.AddPlayerRequest;
import com.somuncu.footballmarkt.request.player.UpdatePlayerRequest;

import java.util.List;

public interface PlayerService {

    public List<Player> listAllPlayers();
    public Player getPlayerById(Long playerId);
    public Player getPlayerByFullName(String fullName);
    public List<Player> getMostViewedPlayers(Long playerNumber);
    public List<Player> listPlayersAccordingToNation(String nation);
    public List<Player> listPlayersAccordingToClub(String clubName);
    public List<Player> listPlayersAccordingToPosition(String position);
    public List<Player> listPlayersAccordingToClubAndPosition(String clubName , String position);
    public List<Player> listAllPlayersAccordingToDescendingMarketValue();
    public List<Player> listAllPlayersOfClubAccordingToDescendingMarketValue(String clubName);
    public void addPlayer(AddPlayerRequest addPlayerRequest);
    public void updatePlayer(UpdatePlayerRequest updatePlayerRequest);
    public void transferPlayer(Long playerId , Long newClubId);
    public void deletePlayer(Long playerId);
    public PlayerDto convertPlayerToPlayerDto(Player player);
    public List<PlayerDto> convertPlayerListToPlayerDtoList(List<Player> playerList);

}
