package com.somuncu.footballmarkt.service.arena;

import com.somuncu.footballmarkt.entities.ArenaGame;
import com.somuncu.footballmarkt.response.PlayArenaGameResponse;
import com.somuncu.footballmarkt.dtos.arenagame.ArenaGameDto;

public interface ArenaGameService {

    public ArenaGame createArenaGame(Long clubId , Long leagueId);
    public PlayArenaGameResponse playArenaGame(Long arenaGameId , Long playerId);
    public void deleteArenaGameRecord(Long arenaGameId);
    public ArenaGameDto convertArenaGameToArenaGameDto(ArenaGame arenaGame);
}
